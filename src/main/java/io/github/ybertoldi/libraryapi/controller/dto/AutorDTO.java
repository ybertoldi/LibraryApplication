package io.github.ybertoldi.libraryapi.controller.dto;
import io.github.ybertoldi.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "campo obrigatorio")
        @Size( min = 2, max = 100, message = "Campo fora do tamanho padrao")
        String nome,
        @NotNull(message = "campo obrigatorio")
        @Past(message = "data invalida - nao pode ser data do futuro")
        LocalDate dataNascimento,
        @Size( min = 2, max = 50, message = "Campo fora do tamanho padrao")
        @NotBlank(message = "campo obrigatorio")
        String nacionalidade) {


    public Autor toAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}