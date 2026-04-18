package com.valesraquel.proyecto_inter.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "documento")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "practica_id")
    private Practica practica;

    private String tipo;
    private String ruta;
    private LocalDate fechaSubida;

    @ManyToOne
    @JoinColumn(name = "subido_por")
    private Usuario subidoPor;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Practica getPractica() { return practica; }
    public void setPractica(Practica practica) { this.practica = practica; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getRuta() { return ruta; }
    public void setRuta(String ruta) { this.ruta = ruta; }
    public LocalDate getFechaSubida() { return fechaSubida; }
    public void setFechaSubida(LocalDate fechaSubida) { this.fechaSubida = fechaSubida; }
    public Usuario getSubidoPor() { return subidoPor; }
    public void setSubidoPor(Usuario subidoPor) { this.subidoPor = subidoPor; }
}