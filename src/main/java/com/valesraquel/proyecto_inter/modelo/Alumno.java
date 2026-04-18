package com.valesraquel.proyecto_inter.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "alumno")
@PrimaryKeyJoinColumn(name = "id")
public class Alumno extends Usuario {

    private String expediente;
    private String curso;

    public String getExpediente() { return expediente; }
    public void setExpediente(String expediente) { this.expediente = expediente; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
}