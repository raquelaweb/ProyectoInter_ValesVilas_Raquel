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

@Controller
public class LoginControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Correo o contraseña incorrectos.");
        }
        return "login";
    }

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

    @GetMapping("/")
    public String inicio() {
        return "redirect:/login";
    }
}