package com.aluracursos.Foro.Hub.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record DatosEntradaTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        String idUsuario,
        String nombreCurso
) {

}
