package com.valesraquel.proyecto_inter.repositorio;

import com.valesraquel.proyecto_inter.modelo.Documento;
import com.valesraquel.proyecto_inter.modelo.Practica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repositorio para gestionar los documentos subidos por los usuarios
public interface DocumentoRepositorio extends JpaRepository<Documento, Integer> {
    // Devuelve todos los documentos de una práctica concreta
    List<Documento> findByPractica(Practica practica);
}