package io.github.ybertoldi.libraryapi.repository;

import io.github.ybertoldi.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("maria");
        autor.setNacionalidade("português");
        autor.setDataNascimento(LocalDate.of(1951,10,20));
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
}
