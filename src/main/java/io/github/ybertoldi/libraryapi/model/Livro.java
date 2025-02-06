package io.github.ybertoldi.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro", schema = "public")
@Data
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    String titulo;

    @Column(name = "data_publicacao", nullable = false)
    LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
    GeneroLivro genero;

    @Column(name = "preco", nullable = false)
    double preco;

    @ManyToOne(
            //cascade = CascadeType.ALL,  -> recomendado não usar. Descomentar para usar no LivroRepositoryTest
            //fetch = FetchType.LAZY
    )
    @JoinColumn(name = "id_autor")
    private Autor autor;

    public void setTo(String titulo, String isbn, GeneroLivro genero, Autor autor, double preco, LocalDate dataPublicacao){
        this.titulo = titulo;
        this.genero = genero;
        this.autor = autor;
        this.dataPublicacao = dataPublicacao;
        this.isbn = isbn;
        this.preco = preco;
    }

}





