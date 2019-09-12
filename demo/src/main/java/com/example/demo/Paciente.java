package com.example.demo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Paciente {

    private @Id @GeneratedValue Long id;
    private String nombre;
    private String role;

    Paciente(){}

    Paciente(String nombre, String rol) {
        this.nombre = nombre;
        this.role = rol;
    }
}
