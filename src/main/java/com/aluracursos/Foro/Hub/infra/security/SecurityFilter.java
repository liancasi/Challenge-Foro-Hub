package com.aluracursos.Foro.Hub.infra.security;

import com.aluracursos.Foro.Hub.domain.Usuarios.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var authHeader = request.getHeader("Authorization");
            logger.debug("Authorization header: {}", authHeader);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                var token = authHeader.substring(7);
                logger.debug("Extracted token: {}", token);

                var subject = tokenService.getSubject(token);
                logger.debug("Token subject: {}", subject);

                if (subject != null) {
                    var usuario = usuarioRepository.findByCorreoElectronico(subject);
                    if (usuario != null) {
                        var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        logger.debug("User authenticated: {}", usuario.getUsername());
                    } else {
                        logger.warn("User not found for email: {}", subject);
                    }
                } else {
                    logger.warn("Invalid token");
                }
            } else {
                logger.debug("No Authorization header or not Bearer token");
            }
        } catch (Exception e) {
            logger.error("Error processing authentication", e);
        }

        filterChain.doFilter(request, response);
    }
}