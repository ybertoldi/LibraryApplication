package io.github.ybertoldi.libraryapi.service;

import io.github.ybertoldi.libraryapi.controller.dto.LivroDTO;
import io.github.ybertoldi.libraryapi.exceptions.AutorInexistenteException;
import io.github.ybertoldi.libraryapi.model.Autor;
import io.github.ybertoldi.libraryapi.model.Livro;
import io.github.ybertoldi.libraryapi.repository.AutorRepository;
import io.github.ybertoldi.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;


    public Autor obterAutor(UUID uuid) {
        Optional<Autor> optionalAutor = autorRepository.findById(uuid);
        if (optionalAutor.isEmpty()){
            throw new AutorInexistenteException("O autor de id " + uuid + " nao existe no banco de dados");
        }
        return optionalAutor.get();

    }

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }
}
