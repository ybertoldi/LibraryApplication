package io.github.ybertoldi.libraryapi.repository;

import io.github.ybertoldi.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
}
