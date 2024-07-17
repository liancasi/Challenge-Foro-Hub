package com.aluracursos.Foro.Hub.controller;

import com.aluracursos.Foro.Hub.domain.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/topicos")

public class TopicoController {
    @Autowired
    private TopicoService topicoService;

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosListaTopico> registrarTopico(@RequestBody @Valid DatosEntradaTopico datos, UriComponentsBuilder uriComponentsBuilder) {
        Topico topico = topicoService.registrarTopico(datos);
        DatosListaTopico datosListarTopico = new DatosListaTopico(topico.getId(), topico.getTitulo(),
                topico.getMensaje(),topico.getAutor().getNombre(),topico.getCurso().getNombre(), topico.getFechaCreacion());
        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(datosListarTopico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaTopico>> listarTopicosActivos(@PageableDefault(size = 10) Pageable paginacion) {
        Page<Topico> topicos = (Page<Topico>) topicoRepository.findByStatusTrue(paginacion);
        Page<DatosListaTopico> datosListaTopicos = topicos.map(topico -> new DatosListaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre(),
                topico.getFechaCreacion()
        ));
        return ResponseEntity.ok(datosListaTopicos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<DatosListaTopico>> buscarPorCursoYAnio(
            @RequestParam String nombreCurso,
            @RequestParam int year,
            @PageableDefault(size = 10, sort = { "fechaCreacion" }, direction = Sort.Direction.ASC) Pageable paginacion) {
        Page<Topico> topicos = topicoService.buscarTopicosPorCursoYAnio(nombreCurso, year, paginacion);
        return ResponseEntity.ok(topicos.map(DatosListaTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> obtenerTopicoPorId(@PathVariable Long id) {
        Optional<Topico> topico = topicoService.buscarTopicoPorId(id);
        if (!topico.isPresent()){
            return ResponseEntity.notFound().build();
        }
        DatosDetalleTopico datosDetalleTopico = new DatosDetalleTopico(
                topico.get().getId(),
                topico.get().getTitulo(),
                topico.get().getMensaje(),
                topico.get().getFechaCreacion(),
                topico.get().getStatus(),
                topico.get().getAutor().getNombre(),
                topico.get().getCurso().getNombre()
        );
        return ResponseEntity.ok(datosDetalleTopico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DatosListaTopico> actualizarTopico(@PathVariable Long id, @RequestBody DatosEntradaTopico datos) {
        Topico topicoActualizado = topicoService.actualizarTopico(id, datos);
        DatosListaTopico datosActualizados = new DatosListaTopico(topicoActualizado.getId(),topicoActualizado.getTitulo(),topicoActualizado.getMensaje(),topicoActualizado.getAutor().getNombre(),topicoActualizado.getCurso().getNombre(),topicoActualizado.getFechaCreacion());
        return ResponseEntity.ok(datosActualizados);

    }

    @DeleteMapping("/desactivar/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        topico.eliminar();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopicoBD(@PathVariable Long id) {
        topicoService.eliminarTopicoPorId(id);
        return ResponseEntity.noContent().build();
    }
}


