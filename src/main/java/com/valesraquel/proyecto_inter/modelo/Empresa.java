package com.valesraquel.proyecto_inter.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

// Clase que representa una empresa colaboradora donde los alumnos hacen las prácticas
@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    // El CIF identifica a la empresa de forma única
    @NotBlank(message = "El CIF es obligatorio")
    @Column(unique = true)
    private String cif;

    private String direccion;
    private String contacto;
    private String email;

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCif() { return cif; }
    public void setCif(String cif) { this.cif = cif; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}