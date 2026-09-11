package com.henrikel.libraryapi.service;

import com.henrikel.libraryapi.core.exception.BusinessException;
import com.henrikel.libraryapi.dto.LivroRequestDTO;
import com.henrikel.libraryapi.dto.LivroResponseDTO;
import com.henrikel.libraryapi.model.Livro;
import com.henrikel.libraryapi.repository.LivroRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import static com.henrikel.libraryapi.core.exception.TipoErro.*;

import java.util.List;



@Service
@AllArgsConstructor
public class LivroServiceImpl implements LivroService{

    private final LivroRepository livroRepository;

    public Page<LivroResponseDTO> listarTodos(Pageable page){
        return livroRepository.findAll(page).map(this::toResponse);
    }

    public LivroResponseDTO salvar(LivroRequestDTO livro){
        Livro livro2 = new Livro();
        livro2.setTitulo(livro.titulo());
        livroRepository.save(livro2);
        return toResponse(livro2);
    }

    public void deletar(Long id){
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new BusinessException(RECURSO_NAO_ENCONTRADO, "Livro não encontrado"));
        livroRepository.delete(livro);
    }
    public LivroResponseDTO buscarLivro(Long id){
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new BusinessException(RECURSO_NAO_ENCONTRADO, "Livro não encontrado"));
        return toResponse(livro);
    }

    public List<LivroResponseDTO> buscarLivroQuery(String titulo){
        return livroRepository.findByTitulo(titulo).stream().map(livro -> new LivroResponseDTO(livro.getTitulo())).toList();
    }

    public LivroResponseDTO alterarLivro(Long id, LivroRequestDTO dto){
        Livro livro2 = livroRepository.findById(id).orElseThrow(() -> new BusinessException(RECURSO_NAO_ENCONTRADO, "Livro não encontrado"));
        livro2.setTitulo(dto.titulo());
        livroRepository.save(livro2);
        return toResponse(livro2);
    }
    public LivroResponseDTO toResponse (Livro livro){
        return new LivroResponseDTO(livro.getTitulo());
    }

}
