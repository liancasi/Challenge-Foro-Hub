package com.aluracursos.Foro.Hub.domain.topico;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


public interface ValidacionTopico {
    public void validar(DatosEntradaTopico datos);
}
