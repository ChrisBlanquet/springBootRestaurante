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


    // ============================
    //          LOGIN
    // ============================

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        @RequestParam(value = "registrado", required = false) String registrado) {

        if (error != null) model.addAttribute("error", true);
        if (logout != null) model.addAttribute("logout", true);
        if (registrado != null) model.addAttribute("registrado", true);

        return "login/loginform";
    }

    @GetMapping("/")
    public String raiz() {
        return "redirect:/login";
    }


    @GetMapping("/403")
    public String error403() {
        return "errores/error403";
    }


    // ============================
    //      REGISTRO
    // ============================

    @GetMapping("/registro")
    public String formularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login/registro";
    }

    @PostMapping("/registro/guardar")
    public String guardar(@ModelAttribute Usuario usuario, Model model) {

        if (usuarioService.existeUsername(usuario.getUsername())) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("errorUsername", "El nombre de usuario ya está en uso");
            return "login/registro";
        }

        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setEstatus(1);

        if (usuario.getId() == null) {
            usuario.setId(usuarioService.generarNuevoId());
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Perfil perfilCliente = perfilService.BuscarPorId(7);
        usuario.agregarPerfilUsuario(perfilCliente);

        usuarioService.GuardarUsuario(usuario);

        return "redirect:/login?registrado=true";
    }
}
