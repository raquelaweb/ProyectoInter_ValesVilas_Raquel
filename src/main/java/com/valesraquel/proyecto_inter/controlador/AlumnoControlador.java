package com.valesraquel.proyecto_inter.controlador;

import com.valesraquel.proyecto_inter.modelo.Alumno;
import com.valesraquel.proyecto_inter.modelo.Documento;
import com.valesraquel.proyecto_inter.modelo.Practica;
import com.valesraquel.proyecto_inter.modelo.Seguimiento;
import com.valesraquel.proyecto_inter.modelo.Usuario;
import com.valesraquel.proyecto_inter.repositorio.AlumnoRepositorio;
import com.valesraquel.proyecto_inter.repositorio.DocumentoRepositorio;
import com.valesraquel.proyecto_inter.servicio.EvaluacionServicio;
import com.valesraquel.proyecto_inter.servicio.PracticaServicio;
import com.valesraquel.proyecto_inter.servicio.SeguimientoServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Controlador que gestiona las funcionalidades del alumno
@Controller
@RequestMapping("/alumno")
public class AlumnoControlador {

    @Autowired private PracticaServicio practicaServicio;
    @Autowired private SeguimientoServicio seguimientoServicio;
    @Autowired private EvaluacionServicio evaluacionServicio;
    @Autowired private AlumnoRepositorio alumnoRepositorio;
    @Autowired private DocumentoRepositorio documentoRepositorio;

    // Muestra el panel principal del alumno
    @GetMapping("/panel")
    public String panel(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("usuario", session.getAttribute("usuario"));
        return "alumno/panel";
    }

    // Muestra las horas registradas y el formulario para añadir nuevas
    @GetMapping("/horas")
    public String verHoras(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Optional<Alumno> alumno = alumnoRepositorio.findById(usuario.getId());
        alumno.ifPresent(a -> {
            List<Practica> practicas = practicaServicio.buscarPorAlumno(a);
            if (!practicas.isEmpty()) {
                Practica p = practicas.get(0);
                model.addAttribute("practica", p);
                model.addAttribute("seguimientos", seguimientoServicio.listarPorPractica(p));
            }
        });
        model.addAttribute("nuevoSeguimiento", new Seguimiento());
        return "alumno/horas";
    }

    // Guarda un nuevo registro de horas
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

    // Muestra las evaluaciones recibidas por el alumno
    @GetMapping("/evaluaciones")
    public String verEvaluaciones(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Optional<Alumno> alumno = alumnoRepositorio.findById(usuario.getId());
        alumno.ifPresent(a -> {
            List<Practica> practicas = practicaServicio.buscarPorAlumno(a);
            if (!practicas.isEmpty()) {
                model.addAttribute("evaluaciones", evaluacionServicio.listarPorPractica(practicas.get(0)));
            }
        });
        return "alumno/evaluaciones";
    }

    // Muestra los documentos subidos por el alumno
    @GetMapping("/documentos")
    public String verDocumentos(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Optional<Alumno> alumno = alumnoRepositorio.findById(usuario.getId());
        alumno.ifPresent(a -> {
            List<Practica> practicas = practicaServicio.buscarPorAlumno(a);
            if (!practicas.isEmpty()) {
                Practica p = practicas.get(0);
                model.addAttribute("practica", p);
                model.addAttribute("documentos", documentoRepositorio.findByPractica(p));
            }
        });
        model.addAttribute("usuario", usuario);
        return "alumno/documentos";
    }

    // Guarda un nuevo documento registrado por el alumno
    @PostMapping("/documentos/guardar")
    public String guardarDocumento(@RequestParam String tipo,
                                   @RequestParam String nombre,
                                   @RequestParam Integer practicaId,
                                   HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        practicaServicio.buscarPorId(practicaId).ifPresent(p -> {
            Documento doc = new Documento();
            doc.setPractica(p);
            doc.setTipo(tipo);
            doc.setRuta(nombre);
            doc.setFechaSubida(java.time.LocalDate.now());
            doc.setSubidoPor(usuario);
            documentoRepositorio.save(doc);
        });
        return "redirect:/alumno/documentos";
    }
}