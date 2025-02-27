package io.github.ybertoldi.libraryapi.service;

import io.github.ybertoldi.libraryapi.exceptions.AutorInexistenteException;
import io.github.ybertoldi.libraryapi.exceptions.CampoInvalidoException;
import io.github.ybertoldi.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.ybertoldi.libraryapi.model.Autor;
import io.github.ybertoldi.libraryapi.model.GeneroLivro;
import io.github.ybertoldi.libraryapi.model.Livro;
import io.github.ybertoldi.libraryapi.repository.AutorRepository;
import io.github.ybertoldi.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import static io.github.ybertoldi.libraryapi.repository.specs.LivroSpecs.*;

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
        if(livro.getPreco() == null && livro.getDataPublicacao().getYear() >= 2020){
            throw new CampoInvalidoException("preco","todo livro a partir de 2020 deve ter o preço informado");
        }

        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID uuid) {
        return livroRepository.livroComAutorPorId(uuid);
    }

    public void deletar(UUID id) {
            livroRepository.deleteById(id);
    }

    public Page<Livro> pesquisa(String titulo, String isbn, Integer anoPublicacao, GeneroLivro genero, String nomeAutor, Integer anoInicio, Integer anoFim, Pageable pageable) {

//        USANDO FILTER
//
//        List<Livro> lista = (nomeAutor == null) ? livroRepository.findAll() : livroRepository.todosOsLivrosComAutor();
//        return lista.stream()
//                .filter(l -> titulo == null || l.getTitulo().contains(titulo))
//                .filter(l -> isbn == null || l.getIsbn().equals(isbn))
//                .filter(l -> anoPublicacao == null || l.getDataPublicacao().getYear() == anoPublicacao)
//                .filter(l -> genero == null || l.getGenero().equals(genero))
//                .filter(l -> nomeAutor == null || l.getAutor().getNome().contains(nomeAutor))
//                .filter(l -> anoInicio == null || l.getDataPublicacao().getYear() >= anoInicio)
//                .filter(l -> anoInicio == null || l.getDataPublicacao().getYear() <= anoFim)
//                .toList();

//        USANDO QUERYBYEXAMPLE
//
//        // Criar exemplo com dados fornecidos
//        Livro livroExemplo = new Livro();
//
//        if (titulo != null) {livroExemplo.setTitulo(titulo);}
//        if (isbn != null) {livroExemplo.setIsbn(isbn);}
//        if (genero != null) {livroExemplo.setGenero(genero);}
//        if (nomeAutor != null) {
//            Autor autor = new Autor();
//            autor.setNome(nomeAutor);
//            livroExemplo.setAutor(autor);
//        }
//
//        // Criar ExampleMatcher
//        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // Para fazer busca por substring no nome
//                .withIgnoreCase() // Ignorar a comparação de maiúsculas/minúsculas
//                .withMatcher("autor.nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()); // Buscar somente no campo autor.nome dentro de Autor
//
//        Example<Livro> example = Example.of(livroExemplo, matcher);
//
//        // Realizar consulta com QBE
//        List<Livro> livros = livroRepository.findAll(example);
//
//        // Filtrar os livros com base no intervalo de anos e retornar
//        return livros.stream()
//                .filter(l -> anoInicio == null || l.getDataPublicacao().getYear() >= anoInicio)
//                .filter(l -> anoFim == null || l.getDataPublicacao().getYear() <= anoFim)
//                .toList();

//        USANDO SPECIFICATIONS
//

        Specification<Livro> specification = Specification.where(null);

        // Adiciona as especificações com base nos parâmetros recebidos
        if (titulo != null) {
            specification = specification.and(tituloLike(titulo));
        }
        if (isbn != null) {
            specification = specification.and(isbnEqual(isbn));
        }
        if (anoPublicacao != null) {
            specification = specification.and(anoPublicacaoEqual(anoPublicacao));
        }
        if (genero != null) {
            specification = specification.and(generoEqual(genero));
        }
        if (nomeAutor != null) {
            specification = specification.and(nomeAutorLike(nomeAutor));
        }
        if (anoInicio != null) {
            specification = specification.and(anoAfter(anoInicio));
        }
        if (anoFim != null) {
            specification = specification.and(anoBefore(anoFim));
        }

        // Realiza a consulta com as Specifications combinadas
        return livroRepository.findAll(specification, pageable);
    }
}
