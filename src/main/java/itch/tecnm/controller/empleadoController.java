package itch.tecnm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import itch.tecnm.model.Empleado;
import itch.tecnm.service.IEmpleado;

@Controller
@RequestMapping(value="/empleado")
public class empleadoController {
	
	@Autowired
	private IEmpleado serviceEmpleado;
	
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
    public String guardarEmpleado(@ModelAttribute("empleado") Empleado empleado) {
        serviceEmpleado.guardarEmpleado(empleado);
        return "redirect:/empleado/listadoEmpleados";
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


}
