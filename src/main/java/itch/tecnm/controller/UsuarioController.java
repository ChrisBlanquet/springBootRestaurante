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

        // SI TIENE ID ES EDICIÓN
        if (usuario.getId() != null) {

            Usuario existente = serviceUsuario.BuscarUsuarioId(usuario.getId());

            if (existente != null) {
                // 🔥 Mantener contraseña ENCRIPTADA existente
                usuario.setPassword(existente.getPassword());

                // Mantener fecha registro
                usuario.setFechaRegistro(existente.getFechaRegistro());
            } else {
                // Si no existe el usuario en DB, lo tratamos como NUEVO
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                usuario.setFechaRegistro(LocalDateTime.now());
                usuario.setEstatus(1);
            }

        } else {
            // REGISTRO NUEVO
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuario.setFechaRegistro(LocalDateTime.now());
            usuario.setEstatus(1);
        }

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
