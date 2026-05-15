package com.valesraquel.proyecto_inter.controlador;

import com.valesraquel.proyecto_inter.modelo.Documento;
import com.valesraquel.proyecto_inter.modelo.Evaluacion;
import com.valesraquel.proyecto_inter.modelo.Practica;
import com.valesraquel.proyecto_inter.modelo.Tutor;
import com.valesraquel.proyecto_inter.modelo.Usuario;
import com.valesraquel.proyecto_inter.repositorio.DocumentoRepositorio;
import com.valesraquel.proyecto_inter.repositorio.PracticaRepositorio;
import com.valesraquel.proyecto_inter.repositorio.TutorRepositorio;
import com.valesraquel.proyecto_inter.servicio.EvaluacionServicio;
import com.valesraquel.proyecto_inter.servicio.SeguimientoServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

// Controlador que gestiona las funcionalidades del tutor de empresa
@Controller
@RequestMapping("/tutorempresa")
public class TutorEmpresaControlador {

    @Autowired private SeguimientoServicio seguimientoServicio;
    @Autowired private EvaluacionServicio evaluacionServicio;
    @Autowired private TutorRepositorio tutorRepositorio;
    @Autowired private PracticaRepositorio practicaRepositorio;
    @Autowired private DocumentoRepositorio documentoRepositorio;

    private static final String UPLOAD_DIR = "uploads/";

    // Muestra el panel principal del tutor de empresa
    @GetMapping("/panel")
    public String panel(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("usuario", session.getAttribute("usuario"));
        return "tutorempresa/panel";
    }

    // Muestra todas las prácticas asignadas al tutor de empresa
    @GetMapping("/horas")
    public String verHoras(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        tutorRepositorio.findById(usuario.getId()).ifPresent(t -> {
            List<Practica> practicas = practicaRepositorio.findByTutorEmpresa(t);
            model.addAttribute("practicas", practicas);
        });
        return "tutorempresa/horas";
    }

    // Muestra el detalle de horas de una práctica concreta
    @GetMapping("/horas/{id}")
    public String detallehoras(@PathVariable Integer id, HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        practicaRepositorio.findById(id).ifPresent(p -> {
            model.addAttribute("practica", p);
            model.addAttribute("seguimientos", seguimientoServicio.listarPorPractica(p));
        });
        return "tutorempresa/detallehoras";
    }

    // Marca un registro de horas como validado
    @GetMapping("/horas/validar/{id}")
    public String validarHoras(@PathVariable Integer id,
                               @RequestParam Integer practicaId) {
        seguimientoServicio.validar(id);
        return "redirect:/tutorempresa/horas/" + practicaId;
    }

    // Muestra todas las prácticas para evaluar
    @GetMapping("/evaluaciones")
    public String verEvaluaciones(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("usuario", session.getAttribute("usuario"));
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        tutorRepositorio.findById(usuario.getId()).ifPresent(t -> {
            List<Practica> practicas = practicaRepositorio.findByTutorEmpresa(t);
            model.addAttribute("practicas", practicas);
        });
        model.addAttribute("nuevaEvaluacion", new Evaluacion());
        return "tutorempresa/evaluaciones";
    }

    // Muestra el detalle de evaluaciones de una práctica concreta
    @GetMapping("/evaluaciones/{id}")
    public String detalleEvaluaciones(@PathVariable Integer id, HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("usuario", session.getAttribute("usuario"));
        practicaRepositorio.findById(id).ifPresent(p -> {
            model.addAttribute("practica", p);
            model.addAttribute("evaluaciones", evaluacionServicio.listarPorPractica(p));
        });
        model.addAttribute("nuevaEvaluacion", new Evaluacion());
        return "tutorempresa/detalleevaluaciones";
    }

    // Guarda una evaluación nueva del alumno
    @PostMapping("/evaluaciones/guardar")
    public String guardarEvaluacion(@ModelAttribute Evaluacion evaluacion,
                                    @RequestParam Integer practicaId,
                                    @RequestParam Integer tutorId) {
        practicaRepositorio.findById(practicaId).ifPresent(evaluacion::setPractica);
        tutorRepositorio.findById(tutorId).ifPresent(evaluacion::setTutor);
        evaluacionServicio.guardar(evaluacion);
        return "redirect:/tutorempresa/evaluaciones/" + practicaId;
    }

    // Muestra todas las prácticas para gestionar documentos
    @GetMapping("/documentos")
    public String verDocumentos(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        tutorRepositorio.findById(usuario.getId()).ifPresent(t -> {
            List<Practica> practicas = practicaRepositorio.findByTutorEmpresa(t);
            model.addAttribute("practicas", practicas);
        });
        return "tutorempresa/documentos";
    }

    // Muestra el detalle de documentos de una práctica concreta
    @GetMapping("/documentos/{id}")
    public String detalleDocumentos(@PathVariable Integer id, HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("usuario", session.getAttribute("usuario"));
        practicaRepositorio.findById(id).ifPresent(p -> {
            model.addAttribute("practica", p);
            model.addAttribute("documentos", documentoRepositorio.findByPractica(p));
        });
        return "tutorempresa/detalledocumentos";
    }

    // Guarda un documento subido por el tutor de empresa
    @PostMapping("/documentos/guardar")
    public String guardarDocumento(@RequestParam String tipo,
                                   @RequestParam MultipartFile archivo,
                                   @RequestParam Integer practicaId,
                                   HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        practicaRepositorio.findById(practicaId).ifPresent(p -> {
            if (archivo != null && !archivo.isEmpty()) {
                try {
                    File dir = new File(UPLOAD_DIR);
                    if (!dir.exists()) dir.mkdirs();
                    String nombreUnico = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
                    archivo.transferTo(new File(UPLOAD_DIR + nombreUnico));
                    Documento doc = new Documento();
                    doc.setPractica(p);
                    doc.setTipo(tipo);
                    doc.setRuta(nombreUnico);
                    doc.setFechaSubida(java.time.LocalDate.now());
                    doc.setSubidoPor(usuario);
                    documentoRepositorio.save(doc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return "redirect:/tutorempresa/documentos/" + practicaId;
    }
}