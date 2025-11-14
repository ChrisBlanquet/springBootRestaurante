package itch.tecnm.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import itch.tecnm.model.Perfil;
import itch.tecnm.service.IPerfilService;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private IPerfilService servicePerfil;

    @GetMapping("/listado")
    public String listarPerfiles(Model model) {
        List<Perfil> listaPerfiles = servicePerfil.BuscarTodos();
        model.addAttribute("listaPerfiles", listaPerfiles);
        return "perfil/listaPerfil";
    }

    @GetMapping("/nuevo")
    public String nuevoPerfil(Model model) {
        model.addAttribute("perfil", new Perfil());
        return "perfil/formPerfil";
    }

    @PostMapping("/guardar")
    public String guardarPerfil(@ModelAttribute Perfil perfil) {
        servicePerfil.Guardar(perfil);
        return "redirect:/perfil/listado";
    }


    @GetMapping("/editar/{id}")
    public String editarPerfil(@PathVariable("id") Integer id, Model model) {
    	Perfil perfil = servicePerfil.BuscarPorId(id);
        if (perfil == null) return "redirect:/perfil/listado";
        model.addAttribute("perfil", perfil);
        return "perfil/formPerfil";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPerfil(@PathVariable("id") Integer id) {
        servicePerfil.Eliminar(id);
        return "redirect:/perfil/listado";
    }
}
