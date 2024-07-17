package com.aluracursos.Foro.Hub.domain.respuesta;

import com.aluracursos.Foro.Hub.domain.Usuarios.Usuario;
import com.aluracursos.Foro.Hub.domain.Usuarios.UsuarioRepository;
import com.aluracursos.Foro.Hub.domain.topico.Topico;
import com.aluracursos.Foro.Hub.domain.topico.TopicoRepository;
import com.aluracursos.Foro.Hub.infra.errores.Validacion;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RespuestaService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    public DatosRegistrarRespuesta guardarRespuesta(DatosEntradaRespuesta datos) {
        Long numero = Long.parseLong(datos.idUsuario());
        Optional<Usuario> usuario = usuarioRepository.findById(numero);
        if (!usuario.isPresent()) {
            throw new Validacion("No existe el usuario");
        }
        numero = Long.parseLong(datos.idTopico());
        Optional<Topico> topico = topicoRepository.findById(numero);
        if (!topico.isPresent()) {
            throw new Validacion("Este topico no fue encontrado");
        }
        DatosRegistrarRespuesta registro = new DatosRegistrarRespuesta(datos.mensaje(), topico.get(), usuario.get(), datos.solucion());

        return registro;
    }

    public Respuesta actualizarRespuesta(Long id, DatosActualizaRespuesta datos) {
        Optional<Respuesta> optionalRespuesta = respuestaRepository.findById(id);

        if (!optionalRespuesta.isPresent()) {
            throw new EntityNotFoundException("Tópico no encontrado");
        }

        Respuesta respuesta = optionalRespuesta.get();

        respuesta.actualizarDatos(datos);

        return respuestaRepository.save(respuesta);
    }
}
