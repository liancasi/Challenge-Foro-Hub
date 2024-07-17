package com.aluracursos.Foro.Hub.domain.Usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizaUsuario(
        @NotNull
        Long id,
        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String correoElectronico,
        @NotBlank
        String contrasena
) {
}
