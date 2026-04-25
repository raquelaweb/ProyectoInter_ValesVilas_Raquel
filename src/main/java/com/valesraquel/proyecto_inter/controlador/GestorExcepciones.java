package com.valesraquel.proyecto_inter.controlador;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GestorExcepciones {

    @ExceptionHandler(Exception.class)
    public String manejarErrorGeneral(Exception ex, Model model) {
        model.addAttribute("mensaje", "Ha ocurrido un error inesperado: " + ex.getMessage());
        return "error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String manejarArgumentoInvalido(IllegalArgumentException ex, Model model) {
        model.addAttribute("mensaje", "Datos incorrectos: " + ex.getMessage());
        return "error";
    }
}