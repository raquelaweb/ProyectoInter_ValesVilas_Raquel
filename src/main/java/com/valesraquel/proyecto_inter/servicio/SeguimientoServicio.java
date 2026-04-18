package com.valesraquel.proyecto_inter.servicio;

import com.valesraquel.proyecto_inter.modelo.Practica;
import com.valesraquel.proyecto_inter.modelo.Seguimiento;
import com.valesraquel.proyecto_inter.repositorio.SeguimientoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeguimientoServicio {

    @Autowired
    private SeguimientoRepositorio repositorio;

    public List<Seguimiento> listarPorPractica(Practica practica) {
        return repositorio.findByPractica(practica);
    }

    public Seguimiento guardar(Seguimiento seguimiento) {
        return repositorio.save(seguimiento);
    }

    public void validar(Integer id) {
        repositorio.findById(id).ifPresent(s -> {
            s.setValidado(true);
            repositorio.save(s);
        });
    }
}
