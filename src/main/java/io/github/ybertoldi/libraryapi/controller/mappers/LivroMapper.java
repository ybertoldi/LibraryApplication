package io.github.ybertoldi.libraryapi.controller.mappers;

import io.github.ybertoldi.libraryapi.controller.dto.LivroCadastroDTO;
import io.github.ybertoldi.libraryapi.controller.dto.LivroResultadoPesquisaDTO;
import io.github.ybertoldi.libraryapi.model.Livro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LivroMapper {
    LivroMapper INSTANCE = Mappers.getMapper(LivroMapper.class);

    LivroResultadoPesquisaDTO livroToDto(Livro livro);
    @Mapping(source = "idAutor", target = "autor.id")
    Livro dtoToLivro(LivroCadastroDTO dto);
}
