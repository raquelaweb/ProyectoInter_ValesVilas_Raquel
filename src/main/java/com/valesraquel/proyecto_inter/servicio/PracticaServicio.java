package com.valesraquel.proyecto_inter.servicio;

import com.valesraquel.proyecto_inter.modelo.Alumno;
import com.valesraquel.proyecto_inter.modelo.Practica;
import com.valesraquel.proyecto_inter.repositorio.PracticaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Servicio que gestiona las prácticas en empresa
@Service
public class PracticaServicio {

    @Autowired
    private PracticaRepositorio repositorio;

    // Devuelve todas las prácticas
    public List<Practica> listarTodas() {
        return repositorio.findAll();
    }

    // Busca las prácticas de un alumno concreto
    public List<Practica> buscarPorAlumno(Alumno alumno) {
        return repositorio.findByAlumno(alumno);
    }

    // Busca una práctica por su id
    public Optional<Practica> buscarPorId(Integer id) {
        return repositorio.findById(id);
    }

    // Guarda o actualiza una práctica
    public Practica guardar(Practica practica) {
        return repositorio.save(practica);
    }
}