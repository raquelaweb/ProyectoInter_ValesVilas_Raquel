package com.valesraquel.proyecto_inter.repositorio;

import com.valesraquel.proyecto_inter.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Repositorio principal de usuarios
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    // Busca un usuario por su email, que es lo que usamos para el login
    Optional<Usuario> findByEmail(String email);
}