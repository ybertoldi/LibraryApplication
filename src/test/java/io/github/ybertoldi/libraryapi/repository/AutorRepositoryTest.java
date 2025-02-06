package io.github.ybertoldi.libraryapi.repository;

import io.github.ybertoldi.libraryapi.model.Autor;
import io.github.ybertoldi.libraryapi.model.GeneroLivro;
import io.github.ybertoldi.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("H. G. Wells");
        autor.setNacionalidade("inglês");
        autor.setDataNascimento(LocalDate.of(1866,8,21));
        System.out.println(autor.getId());

        var autorSalvo = repository.save(autor);
        System.out.println(autorSalvo);
    }

    @Test
    public void atualizarTeste(){
        UUID id = UUID.fromString("75a3ccc8-83a8-457e-9793-80fb0086757e");

        Optional<Autor> possivelAutor = repository.findById(id);
        
        if (possivelAutor.isPresent()){
            Autor autor = possivelAutor.get();
            System.out.println("Dados do autor");
            System.out.println(autor);

            autor.setDataNascimento(LocalDate.of(1960,2,15));
            repository.save(autor);
        }
        
    }

    @Test
    public void listarTest(){
        List<Autor> list = repository.findAll();
        list.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("contagem de autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest(){
        UUID id = UUID.fromString("75a3ccc8-83a8-457e-9793-80fb0086757e");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest(){
        Autor autor = repository.findAll().getFirst();
        System.out.println("Autor deletado: " + autor);
        repository.delete(autor);
    }

    @Test
    public void salvarComLivrosTeste(){
        Autor autor = new Autor();
        autor.setNome("Liev Tolstói");
        autor.setNacionalidade("russo");
        autor.setDataNascimento(LocalDate.of(1828, 9, 9));


        List<Livro> listaLivro = new ArrayList<>();
        listaLivro.add(new Livro());
        listaLivro.getFirst().setTo("Guerra e Paz", "1232-312-42", GeneroLivro.FICCAO, autor, 100.20d, LocalDate.of(1880,9,10));
        listaLivro.add(new Livro());
        listaLivro.get(1).setTo("Anna Karenina", "112-312-23", GeneroLivro.FICCAO, autor, 130.00d, LocalDate.of(1870, 2, 13));
        autor.setLivros(listaLivro);

        repository.save(autor);
        System.out.println("O id do autor é: " + autor.getId());
        livroRepository.saveAll(autor.getLivros());

    }

    @Test
    public void listarLivrosAutor(){
        Autor autor = repository.findAll().get(2);
        System.out.println("Livros de " + autor.getNome());
        livroRepository.findByAutor(autor).forEach(System.out::println);
    }
}
