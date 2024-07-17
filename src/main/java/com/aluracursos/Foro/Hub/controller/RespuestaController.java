package com.aluracursos.Foro.Hub.controller;


import com.aluracursos.Foro.Hub.domain.respuesta.*;
import com.aluracursos.Foro.Hub.domain.topico.DatosEntradaTopico;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/respuestas")

public class RespuestaController {
    @Autowired
    RespuestaService respuestaService;

    @Autowired
    RespuestaRepository respuestaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosListaRespuesta> registarTopico(@RequestBody @Valid DatosEntradaRespuesta datosEntradaRespuestas,
                                                              UriComponentsBuilder uriComponentsBuilder) {
        DatosRegistrarRespuesta registroNuevo = respuestaService.guardarRespuesta(datosEntradaRespuestas);
        Respuesta respuesta = new Respuesta(registroNuevo);
        respuestaRepository.save(respuesta);
        DatosListaRespuesta datosListarRespuesta = new DatosListaRespuesta(respuesta.getId(),respuesta.getMensaje(),
                respuesta.getTopico().getTitulo(),respuesta.getAutor().getNombre().toString(),respuesta.getSolucion(),respuesta.getFechaCreacion());
        URI uri = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(uri).body(datosListarRespuesta);
    }

    @GetMapping
    public ResponseEntity<?> consultar(
            @PageableDefault(size = 5,sort = { "id" }, direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(respuestaRepository.findAll(paginacion).stream()
                .map(t -> new DatosListaRespuesta(t.getId(),t.getMensaje(),t.getTopico().getTitulo(),t.getAutor().getNombre().toString(),t.getSolucion(),t.getFechaCreacion())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListaRespuesta> retornarUsuario(@PathVariable Long id) {
        Optional<Respuesta> respuesta = Optional.ofNullable(respuestaRepository.getReferenceById(id));
        if (!respuesta.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        DatosListaRespuesta datosListarUsuario = new DatosListaRespuesta(respuesta.get().getId(), respuesta.get().getMensaje(),
                respuesta.get().getTopico().getTitulo(),respuesta.get().getAutor().getNombre(), respuesta.get().getSolucion(),respuesta.get().getFechaCreacion());
        return ResponseEntity.ok(datosListarUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DatosListaRespuesta> actualizarTopico(@PathVariable Long id, @RequestBody DatosActualizaRespuesta datos) {
        Respuesta respuestaActualizado = respuestaService.actualizarRespuesta(id, datos);
        DatosListaRespuesta datosActualizados = new DatosListaRespuesta(respuestaActualizado.getId(),respuestaActualizado.getMensaje(),
                respuestaActualizado.getTopico().getTitulo(),respuestaActualizado.getAutor().getNombre(),respuestaActualizado.getSolucion(),
                respuestaActualizado.getFechaCreacion());
        return ResponseEntity.ok(datosActualizados);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Respuesta> respuesta = respuestaRepository.findById(id);
        System.out.println(respuesta);
        if(!respuesta.isPresent()) {
            throw new ValidationException("este id para la respuesta no fue encontrado");
        }
        respuestaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

