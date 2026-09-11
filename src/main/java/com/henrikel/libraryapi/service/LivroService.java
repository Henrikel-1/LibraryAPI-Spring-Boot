package com.henrikel.libraryapi.service;

import com.henrikel.libraryapi.dto.LivroRequestDTO;
import com.henrikel.libraryapi.dto.LivroResponseDTO;
import com.henrikel.libraryapi.model.Livro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface LivroService {
    Page<Livro> listarTodos(Pageable pageable);
    Livro salvar(LivroRequestDTO dto);
    Livro deletar(Long id);
    Livro buscarLivro(Long id);
    Livro alterarLivro(Long id, LivroRequestDTO dto);
    List<LivroResponseDTO> buscarLivroQuery(String titulo);
}
