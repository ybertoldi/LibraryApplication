package io.github.ybertoldi.libraryapi.service;

import io.github.ybertoldi.libraryapi.exceptions.AutorInexistenteException;
import io.github.ybertoldi.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.ybertoldi.libraryapi.model.Autor;
import io.github.ybertoldi.libraryapi.model.GeneroLivro;
import io.github.ybertoldi.libraryapi.model.Livro;
import io.github.ybertoldi.libraryapi.repository.AutorRepository;
import io.github.ybertoldi.libraryapi.repository.LivroRepository;
import static io.github.ybertoldi.libraryapi.repository.specs.LivroSpecs.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void deletar(UUID id) {
            livroRepository.deleteById(id);
    }

    public List<Livro> pesquisa(String titulo, String isbn, Integer anoPublicacao, GeneroLivro genero, String nomeAutor) {
        Specification<Livro> specs = Specification
                .where((root, query, cb) -> cb.conjunction());

        if (titulo != null){
            specs = specs.and(tituloLike(titulo));
        }

        if (isbn != null){
            specs = specs.and(isbnEqual(isbn));
        }

//        if (anoPublicacao != null) {
//            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
//        }

        if (genero != null) {
            specs = specs.and(generoEqual(genero));
        }

        return livroRepository.findAll(specs);
    }
}
