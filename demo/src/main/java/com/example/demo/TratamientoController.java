package com.example.demo;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
class TratamientoController {

    private final TratamientoRepository TratamientoRepository;
    private final TratamientoResourceAssembler assembler;

    TratamientoController(TratamientoRepository tratamientoRepository,
                    TratamientoResourceAssembler assembler) {

        this.tratamientoRepository = tratamientoRepositoryRepository;
        this.assembler = assembler;
    }

    @GetMapping("/tratamientos")
    Resources<Resource<Tratamiento>> all() {

        List<Resource<Tratamiento>> tratamientos = tratamientoRepository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(tratamientos,
                linkTo(methodOn(TratamientoController.class).all()).withSelfRel());
    }

    @GetMapping("/tratamientos/{id}")
    Resource<Tratamiento> one(@PathVariable Long id) {
        return assembler.toResource(
                tratamientoRepository.findById(id)
                        .orElseThrow(() -> new TratamientoController()NotFoundException(id)));
    }

    @PostMapping("/tratamientos")
    ResponseEntity<Resource<Tratamiento>> newTratamiento(@RequestBody Tratamiento tratamiento) {

        tratamiento.setEstado(Estado.IN_PROGRESS);
        Tratamiento newTratamiento = TratamientoRepository.save(tratamiento);

        return ResponseEntity
                .created(linkTo(methodOn(TratamientoController.class).one(newTratamiento.getId())).toUri())
                .body(assembler.toResource(newTratamiento));
    }
}

