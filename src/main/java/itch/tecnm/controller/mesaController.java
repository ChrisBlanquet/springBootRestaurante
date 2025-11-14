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

import itch.tecnm.model.Mesa;
import itch.tecnm.service.IMesa;


@Controller
@RequestMapping(value="/mesa")
public class mesaController {
    @Autowired
    
    private IMesa serviceMesa;

    @GetMapping("/listado")
    public String listarMesas(Model model) {
        List<Mesa> mesas = serviceMesa.listarTodasLasMesas();
        model.addAttribute("mesaLista", mesas);
        return "mesa/ListaMesas";
    }

    @GetMapping("/crear")
    public String crearMesa(Model model) {
        model.addAttribute("mesa", new Mesa());
        return "mesa/crearMesas";
    }

    @PostMapping("/guardar")
    public String guardarMesa(@ModelAttribute Mesa mesa) {
        serviceMesa.guardarMesa(mesa);
        return "redirect:/mesa/listado";
    }

    //EDITAR
    @GetMapping("/editar/{id}")
    public String editarMesa(@PathVariable("id") Integer idMesa, Model model) {
        Mesa mesa = serviceMesa.buscarMesaPorId(idMesa);
        if (mesa == null) {
            return "redirect:/mesa/listado";
        }
        model.addAttribute("mesa", mesa);
        return "mesa/crearMesas";
    }

    //ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarMesa(@PathVariable("id") Integer idMesa) {
        serviceMesa.eliminarMesa(idMesa);
        return "redirect:/mesa/listado";
    }
    
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable("id") Integer idMesa, Model model) {
        Mesa mesa = serviceMesa.buscarMesaPorId(idMesa);
        if (mesa == null) return "redirect:/mesa/listado";
        model.addAttribute("mesa", mesa);
        return "mesa/verMesa";
    }
}
