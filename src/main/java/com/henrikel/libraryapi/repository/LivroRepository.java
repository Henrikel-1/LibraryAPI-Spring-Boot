package com.henrikel.libraryapi.repository;


import com.henrikel.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    boolean existsByTituloIgnoreCase(String titulo);
    boolean existsByTituloIgnoreCaseAndIdNot(String titulo, Long id);
    List<Livro> findByTitulo(String titulo);
}
