package com.valesraquel.proyecto_inter.repositorio;

import com.valesraquel.proyecto_inter.modelo.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio para acceder a los datos de las empresas en la base de datos
public interface EmpresaRepositorio extends JpaRepository<Empresa, Integer> {
}