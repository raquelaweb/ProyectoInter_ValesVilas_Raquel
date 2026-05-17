package com.valesraquel.proyecto_inter.servicio;

import java.util.List;
import com.valesraquel.proyecto_inter.modelo.Usuario;
import com.valesraquel.proyecto_inter.modelo.Alumno;
import com.valesraquel.proyecto_inter.modelo.Tutor;
import com.valesraquel.proyecto_inter.modelo.Practica;
import com.valesraquel.proyecto_inter.repositorio.AlumnoRepositorio;
import com.valesraquel.proyecto_inter.repositorio.DocumentoRepositorio;
import com.valesraquel.proyecto_inter.repositorio.PracticaRepositorio;
import com.valesraquel.proyecto_inter.repositorio.UsuarioRepositorio;
import com.valesraquel.proyecto_inter.repositorio.EvaluacionRepositorio;
import com.valesraquel.proyecto_inter.repositorio.SeguimientoRepositorio;
import com.valesraquel.proyecto_inter.repositorio.TutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired private UsuarioRepositorio repositorio;
    @Autowired private PracticaRepositorio practicaRepositorio;
    @Autowired private AlumnoRepositorio alumnoRepositorio;
    @Autowired private TutorRepositorio tutorRepositorio;
    @Autowired private EvaluacionRepositorio evaluacionRepositorio;
    @Autowired private SeguimientoRepositorio seguimientoRepositorio;
    @Autowired private DocumentoRepositorio documentoRepositorio;
    @Autowired private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repositorio.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                List.of(new SimpleGrantedAuthority(usuario.getRol().name()))
        );
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return repositorio.findByEmail(email);
    }

    public List<Usuario> listarTodos() {
        return repositorio.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return repositorio.findById(id);
    }

    @Transactional
    public Usuario guardar(Usuario usuario) {
        Usuario guardado = repositorio.save(usuario);
        repositorio.flush();

        if (usuario.getRol() == Usuario.Rol.ALUMNO) {
            if (!alumnoRepositorio.existsById(guardado.getId())) {
                jdbcTemplate.update("INSERT INTO alumno (id) VALUES (?)", guardado.getId());
            }
        } else if (usuario.getRol() == Usuario.Rol.TUTOR_EMPRESA) {
            if (!tutorRepositorio.existsById(guardado.getId())) {
                jdbcTemplate.update("INSERT INTO tutor (id, tipo) VALUES (?, 'EMPRESA')", guardado.getId());
            }
        } else if (usuario.getRol() == Usuario.Rol.TUTOR_CENTRO) {
            if (!tutorRepositorio.existsById(guardado.getId())) {
                jdbcTemplate.update("INSERT INTO tutor (id, tipo) VALUES (?, 'CENTRO')", guardado.getId());
            }
        }

        return guardado;
    }

    // Elimina un usuario y todos sus datos asociados para no romper las foreign keys
    public void eliminar(Integer id) {
        alumnoRepositorio.findById(id).ifPresent(alumno -> {
            List<Practica> practicas = practicaRepositorio.findByAlumno(alumno);
            practicas.forEach(p -> {
                documentoRepositorio.deleteAll(documentoRepositorio.findByPractica(p));
                evaluacionRepositorio.deleteAll(evaluacionRepositorio.findByPractica(p));
                seguimientoRepositorio.deleteAll(seguimientoRepositorio.findByPractica(p));
            });
            practicaRepositorio.deleteAll((Iterable<? extends Practica>) practicas);
        });
        repositorio.deleteById(id);
    }
}