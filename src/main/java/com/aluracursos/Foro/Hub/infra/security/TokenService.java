package com.aluracursos.Foro.Hub.infra.security;

import com.aluracursos.Foro.Hub.domain.Usuarios.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


@Service
public class TokenService {
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Value("12345")
    private String secret;

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            logger.warn("Invalid JWT token", exception);
            return null;
        }
    }

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Foro Hub")
                    .withSubject(usuario.getCorreoElectronico())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            logger.error("Error al generar el token JWT", exception);
            throw new RuntimeException("Error al generar el token JWT", exception);
        }
    }

    private Date generarFechaExpiracion() {
        return new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000); // 2 horas
    }
}