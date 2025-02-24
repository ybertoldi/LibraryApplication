package io.github.ybertoldi.libraryapi.service;

import io.github.ybertoldi.libraryapi.controller.dto.LivroResultadoPesquisaDTO;
import io.github.ybertoldi.libraryapi.exceptions.AutorInexistenteException;
import io.github.ybertoldi.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.ybertoldi.libraryapi.model.Autor;
import io.github.ybertoldi.libraryapi.model.Livro;
import io.github.ybertoldi.libraryapi.repository.AutorRepository;
import io.github.ybertoldi.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (! autorRepository.existsById(livro.getAutor().getId())){
            throw new AutorInexistenteException("O autor nao existe no banco de dados");
        }
        if (livroRepository.existsByIsbn(livro.getIsbn())){
            throw new RegistroDuplicadoException("Isbn já consta no banco");
        }

        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID uuid) {
        return livroRepository.livroComAutorPorId(uuid);
    }
}
