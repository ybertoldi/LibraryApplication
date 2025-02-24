package io.github.ybertoldi.libraryapi.controller;

import io.github.ybertoldi.libraryapi.controller.dto.AutorDTO;
import io.github.ybertoldi.libraryapi.controller.dto.ErroResposta;
import io.github.ybertoldi.libraryapi.controller.mappers.AutorMapper;
import io.github.ybertoldi.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.ybertoldi.libraryapi.model.Autor;
import io.github.ybertoldi.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService service;
    private final AutorMapper mapper;

    @PostMapping
    public ResponseEntity<Object> cadastroDeAutor(@RequestBody @Valid AutorDTO dto) {
        try {
            Autor autor = mapper.dtoToAutor(dto);
            autor = service.salvaAutor(autor);

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autor.getId())
                    .toUri();

            return ResponseEntity.created(uri).build();
        } catch (RegistroDuplicadoException e) {
            ErroResposta erroDto = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> obterDetalhes(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Autor> autor = service.obterPorId(uuid);

        if (autor.isPresent()) {
            AutorDTO dto = mapper.autorToDto(autor.get());
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletaAutor(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Autor> optionalAutor = service.obterPorId(uuid);

        if (optionalAutor.isPresent()) {
            service.deletarPorId(optionalAutor.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade
    ) {

        List<Autor> pesquisa = service.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> dtos = pesquisa
                .stream()
                .map(mapper::autorToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto) {
        try {
            UUID uuid = UUID.fromString(id);
            Optional<Autor> optionalAutor = service.obterPorId(uuid);

            if (optionalAutor.isPresent()) {
                Autor autor = optionalAutor.get();
                autor.setNacionalidade(dto.nacionalidade());
                autor.setNome(dto.nome());
                autor.setDataNascimento(dto.dataNascimento());
                service.atualizar(autor);
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.notFound().build();
        } catch (RegistroDuplicadoException e) {
            ErroResposta erroDto = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }
}
