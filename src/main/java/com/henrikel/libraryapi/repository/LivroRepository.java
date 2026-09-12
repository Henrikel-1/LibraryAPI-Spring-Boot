package com.henrikel.libraryapi.repository;


import com.henrikel.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    boolean existsByTituloIgnoreCase(String titulo);
    boolean existsByTituloIgnoreCaseAndIdNot(String titulo, Long id);
    List<Livro> findByTitulo(String titulo);
}
