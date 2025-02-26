package io.github.ybertoldi.libraryapi.repository.specs;

import io.github.ybertoldi.libraryapi.model.GeneroLivro;
import io.github.ybertoldi.libraryapi.model.Livro;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn){
        return (root, query, cb) ->
                cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo){
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero){
        return (root, query, cb) ->
                cb.equal(root.get("genero"), genero.toString());
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao){
        return (root, query, cb) ->
                cb.between(root.get("dataPublicacao"), LocalDate.of(anoPublicacao - 1,12,31), LocalDate.of(anoPublicacao + 1,1,1));
    }

    public static Specification<Livro> nomeAutorLike(String nomeAutor){
        return (root, query, builder) ->{
            Join<Object, Object> joined = root.join("autor");
            return builder.like(builder.lower(joined.get("nome")), "%" + nomeAutor + "%");
        };
    }
//builder.function("extract", Integer.class, builder.literal("year"), root.get("dataPublicacao")
    public static Specification<Livro> anoAfter(Integer ano){
        return (root, query, builder) ->{
            return builder.greaterThanOrEqualTo(root.get("dataPublicacao"), LocalDate.of(ano, 1, 1));
        };
    }

    public static Specification<Livro> anoBefore(Integer ano){
        return (root, query, builder) ->{
            return builder.lessThan(root.get("dataPublicacao"), LocalDate.of(ano+1, 1, 1));
        };
    }



}
