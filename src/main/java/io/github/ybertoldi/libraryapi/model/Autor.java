package io.github.ybertoldi.libraryapi.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
public class Autor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    String nome;

    @Column(name = "data_nascimento", nullable = false)
    LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 30)
    String nacionalidade;
}
