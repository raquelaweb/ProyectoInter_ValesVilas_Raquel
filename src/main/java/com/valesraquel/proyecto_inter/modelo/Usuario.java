package com.valesraquel.proyecto_inter.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// Clase que representa a un usuario del sistema
// Puede ser admin, alumno, tutor de empresa o tutor de centro
@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED) // herencia con tabla propia para cada subtipo
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    // El email es único y se usa para iniciar sesión
    @Email(message = "El email no es válido")
    @NotBlank(message = "El email es obligatorio")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    // El rol determina a qué partes de la app puede acceder el usuario
    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private Rol rol;

    // Roles disponibles en la aplicación
    public enum Rol {
        ADMIN, TUTOR_CENTRO, TUTOR_EMPRESA, ALUMNO
    }

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}