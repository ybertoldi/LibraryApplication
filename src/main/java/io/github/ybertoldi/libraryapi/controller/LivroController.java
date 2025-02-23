package io.github.ybertoldi.libraryapi.controller;

import io.github.ybertoldi.libraryapi.controller.dto.LivroDTO;
import io.github.ybertoldi.libraryapi.model.Autor;
import io.github.ybertoldi.libraryapi.model.Livro;
import io.github.ybertoldi.libraryapi.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody LivroDTO livroDTO){
        Autor autor = livroService.obterAutor(livroDTO.idAutor());

        Livro livro = new Livro();
        livro.setTitulo(livroDTO.titulo());
        livro.setIsbn(livroDTO.isbn());
        livro.setGenero(livroDTO.genero());
        livro.setPreco(livroDTO.preco());
        livro.setDataPublicacao(livroDTO.dataPublicacao());
        livro.setAutor(autor);

        Livro livroCriado = livroService.salvar(livro);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(livroCriado.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
