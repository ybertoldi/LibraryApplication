package io.github.ybertoldi.libraryapi.repository;

import io.github.ybertoldi.libraryapi.model.Autor;
import io.github.ybertoldi.libraryapi.model.GeneroLivro;
import io.github.ybertoldi.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 */

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    boolean existsByIsbn(String isbn);

    //query method
    //selecet * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    //jpql - referencia as classes, não ao sql
    @Query(" select l from Livro  as l order by l.titulo")
    List<Livro> listarTodosLivros();

    @Query(" select a from Livro as l join l.autor a where l = ?1 ")
    Autor acharAutorDoLivro(Livro livro);

    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    @Query("""
            select l
            from Livro l
            where l.genero = :g
            order by :po
            """)
    List<Livro> listarLivroPorGenero(@Param("g") GeneroLivro genero, @Param("po") String paramOrdenacao);

    @Query("""
            select l
            from Livro l
            where l.genero = ?1
            order by ?2
            """)
    List<Livro> listarLivroPorGeneroPositionalParam(GeneroLivro genero, String paramOrdenacao);

    @Transactional
    @Modifying
    @Query("""
            delete
            from Livro
            where genero = ?1
            """)
    void deleteByGenero(GeneroLivro generoLivro);


    @Query("""
            select l 
            from Livro l 
            join fetch l.autor
            """)
    List<Livro> todosOsLivrosComAutor();

    @Query("""
            select l 
            from Livro l 
            join fetch l.autor
            where l.id = ?1
            """)
    Optional<Livro> livroComAutorPorId(UUID id);

    boolean existsByAutor(Autor autor);

}
