package itch.tecnm.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import itch.tecnm.model.UsuarioDetalle;
import itch.tecnm.service.IUsuarioDetalleService;

/*
import itch.tecnm.model.Cliente;
import itch.tecnm.service.impl.ClienteServiceImpl;
import java.util.LinkedList;
import java.util.List;
*/

@Controller

public class InicioController {
	/*
	@GetMapping("/home")
	public String mostrarHome(Model model) {
		String nombre="Christopher Blanquet Mendez";
		String correo="blanqiet@gmail.com";
		Date fechaActual=new Date();
		model.addAttribute("nombre", nombre);
		model.addAttribute("correo", correo);
		model.addAttribute("fecha", fechaActual);
		return "Inicio";
	}*/
	
	@Autowired
	private IUsuarioDetalleService usuarioDetalleService;
	
	@GetMapping("/cliente")
	public String datosCliente(Model model) {
		String nombre="Christopher blanquet";
		String correo="blanqiet@gmail.com";
		Double credito=13000.00;
		boolean vigente=true;
		Date fechaActual=new Date();
		
		model.addAttribute("nom", nombre);
		model.addAttribute("correo", correo);
		model.addAttribute("credito", credito);
		model.addAttribute("vigente", vigente);
		model.addAttribute("fecha", fechaActual);
		return "Cliente";
	}
	
	
	@GetMapping("/inicio")
	public String inicio(Authentication auth) {

	    if (auth == null) {
	        return "inicio";
	    }

	    String username = auth.getName();

	    boolean esAdmin = auth.getAuthorities().stream()
	            .anyMatch(r -> r.getAuthority().equals("ADMIN"));

	    boolean esSupervisor = auth.getAuthorities().stream()
	            .anyMatch(r -> r.getAuthority().equals("SUPERVISOR"));

	    boolean esCliente = auth.getAuthorities().stream()
	            .anyMatch(r -> r.getAuthority().equals("CLIENTE"));

	    boolean esEmpleado = auth.getAuthorities().stream()
	            .anyMatch(r -> 
	                r.getAuthority().equals("MESERO") ||
	                r.getAuthority().equals("COCINERO") ||
	                r.getAuthority().equals("CAJERO")
	            );

	    // ADMIN y SUPERVISOR NO necesitan datos
	    if (esAdmin || esSupervisor) {
	        return "inicio";
	    }

	    UsuarioDetalle detalle = usuarioDetalleService.buscarPorUsername(username);

	    // CLIENTE → debe tener idCliente
	    if (esCliente) {
	        if (detalle == null || detalle.getIdCliente() == null) {
	            return "redirect:/cliente/completar-datos";
	        }
	        return "inicio";
	    }

	    // EMPLEADO → debe tener claveEmpleado
	    if (esEmpleado) {
	        if (detalle == null || detalle.getClaveEmpleado() == null) {
	            return "redirect:/empleado/completar-datos";
	        }
	        return "inicio";
	    }

	    return "inicio";
	}



	
	
	/*
	@GetMapping("/listadocli")
	public String mostrarListaClientes(Model model) {
		
		ClienteServiceImpl cliente = new ClienteServiceImpl();
		List<Cliente> listaClientes=cliente.bucarTodosClientes();
		model.addAttribute("nombre",listaClientes.getFirst().getNombre());
		//Crear una instancia de tipo lista
		
		List<String> listac=new LinkedList<String>();
		
		//Agregando elemetnos a la lista
		
		listac.add("Eduardo Solano");
		listac.add("Melvin Villanueva Gomez");
		listac.add("Maritza Paola Cordova Garcia");
		
		
		model.addAttribute("cliente",listac);
		
		return "listaC";
	}*/
	
}
