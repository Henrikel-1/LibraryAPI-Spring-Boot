package com.henrikel.libraryapi.service;

import com.henrikel.libraryapi.model.Livro;
import com.henrikel.libraryapi.repository.LivroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public List<Livro> listarTodos(){
        return livroRepository.findAll();
    }

    public Livro salvar(Livro livro){
        return livroRepository.save(livro);
    }

    public Livro deletar(Long id){
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        livroRepository.delete(livro);
        return livro;
    }
    public Livro buscarLivro(Long id){
        return livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

}
