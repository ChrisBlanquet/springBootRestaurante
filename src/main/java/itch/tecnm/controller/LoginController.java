package itch.tecnm.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import itch.tecnm.model.Perfil;
import itch.tecnm.model.Usuario;
import itch.tecnm.service.IPerfilService;
import itch.tecnm.service.IUsuario;

@Controller
public class LoginController {

    @Autowired
    private IUsuario usuarioService;

    @Autowired
    private IPerfilService perfilService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // ==================================================
    //      LOGIN
    // ==================================================
    @GetMapping("/Login")
    public String login(Model model,
                        @RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        @RequestParam(value = "registrado", required = false) String registrado) {

        if (error != null) model.addAttribute("error", true);
        if (logout != null) model.addAttribute("logout", true);
        if (registrado != null) model.addAttribute("registrado", true);

        return "/login/loginform";
    }


    @GetMapping("/403")
    public String error403() {
        return "errores/error403";
    }


    // ==================================================
    //     FORMULARIO DE REGISTRO
    // ==================================================
    @GetMapping("/registro")
    public String formularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login/registro"; // Vista que te voy a generar si quieres
    }


    // ==================================================
    //     GUARDAR USUARIO (REGISTRO)
    // ==================================================
    @PostMapping("/registro/guardar")
    public String guardar(@ModelAttribute Usuario usuario) {

        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setEstatus(1);

        // Generar ID manual
        if (usuario.getId() == null) {
            Integer nuevoId = usuarioService.generarNuevoId();
            usuario.setId(nuevoId);
        }

        // Encriptar contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Asignar perfil CLIENTE (id = 7)
        Perfil perfilCliente = perfilService.BuscarPorId(7);
        usuario.agregarPerfilUsuario(perfilCliente);

        // Guardar usuario
        usuarioService.GuardarUsuario(usuario);

        return "redirect:/Login?registrado=true";
    }
}
