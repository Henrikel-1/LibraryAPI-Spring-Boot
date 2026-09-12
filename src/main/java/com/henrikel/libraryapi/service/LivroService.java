package com.henrikel.libraryapi.service;

import com.henrikel.libraryapi.dto.LivroPatchDTO;
import com.henrikel.libraryapi.dto.LivroRequestDTO;
import com.henrikel.libraryapi.dto.LivroResponseDTO;
import com.henrikel.libraryapi.model.Livro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface LivroService {
    Page<LivroResponseDTO> listarTodos(Pageable pageable);
    LivroResponseDTO salvar(LivroRequestDTO dto);
    void deletar(Long id);
    LivroResponseDTO buscarLivro(Long id);
    LivroResponseDTO alterarLivro(Long id, LivroRequestDTO dto);
    List<LivroResponseDTO> buscarLivroQuery(String titulo);
    LivroResponseDTO alterarAtributo(Long id, LivroPatchDTO dto);
}
