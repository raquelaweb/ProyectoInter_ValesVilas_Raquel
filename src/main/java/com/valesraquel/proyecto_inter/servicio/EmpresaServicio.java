package com.valesraquel.proyecto_inter.servicio;

import com.valesraquel.proyecto_inter.modelo.Empresa;
import com.valesraquel.proyecto_inter.repositorio.EmpresaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaServicio {

    @Autowired
    private EmpresaRepositorio repositorio;

    public List<Empresa> listarTodas() {
        return repositorio.findAll();
    }

    public Optional<Empresa> buscarPorId(Integer id) {
        return repositorio.findById(id);
    }

    public Empresa guardar(Empresa empresa) {
        return repositorio.save(empresa);
    }

    public void eliminar(Integer id) {
        repositorio.deleteById(id);
    }
}