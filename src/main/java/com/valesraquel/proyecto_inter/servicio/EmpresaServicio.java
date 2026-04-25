package com.valesraquel.proyecto_inter.servicio;

import com.valesraquel.proyecto_inter.modelo.Empresa;
import com.valesraquel.proyecto_inter.repositorio.EmpresaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Servicio que gestiona la lógica de negocio de las empresas
@Service
public class EmpresaServicio {

    @Autowired
    private EmpresaRepositorio repositorio;

    // Devuelve todas las empresas registradas
    public List<Empresa> listarTodas() {
        return repositorio.findAll();
    }

    // Busca una empresa por su id
    public Optional<Empresa> buscarPorId(Integer id) {
        return repositorio.findById(id);
    }

    // Guarda o actualiza una empresa
    public Empresa guardar(Empresa empresa) {
        return repositorio.save(empresa);
    }

    // Elimina una empresa por su id
    public void eliminar(Integer id) {
        repositorio.deleteById(id);
    }
}