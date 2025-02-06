package io.github.ybertoldi.libraryapi.repository;

import io.github.ybertoldi.libraryapi.model.Autor;
import io.github.ybertoldi.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    //query method
    //selecet * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);
}
