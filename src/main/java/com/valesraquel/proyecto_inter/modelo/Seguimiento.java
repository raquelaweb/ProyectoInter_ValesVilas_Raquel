package com.valesraquel.proyecto_inter.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seguimiento")
public class Seguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "practica_id")
    private Practica practica;

    private LocalDate fecha;
    private Float horas;
    private String descripcion;
    private Boolean validado = false;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Practica getPractica() { return practica; }
    public void setPractica(Practica practica) { this.practica = practica; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Float getHoras() { return horas; }
    public void setHoras(Float horas) { this.horas = horas; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Boolean getValidado() { return validado; }
    public void setValidado(Boolean validado) { this.validado = validado; }
}