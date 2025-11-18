package itch.tecnm.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

import itch.tecnm.model.Cliente;
import itch.tecnm.model.UsuarioDetalle;
import itch.tecnm.service.IClienteService;
import itch.tecnm.service.IUsuarioDetalleService;

@Controller
@RequestMapping(value="/cliente")
public class ClienteController {


	@Autowired
	private IClienteService serviceCliente;
	
    @Autowired
    private IUsuarioDetalleService usuarioDetalleService;


	@GetMapping("/listadocli")
	public String mostrarListaClientes(Model model) {

		List<Cliente> lista = serviceCliente.bucarTodosClientes();
		model.addAttribute("clienteLista", lista);

		return "listaC";
	}

	@GetMapping("/ver/{id}")
	public String verDetalleCliente(@PathVariable("id") int idCliente, Model model) {
		// Primero se debe buscar
		Cliente cliente = serviceCliente.buscarPorIdCliente(idCliente);

		System.out.println("El cliente encontrado es " + cliente);
		model.addAttribute("clienteID", cliente);

		return "cliente/detalle";
	}


    @GetMapping("/crear")
    public String crearCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("origen", "admin");
        return "cliente/formCliente";
    }
	
    @GetMapping("/completar-datos")
    public String completarDatosCliente(Model model, Authentication auth) {

        String username = auth.getName();

        UsuarioDetalle detalle = usuarioDetalleService.buscarPorUsername(username);
        Cliente cliente;

        if (detalle == null) {
            detalle = new UsuarioDetalle();
            detalle.setUsername(username);
        }

        if (detalle.getIdCliente() == null) {
            cliente = new Cliente();
        } else {
            cliente = serviceCliente.buscarPorIdCliente(detalle.getIdCliente());
            if (cliente == null) cliente = new Cliente();
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("origen", "login");

        return "cliente/formCliente";
    }
	
	

	  @PostMapping("/guardar")
	    public String guardarCliente(
	            @ModelAttribute("cliente") Cliente cliente,
	            @RequestParam(value = "username", required = false) String usernameForm,
	            @RequestParam(value = "origen", required = false) String origen,
	            @RequestParam("archivo") MultipartFile multiPart,
	            Authentication auth) {

	        // ---------------------------------------
	        // 1. Guardado normal del cliente
	        // ---------------------------------------

	        if (cliente.getId() == null) {
	            serviceCliente.guardarCliente(cliente);
	        } else {
	            if (multiPart == null || multiPart.isEmpty()) {
	                Cliente actual = serviceCliente.buscarPorIdCliente(cliente.getId());
	                if (actual != null) cliente.setFotocliente(actual.getFotocliente());
	            }
	        }

	        if (!multiPart.isEmpty()) {
	            try {
	                String ruta = "C:\\Users\\cris_\\Pictures\\ProyectoSpring";

	                String original = multiPart.getOriginalFilename();
	                String extension = "";
	                if (original != null && original.contains(".")) {
	                    extension = original.substring(original.lastIndexOf("."));
	                }

	                String nombreBase = cliente.getNombre() + "_" + cliente.getId();
	                nombreBase = nombreBase.replaceAll("\\s+", "_");
	                String nombreImagen = nombreBase + extension;

	                Path carpeta = Paths.get(ruta);
	                Files.createDirectories(carpeta);
	                Path destino = carpeta.resolve(nombreImagen);
	                Files.copy(multiPart.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

	                cliente.setFotocliente(nombreImagen);

	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }

	        serviceCliente.guardarCliente(cliente);


	        // ---------------------------------------
	        // 2. Guardado en usuario_detalle
	        // ---------------------------------------
	        String usernameAsignado;

	        if ("login".equals(origen)) {
	            usernameAsignado = auth.getName();
	        } else {
	            usernameAsignado = usernameForm;
	        }

	        UsuarioDetalle detalle = new UsuarioDetalle();
	        detalle.setUsername(usernameAsignado);
	        detalle.setIdCliente(cliente.getId());
	        usuarioDetalleService.guardar(detalle);


	        // ---------------------------------------
	        // 3. Redirecciones
	        // ---------------------------------------
	        if ("login".equals(origen)) {
	            return "redirect:/inicio";
	        }

	        return "redirect:/cliente/listadocli";
	    }

	
	
	@PostMapping("/eliminar/{id}")
	public String eliminarCliente(@PathVariable("id") int idCliente) {
		serviceCliente.eliminarCliente(idCliente);
		return "redirect:/cliente/listadocli";
	}
	
	
	
	@GetMapping("/editar/{id}")
	public String editarCliente(@PathVariable Integer id, Model model) {
	    Cliente cliente = serviceCliente.buscarPorIdCliente(id);
	    model.addAttribute("cliente", cliente);
	    return "cliente/formCliente";
	}

}
