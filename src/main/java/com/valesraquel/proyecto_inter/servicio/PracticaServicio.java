package com.valesraquel.proyecto_inter.servicio;

import com.valesraquel.proyecto_inter.modelo.Alumno;
import com.valesraquel.proyecto_inter.modelo.Practica;
import com.valesraquel.proyecto_inter.repositorio.PracticaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PracticaServicio {

    @Autowired
    private PracticaRepositorio repositorio;

    public List<Practica> listarTodas() {
        return repositorio.findAll();
    }

    public List<Practica> buscarPorAlumno(Alumno alumno) {
        return repositorio.findByAlumno(alumno);
    }

    public Optional<Practica> buscarPorId(Integer id) {
        return repositorio.findById(id);
    }

    public Practica guardar(Practica practica) {
        return repositorio.save(practica);
    }
}