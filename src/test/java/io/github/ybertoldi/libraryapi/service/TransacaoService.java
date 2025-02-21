package io.github.ybertoldi.libraryapi.service;

import io.github.ybertoldi.libraryapi.model.Autor;
import io.github.ybertoldi.libraryapi.model.GeneroLivro;
import io.github.ybertoldi.libraryapi.model.Livro;
import io.github.ybertoldi.libraryapi.repository.AutorRepository;
import io.github.ybertoldi.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransacaoService {
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void executar(){
        Autor autor = new Autor();
        autor.setNome("H. G. Well");
        autor.setNacionalidade("inglês");
        autor.setDataNascimento(LocalDate.of(1828, 9, 9));

        if (autorRepository.existsByNome(autor.getNome())){
            throw new IllegalArgumentException("O autor já consta no banco de dados");
        }
        List<Livro> listaLivro = new ArrayList<>();
        listaLivro.add(new Livro());
        listaLivro.getFirst().setTo("Guerra e Paz", "1232-312-42", GeneroLivro.FICCAO, autor, 100.20d, LocalDate.of(1880,9,10));
        autor.setLivros(listaLivro);

        autorRepository.save(autor);
        livroRepository.saveAll(autor.getLivros());
    }
}
