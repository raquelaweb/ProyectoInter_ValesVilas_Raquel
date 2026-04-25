package com.valesraquel.proyecto_inter.controlador;

import com.valesraquel.proyecto_inter.modelo.Practica;
import com.valesraquel.proyecto_inter.modelo.Tutor;
import com.valesraquel.proyecto_inter.modelo.Usuario;
import com.valesraquel.proyecto_inter.repositorio.PracticaRepositorio;
import com.valesraquel.proyecto_inter.repositorio.TutorRepositorio;
import com.valesraquel.proyecto_inter.servicio.EvaluacionServicio;
import com.valesraquel.proyecto_inter.servicio.SeguimientoServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Controlador que gestiona las funcionalidades del tutor de centro
@Controller
@RequestMapping("/tutorcentro")
public class TutorCentroControlador {

    @Autowired private SeguimientoServicio seguimientoServicio;
    @Autowired private EvaluacionServicio evaluacionServicio;
    @Autowired private TutorRepositorio tutorRepositorio;
    @Autowired private PracticaRepositorio practicaRepositorio;

    // Muestra el panel principal del tutor de centro
    @GetMapping("/panel")
    public String panel(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("usuario", session.getAttribute("usuario"));
        return "tutorcentro/panel";
    }

    // Muestra las prácticas asignadas al tutor de centro con su seguimiento y evaluaciones
    @GetMapping("/practicas")
    public String supervisarPracticas(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Optional<Tutor> tutor = tutorRepositorio.findById(usuario.getId());
        tutor.ifPresent(t -> {
            List<Practica> practicas = practicaRepositorio.findByTutorCentro(t);
            if (!practicas.isEmpty()) {
                Practica practica = practicas.get(0);
                model.addAttribute("practica", practica);
                model.addAttribute("seguimientos", seguimientoServicio.listarPorPractica(practica));
                model.addAttribute("evaluaciones", evaluacionServicio.listarPorPractica(practica));
            }
        });
        return "tutorcentro/practicas";
    }
}