package itch.tecnm.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import itch.tecnm.Repository.IReservar;
import itch.tecnm.model.Atender;
import itch.tecnm.model.Cliente;
import itch.tecnm.model.DetallePedido;
import itch.tecnm.model.DetallePedidoId;
import itch.tecnm.model.Empleado;
import itch.tecnm.model.Pedido;
import itch.tecnm.model.Producto;
import itch.tecnm.model.Reservar;
import itch.tecnm.model.UsuarioDetalle;
import itch.tecnm.service.IClienteService;
import itch.tecnm.service.IEmpleado;
import itch.tecnm.service.IPedidoService;
import itch.tecnm.service.IProductoService;
import itch.tecnm.service.IUsuarioDetalleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletResponse;





@Controller
@RequestMapping(value="/pedido")
public class PedidoController {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private IPedidoService servicePedido;
	
	@Autowired
	private IClienteService serviceCliente;
	
	@Autowired
	private IProductoService serviceProducto;
	
	@Autowired
	private IEmpleado serviceEmpleado;
	
	@Autowired
	private IReservar reservaService;
	
	@Autowired
	private IUsuarioDetalleService usuarioDetalleService;

	
	
	
	@GetMapping("/listado")
	public String mostrarListaPedidos(
	        @RequestParam(required = false) String fechaInicio,
	        @RequestParam(required = false) String fechaFin,
	        @RequestParam(required = false) String fechaExacta,
	        @RequestParam(required = false) String cliente,
	        @RequestParam(required = false) String empleado,
	        Model model,
	        Authentication auth) {

	    List<Pedido> pedidos = new ArrayList<>();

	    // ----- SI ES MESERO, SOLO SUS PEDIDOS -----
	    boolean esMesero = auth.getAuthorities().stream()
	            .anyMatch(r -> r.getAuthority().equals("MESERO"));

	    if (esMesero) {

	        String username = auth.getName();
	        UsuarioDetalle detalle = usuarioDetalleService.buscarPorUsername(username);

	        if (detalle == null || detalle.getClaveEmpleado() == null) {
	            return "redirect:/empleado/completar-datos";
	        }

	        Empleado emp = serviceEmpleado.buscarPorClave(detalle.getClaveEmpleado());

	        if (emp == null) {
	            return "redirect:/empleado/completar-datos";
	        }

	        // 🔥 Ahora sí, pedir los pedidos del mesero REAL
	        pedidos = servicePedido.buscarPedidosPorEmpleado(emp.getClave());


	        model.addAttribute("pedidoLista", pedidos);
	        return "pedido/listaPedidos";
	    }

	    // ----- SI NO ES MESERO, FILTRA NORMAL -----
	    if (fechaExacta != null && !fechaExacta.isEmpty()) {
	        pedidos = servicePedido.buscarPorFechaExacta(LocalDate.parse(fechaExacta));
	    }

	    else if (fechaInicio != null && !fechaInicio.isEmpty() &&
	             fechaFin != null && !fechaFin.isEmpty()) {

	        pedidos = servicePedido.buscarPorRangoFechas(LocalDate.parse(fechaInicio), LocalDate.parse(fechaFin));
	    }

	    else if (cliente != null && !cliente.isEmpty()) {
	        pedidos = servicePedido.buscarPorCliente(cliente);
	    }

	    else if (empleado != null && !empleado.isEmpty()) {
	        pedidos = servicePedido.buscarPorEmpleado(empleado);
	    }

	    else {
	        pedidos = servicePedido.obtenerPedidos();
	    }

	    model.addAttribute("pedidoLista", pedidos);
	    return "pedido/listaPedidos";
	}


	
	
	
	@GetMapping("/crear")
	public String crearPedidos(Model model, Authentication auth) {

	    model.addAttribute("pedido", new Pedido());
	    model.addAttribute("productos", serviceProducto.bucarTodosProductos());
	    model.addAttribute("clientes", serviceCliente.bucarTodosClientes());

	    String username = auth.getName();

	    UsuarioDetalle detalle = usuarioDetalleService.buscarPorUsername(username);

	    if (detalle == null || detalle.getClaveEmpleado() == null) {
	        return "redirect:/empleado/completar-datos";
	    }

	    Empleado empleadoLogueado = serviceEmpleado.buscarPorClave(detalle.getClaveEmpleado());

	    String rol = auth.getAuthorities().iterator().next().getAuthority();
	    System.out.println("ROL ACTUAL: " + rol);

	    List<Empleado> listaEmpleados;
	    if (rol.equals("MESERO")) {
	        listaEmpleados = List.of(empleadoLogueado);
	    } 
	    else {
	        listaEmpleados = serviceEmpleado.BuscarPuestoMesero(1);
	    }

	    model.addAttribute("empleados", listaEmpleados);

	    return "pedido/crearPedidos";
	}


	
	
