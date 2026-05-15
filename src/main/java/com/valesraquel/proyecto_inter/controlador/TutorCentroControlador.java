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

    // Muestra todas las prácticas asignadas al tutor de centro
    @GetMapping("/practicas")
    public String supervisarPracticas(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        tutorRepositorio.findById(usuario.getId()).ifPresent(t -> {
            List<Practica> practicas = practicaRepositorio.findByTutorCentro(t);
            model.addAttribute("practicas", practicas);
        });
        return "tutorcentro/practicas";
    }

    // Muestra el detalle de una práctica concreta con seguimientos y evaluaciones
    @GetMapping("/practicas/{id}")
    public String detallePractica(@PathVariable Integer id, HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        practicaRepositorio.findById(id).ifPresent(practica -> {
            model.addAttribute("practica", practica);
            model.addAttribute("seguimientos", seguimientoServicio.listarPorPractica(practica));
            model.addAttribute("evaluaciones", evaluacionServicio.listarPorPractica(practica));
        });
        return "tutorcentro/detalle";
    }

    // Valida una evaluación como revisada por el tutor de centro
    @GetMapping("/evaluaciones/validar/{id}")
    public String validarEvaluacion(@PathVariable Integer id,
                                    @RequestParam Integer practicaId) {
        evaluacionServicio.validar(id);
        return "redirect:/tutorcentro/practicas/" + practicaId;
    }

    // Genera un informe resumen de una práctica concreta
    @GetMapping("/informe/{id}")
    public String generarInforme(@PathVariable Integer id, HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        practicaRepositorio.findById(id).ifPresent(practica -> {
            List<com.valesraquel.proyecto_inter.modelo.Seguimiento> seguimientos =
                    seguimientoServicio.listarPorPractica(practica);
            List<com.valesraquel.proyecto_inter.modelo.Evaluacion> evaluaciones =
                    evaluacionServicio.listarPorPractica(practica);
            float totalHoras = (float) seguimientos.stream()
                    .filter(s -> Boolean.TRUE.equals(s.getValidado()))
                    .mapToDouble(s -> s.getHoras() != null ? s.getHoras() : 0)
                    .sum();
            float mediaNotas = (float) evaluaciones.stream()
                    .filter(e -> e.getNota() != null)
                    .mapToDouble(com.valesraquel.proyecto_inter.modelo.Evaluacion::getNota)
                    .average().orElse(0);
            model.addAttribute("practica", practica);
            model.addAttribute("seguimientos", seguimientos);
            model.addAttribute("evaluaciones", evaluaciones);
            model.addAttribute("totalHoras", totalHoras);
            model.addAttribute("mediaNotas", mediaNotas);
        });
        return "tutorcentro/informe";
    }

    // Redirige al informe de la primera práctica activa (desde el menú)
    @GetMapping("/informe")
    public String generarInformeMenu(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        tutorRepositorio.findById(usuario.getId()).ifPresent(t -> {
            List<Practica> practicas = practicaRepositorio.findByTutorCentro(t);
            if (!practicas.isEmpty()) {
                model.addAttribute("practicas", practicas);
            }
        });
        return "tutorcentro/practicas";
    }
}