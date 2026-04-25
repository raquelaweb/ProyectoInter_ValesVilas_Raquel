package com.valesraquel.proyecto_inter.repositorio;

import com.valesraquel.proyecto_inter.modelo.Seguimiento;
import com.valesraquel.proyecto_inter.modelo.Practica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repositorio para gestionar los registros de horas
public interface SeguimientoRepositorio extends JpaRepository<Seguimiento, Integer> {
    // Devuelve todos los registros de horas de una práctica
    List<Seguimiento> findByPractica(Practica practica);
}