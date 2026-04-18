package com.valesraquel.proyecto_inter.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "practica")
public class Practica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "tutor_empresa_id")
    private Tutor tutorEmpresa;

    @ManyToOne
    @JoinColumn(name = "tutor_centro_id")
    private Tutor tutorCentro;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    public enum Estado {
        PENDIENTE, ACTIVA, FINALIZADA
    }

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