package io.github.ybertoldi.libraryapi.controller.mappers;

import io.github.ybertoldi.libraryapi.controller.dto.UsuarioDTO;
import io.github.ybertoldi.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

import javax.swing.text.html.parser.Entity;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    public Usuario DtoToEntity(UsuarioDTO dto);
    public UsuarioDTO EntityToDTO(Usuario usuario);
}
