package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


public class PacienteNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(PacienteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String pacienteNotFoundHandler(PacienteNotFoundException ex) {
        return ex.getMessage();
    }
}
