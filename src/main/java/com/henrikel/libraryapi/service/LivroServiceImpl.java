package com.henrikel.libraryapi.service;

import com.henrikel.libraryapi.dto.LivroRequestDTO;
import com.henrikel.libraryapi.dto.LivroResponseDTO;
import com.henrikel.libraryapi.model.Livro;
import com.henrikel.libraryapi.repository.LivroRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

@Service
@AllArgsConstructor
public class LivroServiceImpl implements LivroService{

    private final LivroRepository livroRepository;

    public Page<Livro> listarTodos(Pageable page){
        return livroRepository.findAll(page);
    }

    public Livro salvar(LivroRequestDTO livro){
        Livro livro2 = new Livro();
        livro2.setTitulo(livro.titulo());
        return livroRepository.save(livro2);
    }

    public Livro deletar(Long id){
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        livroRepository.delete(livro);
        return livro;
    }
    public Livro buscarLivro(Long id){
        return livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    public List<LivroResponseDTO> buscarLivroQuery(String titulo){
        return livroRepository.findByTitulo(titulo).stream().map(livro -> new LivroResponseDTO(livro.getTitulo())).toList();
    }

    public Livro alterarLivro(Long id, LivroRequestDTO dto){
        Livro livro2 = livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        livro2.setTitulo(dto.titulo());
        return livroRepository.save(livro2);
    }

}
