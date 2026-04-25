package com.valesraquel.proyecto_inter.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

// Clase que representa una práctica en empresa asignada a un alumno
// Relaciona al alumno con la empresa y los dos tutores
@Entity
@Table(name = "practica")
public class Practica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Alumno que realiza la práctica
    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    // Empresa donde se realiza la práctica
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    // Tutor de la empresa que supervisa al alumno en el puesto
    @ManyToOne
    @JoinColumn(name = "tutor_empresa_id")
    private Tutor tutorEmpresa;

    // Tutor del centro educativo que hace el seguimiento
    @ManyToOne
    @JoinColumn(name = "tutor_centro_id")
    private Tutor tutorCentro;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    // Estado actual de la práctica
    @Enumerated(EnumType.STRING)
    private Estado estado;

    public enum Estado {
        PENDIENTE, ACTIVA, FINALIZADA
    }

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Alumno getAlumno() { return alumno; }
    public void setAlumno(Alumno alumno) { this.alumno = alumno; }
    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
    public Tutor getTutorEmpresa() { return tutorEmpresa; }
    public void setTutorEmpresa(Tutor tutorEmpresa) { this.tutorEmpresa = tutorEmpresa; }
    public Tutor getTutorCentro() { return tutorCentro; }
    public void setTutorCentro(Tutor tutorCentro) { this.tutorCentro = tutorCentro; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
}