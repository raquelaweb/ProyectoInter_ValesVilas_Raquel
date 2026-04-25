package com.valesraquel.proyecto_inter.controlador;

import com.valesraquel.proyecto_inter.modelo.Usuario;
import com.valesraquel.proyecto_inter.servicio.UsuarioServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Controlador que gestiona el login y la redirección según el rol del usuario
@Controller
public class LoginControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    // Muestra la página de login, con mensaje de error si los datos son incorrectos
    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Correo o contraseña incorrectos.");
        }
        return "login";
    }

    // Redirige al panel correspondiente según el rol del usuario que ha iniciado sesión
    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, HttpSession session) {
        String email = auth.getName();
        usuarioServicio.buscarPorEmail(email).ifPresent(u -> session.setAttribute("usuario", u));

        String rol = auth.getAuthorities().iterator().next().getAuthority();
        return switch (rol) {
            case "ADMIN" -> "redirect:/admin/panel";
            case "ALUMNO" -> "redirect:/alumno/panel";
            case "TUTOR_EMPRESA" -> "redirect:/tutorempresa/panel";
            case "TUTOR_CENTRO" -> "redirect:/tutorcentro/panel";
            default -> "redirect:/login";
        };
    }

    // Redirige la raíz al login
    @GetMapping("/")
    public String inicio() {
        return "redirect:/login";
    }
}