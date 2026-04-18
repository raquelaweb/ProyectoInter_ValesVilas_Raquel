package com.valesraquel.proyecto_inter.controlador;

import com.valesraquel.proyecto_inter.modelo.Evaluacion;
import com.valesraquel.proyecto_inter.modelo.Practica;
import com.valesraquel.proyecto_inter.modelo.Seguimiento;
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

@Controller
@RequestMapping("/tutorempresa")
public class TutorEmpresaControlador {

    @Autowired private SeguimientoServicio seguimientoServicio;
    @Autowired private EvaluacionServicio evaluacionServicio;
    @Autowired private TutorRepositorio tutorRepositorio;
    @Autowired private PracticaRepositorio practicaRepositorio;

    @GetMapping("/panel")
    public String panel(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("usuario", session.getAttribute("usuario"));
        return "tutorempresa/panel";
    }

    @GetMapping("/horas")
    public String verHoras(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Optional<Tutor> tutor = tutorRepositorio.findById(usuario.getId());
        tutor.ifPresent(t -> {
            List<Practica> practicas = practicaRepositorio.findByTutorEmpresa(t);
            if (!practicas.isEmpty()) {
                Practica practica = practicas.get(0);
                model.addAttribute("practica", practica);
                model.addAttribute("seguimientos", seguimientoServicio.listarPorPractica(practica));
            }
        });
        return "tutorempresa/horas";
    }

    @GetMapping("/horas/validar/{id}")
    public String validarHoras(@PathVariable Integer id) {
        seguimientoServicio.validar(id);
        return "redirect:/tutorempresa/horas";
    }

    @GetMapping("/evaluaciones")
    public String verEvaluaciones(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Optional<Tutor> tutor = tutorRepositorio.findById(usuario.getId());
        tutor.ifPresent(t -> {
            List<Practica> practicas = practicaRepositorio.findByTutorEmpresa(t);
            if (!practicas.isEmpty()) {
                model.addAttribute("practica", practicas.get(0));
                model.addAttribute("evaluaciones", evaluacionServicio.listarPorPractica(practicas.get(0)));
            }
        });
        model.addAttribute("nuevaEvaluacion", new Evaluacion());
        return "tutorempresa/evaluaciones";
    }

    @PostMapping("/evaluaciones/guardar")
    public String guardarEvaluacion(@ModelAttribute Evaluacion evaluacion,
                                    @RequestParam Integer practicaId,
                                    @RequestParam Integer tutorId) {
        practicaRepositorio.findById(practicaId).ifPresent(evaluacion::setPractica);
        tutorRepositorio.findById(tutorId).ifPresent(evaluacion::setTutor);
        evaluacionServicio.guardar(evaluacion);
        return "redirect:/tutorempresa/evaluaciones";
    }
}