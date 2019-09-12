package com.example.demo;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;


@Component
public class PacienteeResourceAssembler implements ResourceAssembler<Paciente, Resource<Paciente>> {
    @Override
    public Resource<Paciente> toResource(Paciente paciente) {

        return new Resource<>(paciente,
                linkTo(methodOn(PacienteController.class).one(paciente.getId())).withSelfRel(),
                linkTo(methodOn(PacienteController.class).all()).withRel("pacientes"));
    }


}