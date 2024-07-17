package com.aluracursos.Foro.Hub.domain.Usuarios;

import jakarta.validation.constraints.Email;

import java.util.List;

public record DatosRegistroUsuario(
        String nombre,
        @Email
        String correoElectronico,
        String contrasena

) {
}
