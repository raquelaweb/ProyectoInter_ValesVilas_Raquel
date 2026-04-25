package com.valesraquel.proyecto_inter.controlador;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Clase que gestiona los errores de forma centralizada en toda la aplicación
@ControllerAdvice
public class GestorExcepciones {

    // Captura cualquier error general y redirige a la página de error
    @ExceptionHandler(Exception.class)
    public String manejarErrorGeneral(Exception ex, Model model) {
        model.addAttribute("mensaje", "Ha ocurrido un error inesperado: " + ex.getMessage());
        return "error";
    }

    // Captura errores de datos incorrectos
    @ExceptionHandler(IllegalArgumentException.class)
    public String manejarArgumentoInvalido(IllegalArgumentException ex, Model model) {
        model.addAttribute("mensaje", "Datos incorrectos: " + ex.getMessage());
        return "error";
    }
}