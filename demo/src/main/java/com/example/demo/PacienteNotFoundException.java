package com.example.demo;

class PacienteNotFoundException extends RuntimeException {

    PacienteNotFoundException(Long id) {
        super("No se pudo encontrar paciente " + id);
    }
}