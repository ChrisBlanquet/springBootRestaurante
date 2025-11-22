	package itch.tecnm.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import itch.tecnm.model.Producto;
import itch.tecnm.service.IProductoService;

@Controller
@RequestMapping(value="/producto")
public class ProductoController {
	
	@Autowired
	private IProductoService serviceProduct;

	@GetMapping("/listaProducto")
	public String mostrarListaProducto(Model model) {
		List<Producto> lista=serviceProduct.bucarTodosProductos();
		model.addAttribute("listaProducto",lista);
		return "productoC/listaProductoC";
	}
	
	
	@GetMapping("/verProducto/{id}")
	public String verDetalleProducto(@PathVariable("id") int idProducto,Model model) {
		
		Producto producto=serviceProduct.buscarPorIdProducto(idProducto);
		
		System.out.println("El producto encontrado es "+producto);
		model.addAttribute("productoID",producto);
		
		return "productoC/detalleProducto";
	}
	
	@GetMapping("/listaProductoAdmin/verProducto/{id}")
	public String verDetalleProductoAdmin(@PathVariable("id") int idProducto,Model model) {
		
		Producto producto=serviceProduct.buscarPorIdProducto(idProducto);
		
		System.out.println("El producto encontrado es "+producto);
		model.addAttribute("productoID",producto);
		
		return "productoC/detalleProductoAdmin";
	}
	
	
	@GetMapping("/listaProductoAdmin")
	public String mostrarListaProductoAdmin(
	        @RequestParam(required = false) String nombre,
	        @RequestParam(required = false) Integer tipo,
	        Model model) {

	    List<Producto> lista;

	    if (nombre != null && !nombre.isEmpty() && tipo != null) {
	        lista = serviceProduct.buscarPorNombreYTipo(nombre, tipo);
	    } else if (nombre != null && !nombre.isEmpty()) {
	        lista = serviceProduct.buscarPorNombre(nombre);

	    } else if (tipo != null) {
	        lista = serviceProduct.buscarPorTipo(tipo);

	    } else {
	        lista = serviceProduct.bucarTodosProductos();
	    }

	    model.addAttribute("listaProducto", lista);
	    return "productoC/listaProductoAdmin";
	}

	
	
	// crear Producto
	@GetMapping("/crearProducto")
	public String crearProdcuto(Model model) {
		model.addAttribute("producto", new Producto());
		return "productoC/formProducto";
	}
	
	@PostMapping("/guardarProducto")
	public String guardarProducto(Producto producto,
	                              @RequestParam("archivo") MultipartFile multiPart) {
		
		if(producto.getId()==null) {
			serviceProduct.guardarProducto(producto);
		}else {
	        if (multiPart == null || multiPart.isEmpty()) {
	            Producto actual = serviceProduct.buscarPorIdProducto(producto.getId());
	            if (actual != null) {
	                producto.setFoto(actual.getFoto());
	            }
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

	            String nombreBase = "Prodcuto_"+producto.getNombre() + "_" + producto.getId();
	            nombreBase = nombreBase.replaceAll("\\s+", "_");
	            String nombreImagen = nombreBase + extension;

	            Path carpeta = Paths.get(ruta);
	            Files.createDirectories(carpeta);
	            Path destino = carpeta.resolve(nombreImagen);
	            Files.copy(multiPart.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

	            producto.setFoto(nombreImagen);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    serviceProduct.guardarProducto(producto);
	    return "redirect:/producto/listaProductoAdmin";
	}
	
	
	
	@GetMapping("/eliminarProducto/{id}")
	public String EliminarProducto(@PathVariable("id") int idProducto,Model model) {
		
		Producto producto=serviceProduct.buscarPorIdProducto(idProducto);
		
		if(producto!=null) {
			serviceProduct.eliminarProducto(producto.getId());
		}
		
		return "redirect:/producto/listaProductoAdmin";
	}
	
	
	@GetMapping("/editarProducto/{id}")
	public String editarProdcuto(@PathVariable("id") int idProducto,Model model) {
		
		Producto producto=serviceProduct.buscarPorIdProducto(idProducto);
		
		if(producto!=null) {
			model.addAttribute("producto",producto);
		}
		
		
		
		return "productoC/formProducto";
	}
	
	
	@GetMapping("/buscar")
	public String buscarProductos(
	        @RequestParam(required = false) Integer tipo,
	        @RequestParam(required = false) Double precioMin,
	        @RequestParam(required = false) Double precioMax,
	        Model model) {

	    List<Producto> listaProducto;

	    if (tipo == null && precioMin != null && precioMax != null) {
	        listaProducto = serviceProduct.buscarPorRango(precioMin, precioMax);
	    } else if (tipo != null && precioMin == null && precioMax == null) {
	        listaProducto = serviceProduct.buscarPorTipo(tipo);
	    } else if (tipo != null && precioMin != null && precioMax != null) {
	        listaProducto = serviceProduct.buscarPorTipoYRango(tipo, precioMin, precioMax);
	    } else {
	        listaProducto = serviceProduct.bucarTodosProductos();
	    }

	    model.addAttribute("listaProducto", listaProducto);
	    model.addAttribute("tipo", tipo);
	    model.addAttribute("precioMin", precioMin);
	    model.addAttribute("precioMax", precioMax);

	    return "productoC/listaProductoC";
	}
	
}
