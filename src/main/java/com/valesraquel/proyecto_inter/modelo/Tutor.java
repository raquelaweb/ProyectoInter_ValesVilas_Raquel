package com.valesraquel.proyecto_inter.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "tutor")
@PrimaryKeyJoinColumn(name = "id")
public class Tutor extends Usuario {

    @Enumerated(EnumType.STRING)
    private TipoTutor tipo;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    public enum TipoTutor {
        EMPRESA, CENTRO
    }

    public TipoTutor getTipo() { return tipo; }
    public void setTipo(TipoTutor tipo) { this.tipo = tipo; }
    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
}