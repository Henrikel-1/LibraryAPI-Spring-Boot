package com.henrikel.libraryapi.repository;

import com.henrikel.libraryapi.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo,Long> {
    long countByUsuarioIdAndDataDevolucaoIsNull(Long id);
}
