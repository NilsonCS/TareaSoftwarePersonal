package com.example.demo;

import ch.qos.logback.core.status.Status;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Status getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    private String descripcion;
    private Status estado;

    Tratamiento() {}

    Tratamiento(String descripcion, Status estado) {

        this.descripcion = descripcion;
        this.estado = estado;
    }
}