package com.valesraquel.proyecto_inter.repositorio;

import com.valesraquel.proyecto_inter.modelo.Evaluacion;
import com.valesraquel.proyecto_inter.modelo.Practica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repositorio para gestionar las evaluaciones
public interface EvaluacionRepositorio extends JpaRepository<Evaluacion, Integer> {
    // Devuelve todas las evaluaciones de una práctica concreta
    List<Evaluacion> findByPractica(Practica practica);
}