package com.valesraquel.proyecto_inter.servicio;

import com.valesraquel.proyecto_inter.modelo.Practica;
import com.valesraquel.proyecto_inter.modelo.Seguimiento;
import com.valesraquel.proyecto_inter.repositorio.SeguimientoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// Servicio que gestiona los registros de horas de los alumnos
@Service
public class SeguimientoServicio {

    @Autowired
    private SeguimientoRepositorio repositorio;

    // Devuelve todos los registros de horas de una práctica
    public List<Seguimiento> listarPorPractica(Practica practica) {
        return repositorio.findByPractica(practica);
    }

    // Guarda un nuevo registro de horas
    public Seguimiento guardar(Seguimiento seguimiento) {
        return repositorio.save(seguimiento);
    }

    // Marca un registro de horas como validado
    public void validar(Integer id) {
        repositorio.findById(id).ifPresent(s -> {
            s.setValidado(true);
            repositorio.save(s);
        });
    }
}