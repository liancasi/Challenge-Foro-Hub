package com.aluracursos.Foro.Hub.domain.topico;


import java.time.LocalDateTime;


public record DatosListaTopico(Long id,
                               String titulo,
                               String mensaje,
                               String autor,
                               String curso,
                               LocalDateTime fechaCreacion) {

    public DatosListaTopico(Topico topico) {
        this(topico.getId(),topico.getTitulo(), topico.getMensaje().toString(),topico.getAutor().getNombre(),topico.getCurso().getNombre(),topico.getFechaCreacion());
    }
}
