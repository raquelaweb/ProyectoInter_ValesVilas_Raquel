package com.valesraquel.proyecto_inter.repositorio;

import com.valesraquel.proyecto_inter.modelo.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repositorio para gestionar los tutores
public interface TutorRepositorio extends JpaRepository<Tutor, Integer> {
    // Filtra tutores por tipo: EMPRESA o CENTRO
    List<Tutor> findByTipo(Tutor.TipoTutor tipo);
}