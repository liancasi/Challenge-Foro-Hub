package com.aluracursos.Foro.Hub.domain.topico;

import java.time.LocalDateTime;

public record DatosDetalleTopico(Long id,
                                 String titulo,
                                 String mensaje,
                                 LocalDateTime fechaCreacion,
                                 Boolean status,
                                 String autor,
                                 String curso) {

}
