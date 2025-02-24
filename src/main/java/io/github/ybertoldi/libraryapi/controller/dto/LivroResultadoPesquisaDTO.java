package io.github.ybertoldi.libraryapi.controller.dto;

import io.github.ybertoldi.libraryapi.model.GeneroLivro;

import java.time.LocalDate;
import java.util.UUID;

public record LivroResultadoPesquisaDTO(
        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        GeneroLivro genero,
        Double preco,
        AutorDTO autor
) {
}
