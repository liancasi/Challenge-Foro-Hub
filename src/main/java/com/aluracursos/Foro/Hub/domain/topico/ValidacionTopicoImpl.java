package com.aluracursos.Foro.Hub.domain.topico;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class ValidacionTopicoImpl implements ValidacionTopico {

    @Override
    public void validar(DatosEntradaTopico datos) {
        if (datos.titulo() == null || datos.titulo().isEmpty()) {
            throw new ValidationException("El título no puede estar vacío");
        }

        if (datos.mensaje() == null || datos.mensaje().isEmpty()) {
            throw new ValidationException("El mensaje no puede estar vacío");
        }

        if (datos.titulo().length() < 5) {
            throw new ValidationException("El título debe tener al menos 5 caracteres");
        }


    }
}