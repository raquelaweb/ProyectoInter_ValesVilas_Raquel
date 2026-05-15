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

    // Devuelve la práctica activa del tutor de centro, o la primera si no hay ninguna activa
    private Optional<Practica> getPracticaActiva(Tutor tutor) {
        List<Practica> practicas = practicaRepositorio.findByTutorCentro(tutor);
        return practicas.stream()
                .filter(p -> p.getEstado() == Practica.Estado.ACTIVA)
                .findFirst()
                .or(() -> practicas.stream().findFirst());
    }

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
        tutorRepositorio.findById(usuario.getId()).ifPresent(t -> {
            getPracticaActiva(t).ifPresent(practica -> {
                model.addAttribute("practica", practica);
                model.addAttribute("seguimientos", seguimientoServicio.listarPorPractica(practica));
                model.addAttribute("evaluaciones", evaluacionServicio.listarPorPractica(practica));
            });
        });
        return "tutorcentro/practicas";
    }

    // Valida una evaluación como revisada por el tutor de centro
    @GetMapping("/evaluaciones/validar/{id}")
    public String validarEvaluacion(@PathVariable Integer id) {
        evaluacionServicio.validar(id);
        return "redirect:/tutorcentro/practicas";
    }

    // Genera un informe resumen de la práctica activa asignada al tutor de centro
    @GetMapping("/informe")
    public String generarInforme(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        tutorRepositorio.findById(usuario.getId()).ifPresent(t -> {
            getPracticaActiva(t).ifPresent(practica -> {
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
        });
        return "tutorcentro/informe";
    }
}