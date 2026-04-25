package com.valesraquel.proyecto_inter.controlador;

import com.valesraquel.proyecto_inter.modelo.Usuario;
import com.valesraquel.proyecto_inter.modelo.Empresa;
import com.valesraquel.proyecto_inter.modelo.Tutor;
import com.valesraquel.proyecto_inter.modelo.Alumno;
import com.valesraquel.proyecto_inter.modelo.Practica;
import com.valesraquel.proyecto_inter.servicio.UsuarioServicio;
import com.valesraquel.proyecto_inter.servicio.EmpresaServicio;
import com.valesraquel.proyecto_inter.servicio.PracticaServicio;
import com.valesraquel.proyecto_inter.repositorio.TutorRepositorio;
import com.valesraquel.proyecto_inter.repositorio.AlumnoRepositorio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired private UsuarioServicio usuarioServicio;
    @Autowired private EmpresaServicio empresaServicio;
    @Autowired private PracticaServicio practicaServicio;
    @Autowired private TutorRepositorio tutorRepositorio;
    @Autowired private AlumnoRepositorio alumnoRepositorio;

    // PANEL PRINCIPAL
    @GetMapping("/panel")
    public String panel(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("usuario", session.getAttribute("usuario"));
        return "admin/panel";
    }

    // GESTIÓN DE USUARIOS
    @GetMapping("/usuarios")
    public String listarUsuarios(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("usuarios", usuarioServicio.listarTodos());
        model.addAttribute("nuevoUsuario", new Usuario());
        return "admin/usuarios";
    }

    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        usuarioServicio.guardar(usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id) {
        usuarioServicio.eliminar(id);
        return "redirect:/admin/usuarios";
    }

    // GESTIÓN DE EMPRESAS
    @GetMapping("/empresas")
    public String listarEmpresas(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("empresas", empresaServicio.listarTodas());
        model.addAttribute("nuevaEmpresa", new Empresa());
        return "admin/empresas";
    }

    @PostMapping("/empresas/guardar")
    public String guardarEmpresa(@ModelAttribute Empresa empresa) {
        empresaServicio.guardar(empresa);
        return "redirect:/admin/empresas";
    }

    @GetMapping("/empresas/eliminar/{id}")
    public String eliminarEmpresa(@PathVariable Integer id) {
        empresaServicio.eliminar(id);
        return "redirect:/admin/empresas";
    }

    // ASIGNAR PRÁCTICAS
    @GetMapping("/practicas")
    public String listarPracticas(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("practicas", practicaServicio.listarTodas());
        model.addAttribute("alumnos", alumnoRepositorio.findAll());
        model.addAttribute("empresas", empresaServicio.listarTodas());
        model.addAttribute("tutoresEmpresa", tutorRepositorio.findByTipo(Tutor.TipoTutor.EMPRESA));
        model.addAttribute("tutoresCentro", tutorRepositorio.findByTipo(Tutor.TipoTutor.CENTRO));
        model.addAttribute("nuevaPractica", new Practica());
        return "admin/practicas";
    }

    @PostMapping("/practicas/guardar")
    public String guardarPractica(@RequestParam Integer alumnoId,
                                  @RequestParam Integer empresaId,
                                  @RequestParam Integer tutorEmpresaId,
                                  @RequestParam Integer tutorCentroId,
                                  @RequestParam String fechaInicio,
                                  @RequestParam String fechaFin) {
        Practica practica = new Practica();
        alumnoRepositorio.findById(alumnoId).ifPresent(practica::setAlumno);
        empresaServicio.buscarPorId(empresaId).ifPresent(practica::setEmpresa);
        tutorRepositorio.findById(tutorEmpresaId).ifPresent(practica::setTutorEmpresa);
        tutorRepositorio.findById(tutorCentroId).ifPresent(practica::setTutorCentro);
        practica.setFechaInicio(java.time.LocalDate.parse(fechaInicio));
        practica.setFechaFin(java.time.LocalDate.parse(fechaFin));
        practica.setEstado(Practica.Estado.ACTIVA);
        practicaServicio.guardar(practica);
        return "redirect:/admin/practicas";
    }
}