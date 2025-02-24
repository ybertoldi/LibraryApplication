package io.github.ybertoldi.libraryapi.controller.mappers;

import io.github.ybertoldi.libraryapi.controller.dto.AutorDTO;
import io.github.ybertoldi.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutorMapper {
    AutorMapper INSTANCE = Mappers.getMapper(AutorMapper.class);

    AutorDTO autorToDto(Autor autor);
    Autor dtoToAutor(AutorDTO dto);
}
