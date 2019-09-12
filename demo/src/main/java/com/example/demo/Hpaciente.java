package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

    interface Hpaciente extends JpaRepository<Paciente, Long> {

    }

