package com.valesraquel.proyecto_inter.servicio;

import com.valesraquel.proyecto_inter.modelo.Evaluacion;
import com.valesraquel.proyecto_inter.modelo.Practica;
import com.valesraquel.proyecto_inter.repositorio.EvaluacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EvaluacionServicio {

    @Autowired
    private EvaluacionRepositorio repositorio;

    public List<Evaluacion> listarPorPractica(Practica practica) {
        return repositorio.findByPractica(practica);
    }

    public Evaluacion guardar(Evaluacion evaluacion) {
        return repositorio.save(evaluacion);
    }
}