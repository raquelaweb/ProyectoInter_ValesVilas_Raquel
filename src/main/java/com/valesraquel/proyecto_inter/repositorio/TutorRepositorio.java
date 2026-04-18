package com.valesraquel.proyecto_inter.repositorio;

import com.valesraquel.proyecto_inter.modelo.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TutorRepositorio extends JpaRepository<Tutor, Integer> {
    List<Tutor> findByTipo(Tutor.TipoTutor tipo);
}