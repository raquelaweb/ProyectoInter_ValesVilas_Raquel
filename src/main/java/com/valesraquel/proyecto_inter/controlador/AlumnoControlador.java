package com.valesraquel.proyecto_inter.controlador;

import com.valesraquel.proyecto_inter.modelo.Alumno;
import com.valesraquel.proyecto_inter.modelo.Practica;
import com.valesraquel.proyecto_inter.modelo.Seguimiento;
import com.valesraquel.proyecto_inter.modelo.Usuario;
import com.valesraquel.proyecto_inter.repositorio.AlumnoRepositorio;
import com.valesraquel.proyecto_inter.servicio.EvaluacionServicio;
import com.valesraquel.proyecto_inter.servicio.PracticaServicio;
import com.valesraquel.proyecto_inter.servicio.SeguimientoServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/alumno")
public class AlumnoControlador {

    @Autowired private PracticaServicio practicaServicio;
    @Autowired private SeguimientoServicio seguimientoServicio;
    @Autowired private EvaluacionServicio evaluacionServicio;
    @Autowired private AlumnoRepositorio alumnoRepositorio;

    @GetMapping("/panel")
    public String panel(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("usuario", session.getAttribute("usuario"));
        return "alumno/panel";
    }

    @GetMapping("/horas")
    public String verHoras(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Optional<Alumno> alumno = alumnoRepositorio.findById(usuario.getId());
        alumno.ifPresent(a -> {
            practicaServicio.buscarPorAlumno(a).ifPresent(p -> {
                model.addAttribute("practica", p);
                model.addAttribute("seguimientos", seguimientoServicio.listarPorPractica(p));
            });
        });
        model.addAttribute("nuevoSeguimiento", new Seguimiento());
        return "alumno/horas";
    }

    @PostMapping("/horas/guardar")
    public String guardarHoras(@ModelAttribute Seguimiento seguimiento,
                               @RequestParam Integer practicaId,
                               HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        practicaServicio.buscarPorId(practicaId).ifPresent(p -> {
            seguimiento.setPractica(p);
            seguimiento.setValidado(false);
            seguimientoServicio.guardar(seguimiento);
        });
        return "redirect:/alumno/horas";
    }

    @GetMapping("/evaluaciones")
    public String verEvaluaciones(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Optional<Alumno> alumno = alumnoRepositorio.findById(usuario.getId());
        alumno.ifPresent(a -> {
            practicaServicio.buscarPorAlumno(a).ifPresent(p -> {
                model.addAttribute("evaluaciones", evaluacionServicio.listarPorPractica(p));
            });
        });
        return "alumno/evaluaciones";
    }
}