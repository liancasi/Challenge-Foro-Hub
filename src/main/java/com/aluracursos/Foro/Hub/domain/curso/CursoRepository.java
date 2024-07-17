package com.aluracursos.Foro.Hub.domain.curso;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Curso findByNombreIgnoreCase(String s);
}
