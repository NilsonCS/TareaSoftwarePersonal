package com.example.demo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "Tratamiento")
class Tratamiento {

    private @Id @GeneratedValue Long id;

    private String descripcion;
    private Status estado;

    Tratamiento() {}

    Tratamiento(String descripcion, Status estado) {

        this.descripcion = descripcion;
        this.estado = estado;
    }
}