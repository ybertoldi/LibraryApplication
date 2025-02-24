package io.github.ybertoldi.libraryapi.service;

import io.github.ybertoldi.libraryapi.exceptions.OperacaoNaoPermitiaException;
import io.github.ybertoldi.libraryapi.model.Autor;
import io.github.ybertoldi.libraryapi.repository.AutorRepository;
import io.github.ybertoldi.libraryapi.repository.LivroRepository;
import io.github.ybertoldi.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final AutorValidator autorValidator;

    public Autor salvaAutor(Autor autor){
        autorValidator.validar(autor);
        return autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return autorRepository.findById(id);
    }

    public void deletarPorId(Autor autor) {
        if (possuiLivro(autor)){
            throw new OperacaoNaoPermitiaException("Autor possui livros cadastrados. Exclua os livros antes do autor");
        }

        autorRepository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){
        if (nome != null && nacionalidade != null){
            return autorRepository.findByNomeAndNacionalidade(nome,nacionalidade);
        }

        if (nome != null){
            return autorRepository.findByNome(nome);
        }

        if (nacionalidade != null){
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findAll();
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade){
        Autor autor = new Autor();
        autor.setNacionalidade(nacionalidade);
        autor.setNome(nome);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);

        return autorRepository.findAll(autorExample);
    }

    public void atualizar(Autor autor) {
        if (autor.getId() == null){
            throw new IllegalArgumentException("Autor nao cadastrado");
        }
        autorValidator.validar(autor);
        autorRepository.save(autor);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
