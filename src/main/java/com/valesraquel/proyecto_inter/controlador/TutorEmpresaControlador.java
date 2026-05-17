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
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/tutorempresa")
public class TutorEmpresaControlador {

    @Autowired private SeguimientoServicio seguimientoServicio;
    @Autowired private EvaluacionServicio evaluacionServicio;
    @Autowired private TutorRepositorio tutorRepositorio;
    @Autowired private PracticaRepositorio practicaRepositorio;
    @Autowired private DocumentoRepositorio documentoRepositorio;

    private static final String UPLOAD_DIR = "uploads/";

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
        tutorRepositorio.findById(usuario.getId()).ifPresent(t -> {
            List<Practica> practicas = practicaRepositorio.findByTutorEmpresa(t);
            model.addAttribute("practicas", practicas);
        });
        return "tutorempresa/horas";
    }

    @GetMapping("/horas/{id}")
    public String detallehoras(@PathVariable Integer id, HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        practicaRepositorio.findById(id).ifPresent(p -> {
            model.addAttribute("practica", p);
            model.addAttribute("seguimientos", seguimientoServicio.listarPorPractica(p));
        });
        return "tutorempresa/detallehoras";
    }

    @GetMapping("/horas/validar/{id}")
    public String validarHoras(@PathVariable Integer id,
                               @RequestParam Integer practicaId) {
        seguimientoServicio.validar(id);
        return "redirect:/tutorempresa/horas/" + practicaId;
    }

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

    @PostMapping("/evaluaciones/guardar")
    public String guardarEvaluacion(@ModelAttribute Evaluacion evaluacion,
                                    @RequestParam Integer practicaId,
                                    @RequestParam Integer tutorId) {
        practicaRepositorio.findById(practicaId).ifPresent(evaluacion::setPractica);
        tutorRepositorio.findById(tutorId).ifPresent(evaluacion::setTutor);
        evaluacionServicio.guardar(evaluacion);
        return "redirect:/tutorempresa/evaluaciones/" + practicaId;
    }

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

    @PostMapping("/documentos/guardar")
    public String guardarDocumento(@RequestParam String tipo,
                                   @RequestParam MultipartFile archivo,
                                   @RequestParam Integer practicaId,
                                   HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (archivo == null || archivo.isEmpty()) return "redirect:/tutorempresa/documentos/" + practicaId;

        String nombreFinal = archivo.getOriginalFilename();
        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();
            archivo.transferTo(new File(UPLOAD_DIR + UUID.randomUUID() + "_" + nombreFinal));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Optional<Practica> practica = practicaRepositorio.findById(practicaId);
        if (practica.isPresent()) {
            Documento doc = new Documento();
            doc.setPractica(practica.get());
            doc.setTipo(tipo);
            doc.setRuta(nombreFinal);
            doc.setFechaSubida(java.time.LocalDate.now());
            doc.setSubidoPor(usuario);
            documentoRepositorio.save(doc);
        }

        return "redirect:/tutorempresa/documentos/" + practicaId;
    }
}