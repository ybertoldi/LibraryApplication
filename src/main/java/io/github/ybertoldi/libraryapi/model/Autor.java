package io.github.ybertoldi.libraryapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
public class Autor {
    @Id
    @Column(name = "id")
    UUID id;

    @Column(name = "nome")
    String nome;

    @Column(name = "data_nascimento")
    Date dataNascimento;

    @Column(name = "nacionalidade")
    String nacionalidade;
}
