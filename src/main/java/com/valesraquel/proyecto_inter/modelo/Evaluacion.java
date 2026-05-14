package com.valesraquel.proyecto_inter.modelo;

import jakarta.persistence.*;

// Evaluación que el tutor de empresa hace sobre el alumno al finalizar las prácticas
@Entity
@Table(name = "evaluacion")
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Práctica que se está evaluando
    @ManyToOne
    @JoinColumn(name = "practica_id")
    private Practica practica;

    // Tutor que realiza la evaluación
    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    private Float nota;
    private String comentarios;

    // El tutor de centro puede marcar la evaluación como validada
    private Boolean validada = false;

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Practica getPractica() { return practica; }
    public void setPractica(Practica practica) { this.practica = practica; }
    public Tutor getTutor() { return tutor; }
    public void setTutor(Tutor tutor) { this.tutor = tutor; }
    public Float getNota() { return nota; }
    public void setNota(Float nota) { this.nota = nota; }
    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
    public Boolean getValidada() { return validada; }
    public void setValidada(Boolean validada) { this.validada = validada; }
}