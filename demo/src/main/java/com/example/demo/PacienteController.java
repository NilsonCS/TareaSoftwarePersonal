package com.example.demo;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PacienteController {

    private final Hpaciente repository;

    PacienteController(Hpaciente repository) {
        this.repository = repository;
    }

    // Aggregate root

    @GetMapping("/pacientes")
    List<Paciente> all() {
        return repository.findAll();
    }

    @PostMapping("/pacientes")
    Paciente newPaciente(@RequestBody Paciente newPaciente) {
        return repository.save(newPaciente);
    }

    // Single item

    @GetMapping("/pacientes/{id}")
    Paciente one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundException(id));
    }

    @PutMapping("/pacientes/{id}")
    Paciente replacePaciente(@RequestBody Paciente newPaciente, @PathVariable Long id) {

        return repository.findById(id)
                .map(paciente -> {
                    paciente.setNombre(newPaciente.getNombre());
                    paciente.setRol(newPaciente.getRol());
                    return repository.save(paciente);
                })
                .orElseGet(() -> {
                    newPaciente.setId(id);
                    return repository.save(newPaciente);
                });
    }

    @DeleteMapping("/employees/{id}")
    void deletePaciente(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
