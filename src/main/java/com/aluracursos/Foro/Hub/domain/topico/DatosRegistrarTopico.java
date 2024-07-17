package com.aluracursos.Foro.Hub.domain.topico;

import com.aluracursos.Foro.Hub.domain.Usuarios.Usuario;
import com.aluracursos.Foro.Hub.domain.curso.Curso;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public record DatosRegistrarTopico(
        String titulo,
        String mensaje,
        @JsonManagedReference
        Usuario idUsuario,
        Curso nombreCurso
) {
}
