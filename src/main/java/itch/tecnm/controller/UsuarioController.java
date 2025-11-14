package itch.tecnm.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import itch.tecnm.model.Usuario;
import itch.tecnm.service.IPerfilService;
import itch.tecnm.service.IUsuario;

@Controller
@RequestMapping(value="/usuario")
public class UsuarioController {

    @Autowired
    private IUsuario serviceUsuario;

    @Autowired
    private IPerfilService servicePerfil;
    
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/listado")
    public String listarUsuarios(Model model) {
        List<Usuario> listaUsers = serviceUsuario.BuscarTodosLosUsuario();
        model.addAttribute("listaUsers", listaUsers);
        return "usuario/listaUsuarios";
    }


    @GetMapping("/nuevo")
    public String nuevoUsuario(Model model) {
        Usuario usuario = new Usuario();
        usuario.setFechaRegistro(LocalDateTime.now());
        model.addAttribute("usuario", usuario);
        model.addAttribute("listaPerfiles", servicePerfil.BuscarTodos());
        return "usuario/formUsuario";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable("id") Integer id, Model model) {
        Usuario usuario = serviceUsuario.BuscarUsuarioId(id);
        if (usuario == null) return "redirect:/usuario/listado";
        model.addAttribute("usuario", usuario);
        model.addAttribute("listaPerfiles", servicePerfil.BuscarTodos());
        return "usuario/formUsuario";
    }


    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        if (usuario.getFechaRegistro() == null) {
            usuario.setFechaRegistro(LocalDateTime.now());
        }
        
        String passwordPlano = usuario.getPassword();
        String passwordEncriptado = passwordEncoder.encode(passwordPlano);
        usuario.setPassword(passwordEncriptado);
        serviceUsuario.GuardarUsuario(usuario);
        return "redirect:/usuario/listado";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Integer id) {
        serviceUsuario.EliminarUsuario(id);
        return "redirect:/usuario/listado";
    }


    @GetMapping("/ver/{id}")
    public String verUsuario(@PathVariable("id") Integer id, Model model) {
        Usuario usuario = serviceUsuario.BuscarUsuarioId(id);
        if (usuario == null) return "redirect:/usuario/listado";
        model.addAttribute("usuario", usuario);
        return "usuario/verUsuario";
    }
}
