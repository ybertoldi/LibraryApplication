package io.github.ybertoldi.libraryapi.controller;

import io.github.ybertoldi.libraryapi.controller.dto.AutorDTO;
import io.github.ybertoldi.libraryapi.controller.dto.LivroCadastroDTO;
import io.github.ybertoldi.libraryapi.controller.dto.LivroResultadoPesquisaDTO;
import io.github.ybertoldi.libraryapi.controller.mappers.LivroMapper;
import io.github.ybertoldi.libraryapi.model.Autor;
import io.github.ybertoldi.libraryapi.model.Livro;
import io.github.ybertoldi.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid LivroCadastroDTO livroDTO){
        Livro livro = mapper.dtoToLivro(livroDTO);
        Livro livroSalvo = service.salvar(livro);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(livroSalvo.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> pesquisaPorId(@PathVariable("id") String id){
        UUID uuid = UUID.fromString(id);
        Optional<Livro> optionalLivro = service.obterPorId(uuid);

        if (optionalLivro.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        LivroResultadoPesquisaDTO dto = mapper.livroToDto(optionalLivro.get());
        return ResponseEntity.ok(dto);
    }
}