	@GetMapping("/reservas/cliente/{idCliente}")
	@ResponseBody
	public List<Reservar> obtenerReservasCliente(@PathVariable Integer idCliente) {
	    return reservaService.buscarClienteyEstatusyPedidoNull(idCliente, 2);
	}
	
	
	@GetMapping("/reservas/detalle/{id}")
	@ResponseBody
	public Reservar obtenerReservaDetalle(@PathVariable Integer id) {
	    return reservaService.obtenerReservaID(id);
	}
	
	
	@PostMapping("/guardar")
	public String guardarPedido(
	        @ModelAttribute Pedido pedido,
	        @RequestParam("productosSeleccionados") List<Integer> idsProductos,
	        @RequestParam("cantidades") List<Integer> cantidades,
	        @RequestParam("claveEmpleado") String claveEmpleado) {

	    // Si el pedido ya existe
	    Pedido pedidoExistente = null;
	    if (pedido.getIdPedido() != null) {
	        pedidoExistente = servicePedido.encontrarPedidoID(pedido.getIdPedido());
	    }

	    // Si no existe, es nuevo
	    if (pedidoExistente == null) {
	        pedidoExistente = new Pedido();
	    }


	    pedidoExistente.setFecha(pedido.getFecha());
	    pedidoExistente.setTotal(pedido.getTotal());


	    Cliente cliente = serviceCliente.buscarPorIdCliente(pedido.getCliente().getId());
	    pedidoExistente.setCliente(cliente);

	    //sociar reservación si se seleccionó
	    if (pedido.getReservar() != null && pedido.getReservar().getIdServicio() != null) {
	        Reservar reserva = reservaService.obtenerReservaID(pedido.getReservar().getIdServicio());
	        pedidoExistente.setReservar(reserva);
	        reservaService.guardarReserva(reserva);
	    }

	    //Limpiar detalles antiguos
	    if (pedidoExistente.getDetalles() == null) {
	        pedidoExistente.setDetalles(new ArrayList<>());
	    } else {
	        pedidoExistente.getDetalles().clear();
	    }

	    //Fusionar productos repetidos
	    Map<Integer, Integer> mapaProductos = new HashMap<>();
	    for (int i = 0; i < idsProductos.size(); i++) {
	        Integer idProd = idsProductos.get(i);
	        Integer cantidad = cantidades.get(i);
	        mapaProductos.merge(idProd, cantidad, Integer::sum);
	    }


	    for (Map.Entry<Integer, Integer> entry : mapaProductos.entrySet()) {
	        Producto producto = serviceProducto.buscarPorIdProducto(entry.getKey());

	        DetallePedido detalle = new DetallePedido();

	        DetallePedidoId detalleId = new DetallePedidoId();
	        detalleId.setIdPedido(pedidoExistente.getIdPedido());
	        detalleId.setIdProducto(producto.getId());
	        detalle.setId(detalleId);

	        detalle.setPedido(pedidoExistente);
	        detalle.setProducto(producto);
	        detalle.setCantidad(entry.getValue());
	        detalle.setSubtotal(producto.getPrecio() * entry.getValue());

	        pedidoExistente.getDetalles().add(detalle);
	    }

	   
	    Empleado empleado = serviceEmpleado.buscarPorClave(claveEmpleado);	
	    Atender atender = new Atender();
	    atender.setEmpleado(empleado);
	    atender.setPedido(pedidoExistente);

	    if (pedidoExistente.getAtenciones() == null) {
	        pedidoExistente.setAtenciones(new ArrayList<>());
	    } else {
	        pedidoExistente.getAtenciones().clear();
	    }

	    pedidoExistente.getAtenciones().add(atender);

	    servicePedido.guardarPedido(pedidoExistente);

	    return "redirect:/pedido/listado";
	}
	
	
	
