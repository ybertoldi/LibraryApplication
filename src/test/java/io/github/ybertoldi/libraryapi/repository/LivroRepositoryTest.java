package io.github.ybertoldi.libraryapi.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.ybertoldi.libraryapi.model.Autor;
import io.github.ybertoldi.libraryapi.model.GeneroLivro;
import io.github.ybertoldi.libraryapi.model.Livro;
import io.github.ybertoldi.libraryapi.model.LocalDateTypeAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    @Test
    void pesquisaPorTituloTeste(){
        List<Livro> livros = repository.findByTitulo("Guerra e Paz");
        for (Livro livro : livros) {
            System.out.println(livro.getIsbn());
        }
    }

    @Test
    void listarLivrosComQuery(){
        List<Livro> livros = repository.listarTodosLivros();
        for (Livro livro : livros) {
            System.out.println(livro);
        }
    }

    @Test
    void listarLivroGenero(){
        List<Livro> livros = repository.listarLivroPorGenero(GeneroLivro.FICCAO, "genero");
        livros.forEach(System.out::println);
    }

    @Test
    void listarLivroGeneroPositional(){
        List<Livro> livros = repository.listarLivroPorGeneroPositionalParam(GeneroLivro.FICCAO, "genero");
        livros.forEach(System.out::println);
    }

    @Test
    void salvarEmJsonTeste() throws IOException {
        List<Livro> livros = repository.todosOsLivrosComAutor();

        String path = "C:\\Users\\Yuri\\OneDrive\\Área de Trabalho\\Estudo\\expert spring boot\\libraryapi";
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .setPrettyPrinting()
                .create();

        FileWriter writer = new FileWriter(path + "\\livrosExcluidos.json");
        gson.toJson(livros, writer);
    }

    @Test
    void deleteGeneroTest() throws IOException {
        String path = "C:\\Users\\Yuri\\OneDrive\\Área de Trabalho\\Estudo\\expert spring boot\\libraryapi";
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .setPrettyPrinting()
                .create();

        FileWriter writer = new FileWriter(path + "\\livrosExcluidos.json");
        gson.toJson(repository.todosOsLivrosComAutor(), writer);

        repository.deleteByGenero(GeneroLivro.FICCAO);
    }

    @Test
    void inserirBackup() throws FileNotFoundException {
        String path = "C:\\Users\\Yuri\\OneDrive\\Área de Trabalho\\Estudo\\expert spring boot\\libraryapi";
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .setPrettyPrinting()
                .create();

        Type tipo = new TypeToken<ArrayList<Livro>>(){}.getType();
        List<Livro> lista = gson.fromJson(new FileReader(path + "\\livrosExcluidos.json"), tipo);
        for (Livro livro : lista) {
            livro.setId(null);
        }

        repository.saveAll(lista);
    }


}
