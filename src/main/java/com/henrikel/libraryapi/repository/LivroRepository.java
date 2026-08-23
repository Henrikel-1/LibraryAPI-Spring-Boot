package com.henrikel.libraryapi.repository;


import com.henrikel.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
