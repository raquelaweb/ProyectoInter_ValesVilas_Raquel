package com.valesraquel.proyecto_inter.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "evaluacion")
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "practica_id")
    private Practica practica;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    private Float nota;
    private String comentarios;

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
}