package io.github.ybertoldi.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UsuarioDTO(
        @NotBlank(message = "Valor nao pode ser nulo ou vazio")
        String login,
        @NotBlank(message = "Valor nao pode ser nulo ou vazio")
        String senha,
        List<String> roles
) {
}
