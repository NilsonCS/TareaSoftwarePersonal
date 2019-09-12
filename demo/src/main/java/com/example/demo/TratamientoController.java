package com.example.demo;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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


    @DeleteMapping("/tratamientos/{id}/cancel")
    ResponseEntity<ResourceSupport> cancel(@PathVariable Long id) {

        Tratamiento tratamiento = tratamientoRepository.findById(id).orElseThrow(() -> new TratamientoNotFoundException(id));

        if (tratamiento.getEstado() == Estado.progreso) {
            tratamiento.setEstado(Estado.cancelado);
            return ResponseEntity.ok(assembler.toResource(tratamientoRepository.save(tratamiento)));
        }

        return ResponseEntity
                .status(HttpEstado.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't cancel an tratamiento that is in the " + tratamiento.getEstado() + " status"));
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


    @PutMapping("/tratamientos/{id}/completado")
    ResponseEntity<ResourceSupport> complete(@PathVariable Long id) {

        Tratamiento tratamiento = tratamientoRepository.findById(id).orElseThrow(() -> new TratamientoNotFoundException(id));

        if (tratamiento.getEstado() == Estado.progreso) {
            tratamiento.setEstado(Estado.completado);
            return ResponseEntity.ok(assembler.toResource(tratamientoRepository.save(tratamiento)));
        }

        return ResponseEntity
                .status(HttpEstado.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't complete an tratamiento that is in the " + tratamiento.getEstado() + " status"));
    }

    @PostMapping("/tratamientos")
    ResponseEntity<Resource<Tratamiento>> newTratamiento(@RequestBody Tratamiento tratamiento) {

        tratamiento.setEstado(Estado.progreso);
        Tratamiento newTratamiento = TratamientoRepository.save(tratamiento);

        return ResponseEntity
                .created(linkTo(methodOn(TratamientoController.class).one(newTratamiento.getId())).toUri())
                .body(assembler.toResource(newTratamiento));
    }
}

