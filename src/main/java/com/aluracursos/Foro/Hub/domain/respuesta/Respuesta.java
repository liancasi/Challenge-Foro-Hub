package com.aluracursos.Foro.Hub.domain.respuesta;

import com.aluracursos.Foro.Hub.domain.Usuarios.Usuario;
import com.aluracursos.Foro.Hub.domain.topico.Topico;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Respuesta")
@Table(name = "respuesta")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    @ManyToOne
    @JoinColumn(name = "topico_id", nullable = false)
    @JsonBackReference
    private Topico topico;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;
    private String solucion;
    public Respuesta(DatosRegistrarRespuesta datosEntradaRespuestas) {

        this.mensaje = datosEntradaRespuestas.mensaje();
        this.topico = datosEntradaRespuestas.topico();
        this.autor = datosEntradaRespuestas.autor();
        this.solucion = datosEntradaRespuestas.solucion();

    }

    public void actualizarDatos(DatosActualizaRespuesta datosActualizarRespuesta) {
        if(datosActualizarRespuesta.mensaje() != null) {
            this.mensaje = datosActualizarRespuesta.mensaje();
        }
        if(datosActualizarRespuesta.solucion() != null) {
            this.solucion = datosActualizarRespuesta.solucion() ;
        }
    }
}
