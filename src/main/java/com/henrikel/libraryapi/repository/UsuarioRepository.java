package com.henrikel.libraryapi.repository;

import com.henrikel.libraryapi.model.Papel;
import com.henrikel.libraryapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
    List<Usuario> findByNome(String nome);
    Optional<Usuario> findByEmail(String email);
    boolean existsByPapel(Papel papel);
}
