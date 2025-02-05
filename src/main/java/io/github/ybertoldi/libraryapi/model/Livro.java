package io.github.ybertoldi.libraryapi.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "livro", schema = "public")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    UUID id;

    @Column(name = "isbn")
    String isbn;

    @Column(name = "titulo")
    String titulo;

    @Column(name = "data_publicacao")
    Date dataPublicacao;

    @Column(name = "genero")
    String genero;

    @Column(name = "preco")
    double preco;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "autor", referencedColumnName = "id")
    @Column(name = "id_autor")
    Autor autor;

}
