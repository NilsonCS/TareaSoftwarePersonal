package com.example.demo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

    @Data
    @Entity
    class Paciente {

        private @Id @GeneratedValue Long id;
        private String pnombre;
        private String snombre;
        private String rol;

        Paciente() {}

        Paciente(String pnombre, String snombre, String rol) {
            this.pnombre = pnombre;
            this.snombre = snombre;
            this.rol = rol;
        }

        public String getName() {
            return this.pnombre + " " + this.snombre;
        }

        public void setName(String name) {
            String[] parts =name.split(" ");
            this.pnombre = parts[0];
            this.snombre = parts[1];
        }
    }






    // #### Muestra de empleados viejos y nuevos.
}
//
//    private @Id @GeneratedValue Long id;
//    private String nombre;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getNombre() {
//        return nombre;
//    }
//
//    public void setNombre(String nombre) {
//        this.nombre = nombre;
//    }
//
//    public String getRol() {
//        return rol;
//    }
//
//    public void setRol(String rol) {
//        this.rol = rol;
//    }
//
//    private String rol;
//
//    Paciente(){}
//
//    Paciente(String nombre, String rol) {
//        this.nombre = nombre;
//        this.rol = rol;
//    }
//}
