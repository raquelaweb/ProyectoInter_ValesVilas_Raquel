package com.valesraquel.proyecto_inter.servicio;

import com.valesraquel.proyecto_inter.modelo.Evaluacion;
import com.valesraquel.proyecto_inter.modelo.Practica;
import com.valesraquel.proyecto_inter.repositorio.EvaluacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// Servicio que gestiona las evaluaciones de los alumnos
@Service
public class EvaluacionServicio {

    @Autowired
    private EvaluacionRepositorio repositorio;

    // Devuelve todas las evaluaciones de una práctica concreta
    public List<Evaluacion> listarPorPractica(Practica practica) {
        return repositorio.findByPractica(practica);
    }

    // Guarda una evaluación nueva
    public Evaluacion guardar(Evaluacion evaluacion) {
        return repositorio.save(evaluacion);
    }
}