package itch.tecnm.controller;

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

import itch.tecnm.model.Empleado;
import itch.tecnm.model.UsuarioDetalle;
import itch.tecnm.service.IEmpleado;
import itch.tecnm.service.IUsuarioDetalleService;

@Controller
@RequestMapping(value="/empleado")
public class empleadoController {
	
	@Autowired
	private IEmpleado serviceEmpleado;
	
	@Autowired
	private IUsuarioDetalleService usuarioDetalleService;
	
	@GetMapping("/listadoEmpleados")
	public String mostrarListaEmpleados(Model model) {

		List<Empleado> listaEmpleado = serviceEmpleado.MostrartodosEmpledos();
		model.addAttribute("listaEmpleado", listaEmpleado);

		return "/empleado/listaEmpleado";
	}
	
	
	@GetMapping("/crearEmpleado")
	public String CrearEmpleado(Empleado empleado,Model model) {
		model.addAttribute("empleado", new Empleado());
		return "/empleado/crearEmpleado";
	}
	
	@PostMapping("/guardar")
	public String guardarEmpleado(
	        @ModelAttribute("empleado") Empleado empleado,
	        @RequestParam(value = "username", required = false) String usernameForm,
	        @RequestParam(value = "origen", required = false) String origen,
	        Authentication auth) {

	    // Guardar empleado normalmente
	    serviceEmpleado.guardarEmpleado(empleado);

	    String usernameAsignado;

	    // Caso 1: viene desde completar-datos (login)
	    if ("login".equals(origen)) {
	        usernameAsignado = auth.getName();
	    } 
	    // Caso 2: viene desde admin/supervisor
	    else {
	        usernameAsignado = usernameForm;
	    }

	    // Guardar en usuario_detalle
	    UsuarioDetalle detalle = new UsuarioDetalle();
	    detalle.setUsername(usernameAsignado);
	    detalle.setClaveEmpleado(empleado.getClave());
	    usuarioDetalleService.guardar(detalle);

	    if ("login".equals(origen)) {
	        return "redirect:/inicio"; // empleado termina de completar datos
	    }

	    return "redirect:/empleado/listadoEmpleados"; // admin/supervisor
	}

   
	
    @GetMapping("/editar/{clave}")
    public String editarEmpleado(@PathVariable("clave") String clave, Model model) {
        Empleado empleado = serviceEmpleado.buscarPorClave(clave);
        model.addAttribute("empleado", empleado);
        return "/empleado/crearEmpleado";
    }

    @GetMapping("/eliminar/{clave}")
    public String eliminarEmpleado(@PathVariable("clave") String clave) {
        serviceEmpleado.eliminarEmpleado(clave);
        return "redirect:/empleado/listadoEmpleados";		
    }
    
    
    
    @GetMapping("buscar")
    public String buscarEmpleados(@RequestParam(required = false) String nombre, Model model) {
        List<Empleado> listaEmpleado;

        if (nombre == null || nombre.trim().isEmpty()) {
            listaEmpleado = serviceEmpleado.MostrartodosEmpledos();
        } else {
            listaEmpleado = serviceEmpleado.buscarPorNombre(nombre);
        }

        model.addAttribute("listaEmpleado", listaEmpleado);
        model.addAttribute("param", Map.of("nombre", nombre));
        return "empleado/listaEmpleado";
    }
    
    @GetMapping("/completar-datos")
    public String completarDatosEmpleado(Model model, Authentication auth) {

        String username = auth.getName();

        UsuarioDetalle detalle = usuarioDetalleService.buscarPorUsername(username);

        Empleado empleado;

        if (detalle == null) {
            detalle = new UsuarioDetalle();
            detalle.setUsername(username);
        }

        if (detalle.getClaveEmpleado() == null) {
            empleado = new Empleado();
        } else {
            empleado = serviceEmpleado.buscarPorClave(detalle.getClaveEmpleado());
            if (empleado == null) {
                empleado = new Empleado();
            }
        }

        model.addAttribute("empleado", empleado);
        model.addAttribute("origen", "login");

        return "/empleado/crearEmpleado";
    }





    



}
