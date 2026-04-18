package com.valesraquel.proyecto_inter.controlador;

import com.valesraquel.proyecto_inter.modelo.Usuario;
import com.valesraquel.proyecto_inter.servicio.UsuarioServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {
        return usuarioServicio.login(email, password)
                .map(usuario -> {
                    session.setAttribute("usuario", usuario);
                    return switch (usuario.getRol()) {
                        case ADMIN -> "redirect:/admin/panel";
                        case ALUMNO -> "redirect:/alumno/panel";
                        case TUTOR_EMPRESA -> "redirect:/tutorempresa/panel";
                        case TUTOR_CENTRO -> "redirect:/tutorcentro/panel";
                    };
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "Correo o contraseña incorrectos.");
                    return "login";
                });
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/login";
    }
}