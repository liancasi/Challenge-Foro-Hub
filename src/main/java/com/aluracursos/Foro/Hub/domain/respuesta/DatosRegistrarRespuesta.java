package com.aluracursos.Foro.Hub.domain.respuesta;

import com.aluracursos.Foro.Hub.domain.Usuarios.Usuario;
import com.aluracursos.Foro.Hub.domain.topico.Topico;

public record DatosRegistrarRespuesta(
        String mensaje,
        Topico topico,
        Usuario autor,
        String solucion
) {
}
