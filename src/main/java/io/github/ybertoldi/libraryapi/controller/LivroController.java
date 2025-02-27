package io.github.ybertoldi.libraryapi.controller;

import io.github.ybertoldi.libraryapi.controller.dto.LivroCadastroDTO;
import io.github.ybertoldi.libraryapi.controller.dto.LivroResultadoPesquisaDTO;
import io.github.ybertoldi.libraryapi.controller.mappers.LivroMapper;
import io.github.ybertoldi.libraryapi.model.GeneroLivro;
import io.github.ybertoldi.libraryapi.model.Livro;
import io.github.ybertoldi.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<Page<LivroResultadoPesquisaDTO>> pesquisar(
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "ano-inicio", required = false)
            Integer anoInicio,
            @RequestParam(value = "ano-fim", required = false)
            Integer anoFim,
            @PageableDefault(size = 10, sort = "titulo")
            Pageable pageable
            ) {

        Page<Livro> pagina = service.pesquisa(titulo, isbn, anoPublicacao, genero, nomeAutor, anoInicio, anoFim, pageable);
        return ResponseEntity.ok(pagina.map(mapper::livroToDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable("id") String id, @RequestBody @Valid LivroCadastroDTO dto){
        UUID uuid = UUID.fromString(id);
        Livro livro = service.obterPorId(uuid).orElse(null);
        if (livro == null) {return ResponseEntity.notFound().build();}

        livro.setTitulo(dto.titulo());
        livro.setIsbn(dto.isbn());
        livro.setGenero(dto.genero());
        livro.setPreco(dto.preco());
        livro.setAutor(service.obterAutor(dto.idAutor()));
        livro.setDataPublicacao(dto.dataPublicacao());

        service.salvar(livro);
        return ResponseEntity.noContent().build();
    }

}
