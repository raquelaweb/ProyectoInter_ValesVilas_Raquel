package com.valesraquel.proyecto_inter.servicio;

import com.valesraquel.proyecto_inter.modelo.Usuario;
import com.valesraquel.proyecto_inter.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio repositorio;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repositorio.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                List.of(new SimpleGrantedAuthority(usuario.getRol().name()))
        );
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return repositorio.findByEmail(email);
    }

    public List<Usuario> listarTodos() {
        return repositorio.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return repositorio.findById(id);
    }

    public Usuario guardar(Usuario usuario) {
        return repositorio.save(usuario);
    }

    public void eliminar(Integer id) {
        repositorio.deleteById(id);
    }
}