package com.aluracursos.Foro.Hub.domain.respuesta;

import org.antlr.v4.runtime.misc.NotNull;

public record DatosActualizaRespuesta(
        @NotNull
        Long id,
        String mensaje,
        String solucion
) {
}
