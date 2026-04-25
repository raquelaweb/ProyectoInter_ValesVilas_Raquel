package com.valesraquel.proyecto_inter.repositorio;

import com.valesraquel.proyecto_inter.modelo.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio para acceder a los datos de los alumnos en la base de datos
public interface AlumnoRepositorio extends JpaRepository<Alumno, Integer> {
}