package io.github.ybertoldi.libraryapi.controller.dto;

import io.github.ybertoldi.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record LivroDTO(
        @NotBlank(message = "Campo obrigatorio")
        String isbn,
        @NotBlank(message = "Campo obrigatorio")
        String titulo,
        @NotNull(message = "Campo obrigatorio")
        LocalDate dataPublicacao,
        GeneroLivro genero,
        Double preco,
        @NotNull(message = "Campo Obrigatorio")
        UUID idAutor
) {
}
