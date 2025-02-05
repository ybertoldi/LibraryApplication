package io.github.ybertoldi.libraryapi.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro", schema = "public")
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

    @Column(name = "genero", length = 30, nullable = false)
    String genero;

    @Column(name = "preco", nullable = false)
    double preco;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "autor", referencedColumnName = "id")
    @Column(name = "id_autor")
    Autor autor;

}
