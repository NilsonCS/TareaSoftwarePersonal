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

    private final PacienteResourceAssembler assembler;

    PacienteController(Hpaciente repository) {
        this.repository = repository;
    }

    PacienteController(PacienteRepository repository,
                       PacienteResourceAssembler assembler) {

        this.repository = repository;
        this.assembler = assembler;
    }
    // Aggregate root

    Resources<Resource<Paciente>> all() {

        List<Resource<Paciente>> employees = repository.findAll().stream()
                .map(paciente -> new Resource<>(paciente,
                        linkTo(methodOn(PacienteController.class).one(paciente.getId())).withSelfRel(),
                        linkTo(methodOn(PacienteController.class).all()).withRel("Pacientes")))
                .collect(Collectors.toList());

        return new Resources<>(pacientes,
                linkTo(methodOn(PacienteController.class).all()).withSelfRel());
    }


    // ###########Sin rest full#########
//    @GetMapping("/pacientes")
//    List<Paciente> all() {
//        return repository.findAll();
//    }



   // Post sin rest #################
//    @PostMapping("/pacientes")
//    Paciente newPaciente(@RequestBody Paciente newPaciente) {
//        return repository.save(newPaciente);
//    }

    //Post con rest ###########

    @PostMapping("/pacientes")
    ResponseEntity<?> newPaciente(@RequestBody Paciente newPaciente) throws URISyntaxException {

        Resource<Paciente> resource = assembler.toResource(repository.save(newPaciente));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    // Single item
// Mappign pacientes rest

    //########## get usando el ensamblador

    @GetMapping("/pacientes/{id}")
    Resource<Paciente> one(@PathVariable Long id) {

        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundException(id));

        return assembler.toResource(paciente);
    }

    @PutMapping("/pacientes/{id}")
    ResponseEntity<?> replacePaciente(@RequestBody Paciente newPaciente, @PathVariable Long id) throws URISyntaxException {

        Employee updatedPaciente = repository.findById(id)
                .map(paciente -> {
                    paciente.setNombre(newPaciente.getNombre());
                    paciente.setRol(newPaciente.getRol());
                    return repository.save(paciente);
                })
                .orElseGet(() -> {
                    newPaciente.setId(id);
                    return repository.save(newPaciente);
                });

        Resource<Paciente> resource = assembler.toResource(updatedPaciente);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }


//    @GetMapping("/pacientes/{id}")
//    Resource<Paciente> one(@PathVariable Long id) {
//
//        Paciente employee = repository.findById(id)
//                .orElseThrow(() -> new PacienteNotFoundException(id));
//
//        return new Resource<>(employee,
//                linkTo(methodOn(PacienteController.class).one(id)).withSelfRel(),
//                linkTo(methodOn(PacienteController.class).all()).withRel("employees"));
//    }

    @DeleteMapping("/pacientes/{id}")
    ResponseEntity<?> deletePaciente(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }


     // ###############  Mapping  pacientes no rest #########
//    @GetMapping("/pacientes/{id}")
//    Paciente one(@PathVariable Long id) {
//
//        return repository.findById(id)
//                .orElseThrow(() -> new PacienteNotFoundException(id));
//    }

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
