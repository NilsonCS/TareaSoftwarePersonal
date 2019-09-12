package com.example.demo;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class TratamientoResourceAssembler implements ResourceAssembler<Tratamiento, Resource<Tratamiento>> {

    @Override
    public Resource<Tratamiento> toResource(Tratamiento tratamiento) {

        // Unconditional links to single-item resource and aggregate root

        Resource<Tratamiento> tratamientoResource = new Resource<>(tratamiento,
                linkTo(methodOn(TratamientoController.class).one(tratamiento.getId())).withSelfRel(),
                linkTo(methodOn(TratamientoController.class).all()).withRel("tratamientos")
        );

        // Conditional links based on state of the tratamiento

        if (tratamiento.getEstado() == Estado.progreso) {
            tratamientoResource.add(
                    linkTo(methodOn(TratamientoController.class)
                            .cancel(tratamiento.getId())).withRel("cancel"));
            tratamientoResource.add(
                    linkTo(methodOn(TratamientoController.class)
                            .complete(tratamiento.getId())).withRel("complete"));
        }

        return tratamientoResource;
    }
}
