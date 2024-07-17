package com.aluracursos.Foro.Hub.controller;

import com.aluracursos.Foro.Hub.domain.Usuarios.DatosAutenticarUsuario;
import com.aluracursos.Foro.Hub.domain.Usuarios.Usuario;
import com.aluracursos.Foro.Hub.infra.security.DatosJWTToken;
import com.aluracursos.Foro.Hub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> autenticarUsuario(@RequestBody @Validated DatosAutenticarUsuario datosAutenticacionUsuario) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                datosAutenticacionUsuario.correoElectronico(),
                datosAutenticacionUsuario.contrasena()
        );
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        String tipo = "Bearer";
        return ResponseEntity.ok(new DatosJWTToken(JWTToken, tipo));
    }
}