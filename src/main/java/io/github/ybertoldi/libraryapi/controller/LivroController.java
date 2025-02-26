package io.github.ybertoldi.libraryapi.controller;

import io.github.ybertoldi.libraryapi.controller.dto.LivroCadastroDTO;
import io.github.ybertoldi.libraryapi.controller.dto.LivroResultadoPesquisaDTO;
import io.github.ybertoldi.libraryapi.controller.mappers.LivroMapper;
import io.github.ybertoldi.libraryapi.model.GeneroLivro;
import io.github.ybertoldi.libraryapi.model.Livro;
import io.github.ybertoldi.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
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
        service.salvar(livro);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(livro.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<LivroResultadoPesquisaDTO> pesquisaPorId(@PathVariable("id") String id){
        UUID uuid = UUID.fromString(id);
        return service.obterPorId(uuid)
                .map(livro ->  ResponseEntity.ok(mapper.livroToDto(livro)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletaPorId(@PathVariable("id") UUID id){
        return service.obterPorId(id)
                .map(livro -> {
                    service.deletar(id);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<LivroResultadoPesquisaDTO>> pesquisar(
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "anoPublicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "nomeAutor", required = false)
            String nomeAutor,
            @RequestParam(value = "anoInicio", required = false)
            Integer anoInicio,
            @RequestParam(value = "anoFim", required = false)
            Integer anoFim
            ) {

        List<LivroResultadoPesquisaDTO> lista =
                service.pesquisa(titulo, isbn, anoPublicacao, genero, nomeAutor, anoInicio, anoFim)
                        .stream()
                        .map(mapper::livroToDto)
                        .toList();

        return ResponseEntity.ok(lista);
    }

}
