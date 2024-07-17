package com.aluracursos.Foro.Hub.domain.topico;

import com.aluracursos.Foro.Hub.domain.Usuarios.Usuario;
import com.aluracursos.Foro.Hub.domain.curso.Curso;
import com.aluracursos.Foro.Hub.domain.respuesta.Respuesta;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity(name = "Topico")
@Table(name = "topico")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private Boolean status = true;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    @JsonBackReference
    private Usuario autor;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id")
    private Curso curso;
    @OneToMany(mappedBy = "topico", orphanRemoval = true,fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Respuesta> respuestas;


    public Topico(DatosRegistrarTopico datosRegistroTopico) {
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.autor = datosRegistroTopico.idUsuario();
        this.curso = datosRegistroTopico.nombreCurso();
    }

    public void actualizarDatos(DatosEntradaTopico datos, Usuario autor, Curso curso) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.autor = autor;
        this.curso = curso;
    }

    public void eliminar() {

        this.status = false;
    }
}


