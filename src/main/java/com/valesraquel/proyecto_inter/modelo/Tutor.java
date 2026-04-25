package com.valesraquel.proyecto_inter.modelo;

import jakarta.persistence.*;

// Tutor es un tipo de usuario que supervisa las prácticas
// Puede ser tutor de empresa (valida horas y evalúa) o tutor de centro (supervisa)
@Entity
@Table(name = "tutor")
@PrimaryKeyJoinColumn(name = "id")
public class Tutor extends Usuario {

    // Indica si es tutor de empresa o de centro
    @Enumerated(EnumType.STRING)
    private TipoTutor tipo;

    // Solo aplica si es tutor de empresa
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    public enum TipoTutor {
        EMPRESA, CENTRO
    }

    // Getters y setters
    public TipoTutor getTipo() { return tipo; }
    public void setTipo(TipoTutor tipo) { this.tipo = tipo; }
    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
}