	@GetMapping("/ticket/{id}")
	public void generarTicketPDF(@PathVariable("id") int idPedido, HttpServletResponse response)
	        throws IOException, com.itextpdf.text.DocumentException {

	    Pedido pedido = servicePedido.encontrarPedidoID(idPedido);
	    if (pedido == null) {
	        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Pedido no encontrado");
	        return;
	    }


	    float ticketWidth = 250f;
	    ByteArrayOutputStream tempStream = new ByteArrayOutputStream();

	    com.itextpdf.text.Document tempDoc = new com.itextpdf.text.Document(
	            new com.itextpdf.text.Rectangle(ticketWidth, com.itextpdf.text.PageSize.A4.getHeight()),
	            10, 10, 10, 10);
	    com.itextpdf.text.pdf.PdfWriter tempWriter = com.itextpdf.text.pdf.PdfWriter.getInstance(tempDoc, tempStream);
	    tempDoc.open();

	    com.itextpdf.text.Font fTitulo = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 10, com.itextpdf.text.Font.BOLD);
	    com.itextpdf.text.Font fNormal = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 8, com.itextpdf.text.Font.NORMAL);
	    com.itextpdf.text.Font fItalic = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 8, com.itextpdf.text.Font.ITALIC);

	    com.itextpdf.text.Paragraph contenido = new com.itextpdf.text.Paragraph();

	    try {
	        String logoPath = "src/main/resources/static/imagen/imagenesEmpresa/logoCortes.png";
	        com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance(logoPath);
	        logo.scaleToFit(80, 80);
	        logo.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
	        contenido.add(logo);
	    } catch (Exception e) {
	       
	    }

	    com.itextpdf.text.Paragraph encabezado = new com.itextpdf.text.Paragraph("LA MESA DEL AUTOR", fTitulo);
	    encabezado.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
	    contenido.add(encabezado);

	    com.itextpdf.text.Paragraph slogan = new com.itextpdf.text.Paragraph("Elegancia, estilo y precisión", fItalic);
	    slogan.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
	    contenido.add(slogan);
	    
	   com.itextpdf.text.Font fBold = new com.itextpdf.text.Font(
	    	    com.itextpdf.text.Font.FontFamily.HELVETICA, 8, com.itextpdf.text.Font.BOLD);
	   
	   com.itextpdf.text.Font fid = new com.itextpdf.text.Font(
	    	    com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.BOLD);




	    contenido.add(new com.itextpdf.text.Paragraph("\n", fNormal));


	    PdfPTable tablaEncabezado = new PdfPTable(2);
	    tablaEncabezado.setWidthPercentage(100);
	    tablaEncabezado.setWidths(new float[]{1.2f, 0.8f});
	    tablaEncabezado.getDefaultCell().setBorder(PdfPCell.NO_BORDER);


	    Phrase fraseTicket = new Phrase();
	    fraseTicket.add(new Chunk("Ticket de Pedido #", fBold));
	    fraseTicket.add(new Chunk(String.valueOf(pedido.getIdPedido()), fid));

	    // 🔹 Crear la celda
	    PdfPCell celdaTicket = new PdfPCell(fraseTicket);
	    celdaTicket.setBorder(PdfPCell.NO_BORDER);
	    celdaTicket.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tablaEncabezado.addCell(celdaTicket);




	    	PdfPCell celdaFecha = new PdfPCell(new Phrase("Fecha: " + pedido.getFecha(), fBold));
	    	celdaFecha.setBorder(PdfPCell.NO_BORDER);
	    	celdaFecha.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    	tablaEncabezado.addCell(celdaFecha);


	    contenido.add(tablaEncabezado);
	    contenido.add(new com.itextpdf.text.Paragraph("\n", fNormal));



	    String cliente = pedido.getCliente() != null
	            ? pedido.getCliente().getNombre() + " " + pedido.getCliente().getApellidos()
	            : "Cliente no asignado";
	    String empleado = (pedido.getAtenciones() != null && !pedido.getAtenciones().isEmpty())
	            ? pedido.getAtenciones().get(0).getEmpleado().getNombreCompleto()
	            : "Empleado no asignado";

	    contenido.add(new com.itextpdf.text.Paragraph("Cliente: " + cliente, fNormal));
	    contenido.add(new com.itextpdf.text.Paragraph("Atendió: " + empleado, fNormal));

	    if (pedido.getReservar() != null) {
	        Reservar r = pedido.getReservar();
	        contenido.add(new com.itextpdf.text.Paragraph("--------------------------------------------------------"));
	        contenido.add(new com.itextpdf.text.Paragraph("Reservación:", fTitulo));
	        if (r.getFecha() != null)
	            contenido.add(new com.itextpdf.text.Paragraph("Fecha: " + r.getFecha(), fNormal));
	        if (r.getHora() != null)
	            contenido.add(new com.itextpdf.text.Paragraph("Hora: " + r.getHora(), fNormal));
	        if (r.getMesa() != null)
	            contenido.add(new com.itextpdf.text.Paragraph("Mesa: " + r.getMesa().getUbicacion(), fNormal));
	        if (r.getEstatus() != null)
	            contenido.add(new com.itextpdf.text.Paragraph("Estatus: " + r.getEstatus(), fNormal));
	    }

	    contenido.add(new com.itextpdf.text.Paragraph("--------------------------------------------------------"));
	    contenido.add(new com.itextpdf.text.Paragraph("Productos:", fTitulo));

	    for (DetallePedido detalle : pedido.getDetalles()) {
	        String linea = String.format("%s  x%d   $%.2f",
	                detalle.getProducto().getNombre(),
	                detalle.getCantidad(),
	                detalle.getSubtotal());
	        contenido.add(new com.itextpdf.text.Paragraph(linea, fNormal));
	    }

	    
	    PdfPTable tablaTotales = new PdfPTable(2);
	    tablaTotales.setWidthPercentage(100);
	    tablaTotales.setWidths(new float[]{1.5f, 1f});


	    PdfPCell celdaLinea = new PdfPCell(new Phrase("-----------------------------------------------------------------------------------", fNormal));
	    celdaLinea.setColspan(2);
	    celdaLinea.setBorder(PdfPCell.NO_BORDER);
	    tablaTotales.addCell(celdaLinea);


	    PdfPCell celdaSubtotal = new PdfPCell(new Phrase("Subtotal:", fNormal));
	    celdaSubtotal.setBorder(PdfPCell.NO_BORDER);
	    tablaTotales.addCell(celdaSubtotal);

	    PdfPCell celdaSubtotalValor = new PdfPCell(new Phrase("$" + String.format("%.2f", pedido.getTotal()), fNormal));
	    celdaSubtotalValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    celdaSubtotalValor.setBorder(PdfPCell.NO_BORDER);
	    tablaTotales.addCell(celdaSubtotalValor);

	    
	    PdfPCell celdaTotal = new PdfPCell(new Phrase("TOTAL:", fTitulo));
	    celdaTotal.setBorder(PdfPCell.NO_BORDER);
	    tablaTotales.addCell(celdaTotal);

	    PdfPCell celdaTotalValor = new PdfPCell(new Phrase("$" + String.format("%.2f", pedido.getTotal()), fTitulo));
	    celdaTotalValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    celdaTotalValor.setBorder(PdfPCell.NO_BORDER);
	    tablaTotales.addCell(celdaTotalValor);

	    contenido.add(tablaTotales);

	    com.itextpdf.text.Paragraph pie = new com.itextpdf.text.Paragraph("", fNormal);
	    pie.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

	    pie.add(new com.itextpdf.text.Chunk("\n\n", fNormal));
	    pie.add(new com.itextpdf.text.Chunk("¡Gracias por su preferencia!\n", fItalic));
	    pie.add(new com.itextpdf.text.Chunk("LA MESA DEL AUTOR\n", fItalic));
	    pie.add(new com.itextpdf.text.Chunk("Tel: 744 555 4321 | Av. Revolución #21, Chilpancingo\n", fItalic));
	    pie.add(new com.itextpdf.text.Chunk("\nPara solicitar su factura, visite:\n", fNormal));
	    pie.add(new com.itextpdf.text.Chunk("www.lamesadelautor.mx/facturacion\n", fNormal));
	    pie.add(new com.itextpdf.text.Chunk("Dispone de 72 horas posteriores a su compra.\n", fItalic));
	    pie.add(new com.itextpdf.text.Chunk("-----------------------------------------------------------------------------------\n", fNormal));
	    pie.add(new com.itextpdf.text.Chunk("Este comprobante no es fiscal. Conserve para aclaraciones.\n", fNormal));
	    pie.add(new com.itextpdf.text.Chunk("Atención al cliente: contacto@lamesadelautor.mx\n", fItalic));

	    contenido.add(pie);
	    tempDoc.add(contenido);
	    tempDoc.close();

	    float used = tempWriter.getVerticalPosition(false);
	    float realHeight = (com.itextpdf.text.PageSize.A4.getHeight() - used) + 30;

	    response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "inline; filename=ticket_" + idPedido + ".pdf");

	    com.itextpdf.text.Rectangle finalSize = new com.itextpdf.text.Rectangle(ticketWidth, realHeight);
	    com.itextpdf.text.Document finalDoc = new com.itextpdf.text.Document(finalSize, 10, 10, 10, 10);
	    com.itextpdf.text.pdf.PdfWriter.getInstance(finalDoc, response.getOutputStream());
	    finalDoc.open();
	    finalDoc.add(contenido);
	    finalDoc.close();
	}











	
	
	@GetMapping("/eliminar/{id}")
	public String eliminarPedidos(@PathVariable("id") int idPedido) {
	    servicePedido.eliminarPedido(idPedido);
	    return "redirect:/pedido/listado";
	}
	
	
	@GetMapping("/ver/{id}")
	public String verdetalles(@PathVariable("id") int idPedido, Model model) {
	    Pedido pedido = servicePedido.encontrarPedidoID(idPedido);
	    model.addAttribute("pedido", pedido);
	    return "pedido/verDetallesPedido";
	}
	
	
	@GetMapping("/editar/{id}")
	public String editarPedido(@PathVariable("id") Integer idPedido, Model model) {
	    Pedido pedido = servicePedido.encontrarPedidoID(idPedido);
	    if (pedido == null) {
	        return "redirect:/pedido/listado";
	    }

	    model.addAttribute("pedido", pedido);
	    model.addAttribute("clientes", serviceCliente.bucarTodosClientes());
	    model.addAttribute("productos", serviceProducto.bucarTodosProductos());
	    model.addAttribute("empleados", serviceEmpleado.BuscarPuestoMesero(1));
	    model.addAttribute("pedidoLista", pedido.getDetalles());

	    
	    if (pedido.getCliente() != null) {
	        List<Reservar> reservasCliente = reservaService.buscarClienteyEstatusyPedidoNull(pedido.getCliente().getId(), 2);

	        
	        if (pedido.getReservar() != null && !reservasCliente.contains(pedido.getReservar())) {
	            reservasCliente.add(pedido.getReservar());
	        }

	        model.addAttribute("reservas", reservasCliente);
	    }

	    return "pedido/crearPedidos";
	}



}
