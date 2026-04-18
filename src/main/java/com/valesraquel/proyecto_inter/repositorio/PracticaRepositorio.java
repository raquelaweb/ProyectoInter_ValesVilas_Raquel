package com.valesraquel.proyecto_inter.repositorio;

import com.valesraquel.proyecto_inter.modelo.Alumno;
import com.valesraquel.proyecto_inter.modelo.Practica;
import com.valesraquel.proyecto_inter.modelo.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PracticaRepositorio extends JpaRepository<Practica, Integer> {
    List<Practica> findByAlumno(Alumno alumno);
    List<Practica> findByTutorEmpresa(Tutor tutorEmpresa);
    List<Practica> findByTutorCentro(Tutor tutorCentro);
}