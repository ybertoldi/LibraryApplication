package io.github.ybertoldi.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
@Data
public class Autor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    String nome;

    @Column(name = "data_nascimento", nullable = false)
    LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50)
    String nacionalidade;

    @Transient
//    @OneToMany(mappedBy = "autor")
    List<Livro> livros;
}
