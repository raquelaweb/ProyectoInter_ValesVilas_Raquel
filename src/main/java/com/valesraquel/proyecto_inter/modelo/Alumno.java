package com.valesraquel.proyecto_inter.modelo;

import jakarta.persistence.*;

// Alumno es un tipo de usuario que realiza las prácticas en empresa
// Hereda los datos básicos de Usuario y añade su expediente y curso
@Entity
@Table(name = "alumno")
@PrimaryKeyJoinColumn(name = "id")
public class Alumno extends Usuario {

    private String expediente;
    private String curso;

    // Getters y setters
    public String getExpediente() { return expediente; }
    public void setExpediente(String expediente) { this.expediente = expediente; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
}