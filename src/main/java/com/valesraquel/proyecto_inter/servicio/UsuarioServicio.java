package com.valesraquel.proyecto_inter.servicio;

import com.valesraquel.proyecto_inter.modelo.Usuario;
import com.valesraquel.proyecto_inter.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio repositorio;

    public List<Usuario> listarTodos() {
        return repositorio.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return repositorio.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return repositorio.findByEmail(email);
    }

    public Usuario guardar(Usuario usuario) {
        return repositorio.save(usuario);
    }

    public void eliminar(Integer id) {
        repositorio.deleteById(id);
    }

    public Optional<Usuario> login(String email, String password) {
        return repositorio.findByEmail(email)
                .filter(u -> u.getPassword().equals(password));
    }
}