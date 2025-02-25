package io.github.ybertoldi.libraryapi.controller.mappers;

import io.github.ybertoldi.libraryapi.controller.dto.LivroCadastroDTO;
import io.github.ybertoldi.libraryapi.controller.dto.LivroResultadoPesquisaDTO;
import io.github.ybertoldi.libraryapi.model.Livro;
import io.github.ybertoldi.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {
    @Autowired
    AutorRepository autorRepository;

    public abstract LivroResultadoPesquisaDTO livroToDto(Livro livro);
    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro dtoToLivro(LivroCadastroDTO dto);
}
