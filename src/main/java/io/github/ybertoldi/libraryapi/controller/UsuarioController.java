package io.github.ybertoldi.libraryapi.controller;

import io.github.ybertoldi.libraryapi.controller.dto.UsuarioDTO;
import io.github.ybertoldi.libraryapi.controller.mappers.UsuarioMapper;
import io.github.ybertoldi.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("usuarios")
public class UsuarioController {
    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto){
        service.salvar(mapper.DtoToEntity(dto));
    }
}
