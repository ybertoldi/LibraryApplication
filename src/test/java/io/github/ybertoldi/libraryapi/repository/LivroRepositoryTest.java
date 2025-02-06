package io.github.ybertoldi.libraryapi.repository;

import io.github.ybertoldi.libraryapi.model.Autor;
import io.github.ybertoldi.libraryapi.model.GeneroLivro;
import io.github.ybertoldi.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
public class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarTeste(){
        Livro livro = new Livro();
        livro.setIsbn("9880-1324");
        livro.setPreco(100.45);
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Guerra dos Mundos");
        livro.setDataPublicacao(LocalDate.of(1898, 1, 15));

        Autor autor = autorRepository
                .findById(UUID.fromString("d07b11a6-8e85-4f03-b182-256f344bc3eb"))
                .orElse(null);

        livro.setAutor(autor);

        Livro livroFinal = repository.save(livro);
        System.out.println("livro salvo: " + livroFinal);
    }

    @Test
    public void salvarCascadeTest(){
        Autor autor = new Autor();
        autor.setNome("Fiódor Mikhailovitch Dostoiévski");
        autor.setNacionalidade("russo");
        autor.setDataNascimento(LocalDate.of(1821,11,11));

        Livro livro = new Livro();
        livro.setIsbn("85-7326-208-7");
        livro.setPreco(100.45);
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Crime e Castigo");
        livro.setDataPublicacao(LocalDate.of(1866, 1, 15));
        livro.setAutor(autor);

        Livro livroSalvo = repository.save(livro);
        System.out.println("livro salvo: " + livroSalvo);
    }

    @Test
    public void atualizarTeste(){
        //ver no curso
    }

}
