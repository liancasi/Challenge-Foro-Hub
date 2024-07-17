package com.aluracursos.Foro.Hub.infra.errores;

public class Validacion extends RuntimeException {
    public Validacion(String s) {
        super(s);
    }
}
