package com.aluracursos.Foro.Hub.domain.respuesta;

import java.time.LocalDateTime;

public record DatosListaRespuesta(
        Long id,
        String mensaje,
        String topico,
        String autor,
        String solucion,
        LocalDateTime fechaCreacion
) {
}
