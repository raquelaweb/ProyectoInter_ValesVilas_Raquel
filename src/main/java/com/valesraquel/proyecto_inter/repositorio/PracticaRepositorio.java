package com.valesraquel.proyecto_inter.repositorio;

import com.valesraquel.proyecto_inter.modelo.Alumno;
import com.valesraquel.proyecto_inter.modelo.Practica;
import com.valesraquel.proyecto_inter.modelo.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repositorio para gestionar las prácticas
public interface PracticaRepositorio extends JpaRepository<Practica, Integer> {
    // Busca las prácticas de un alumno concreto
    List<Practica> findByAlumno(Alumno alumno);
    // Busca las prácticas asignadas a un tutor de empresa
    List<Practica> findByTutorEmpresa(Tutor tutorEmpresa);
    // Busca las prácticas asignadas a un tutor de centro
    List<Practica> findByTutorCentro(Tutor tutorCentro);
}