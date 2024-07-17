package com.aluracursos.Foro.Hub.domain.topico;

import com.aluracursos.Foro.Hub.domain.Usuarios.Usuario;
import com.aluracursos.Foro.Hub.domain.Usuarios.UsuarioRepository;
import com.aluracursos.Foro.Hub.domain.curso.Curso;
import com.aluracursos.Foro.Hub.domain.curso.CursoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    List<ValidacionTopico> validadores;

    public Topico registrarTopico(DatosEntradaTopico datos) {
        Long numberId = Long.parseLong(datos.idUsuario().toString());

        validadores.forEach(v -> v.validar(datos));


        var usuario = usuarioRepository.findById(numberId);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        // Verificar existencia del curso
        Curso curso = cursoRepository.findByNombreIgnoreCase(datos.nombreCurso());
        if (curso == null) {
            curso = cursoRepository.save(new Curso(datos.nombreCurso()));
        }

        Topico topico = topicoRepository.findByTituloIgnoreCase(datos.titulo());

        if(topico != null) {
            throw new ValidationException("Titulo repetido");
        }
        topico = topicoRepository.findByMensajeIgnoreCase(datos.mensaje());
        if(topico != null) {
            throw new ValidationException("Mensaje repetido");
        }
        DatosRegistrarTopico datosRegistroTopico = new DatosRegistrarTopico(datos.titulo(), datos.mensaje(), usuario.get(), curso);
        topico = topicoRepository.save(new Topico(datosRegistroTopico));

        return topico;
    }

    public Page<Topico> buscarTopicosPorCursoYAnio(String nombreCurso, int year, Pageable paginacion) {
        return topicoRepository.findByCursoNombreAndYear(nombreCurso, year, paginacion);
    }

    public Optional<Topico> buscarTopicoPorId(Long id) {
        return topicoRepository.findById(id);
    }

    public Topico actualizarTopico(Long id, DatosEntradaTopico datos) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        if (!optionalTopico.isPresent()) {
            throw new EntityNotFoundException("Tópico no encontrado");
        }

        validadores.forEach(v -> v.validar(datos));

        Topico topico = optionalTopico.get();

        // Verificar y asignar el usuario y curso
        Long numberId = Long.parseLong(datos.idUsuario());
        Usuario usuario = usuarioRepository.findById(numberId)
                .orElseThrow(() -> new ValidationException("Usuario no encontrado"));

        Curso curso = cursoRepository.findByNombreIgnoreCase(datos.nombreCurso());
        if (curso == null) {
            curso = cursoRepository.save(new Curso(datos.nombreCurso()));
        }

        topico.actualizarDatos(datos, usuario, curso);

        return topicoRepository.save(topico);
    }

    public void eliminarTopicoPorId(Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isPresent()) {
            topicoRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("No se encontró el tópico con ID: " + id);
        }
    }

}
