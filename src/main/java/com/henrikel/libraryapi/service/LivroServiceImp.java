package com.henrikel.libraryapi.service;

import com.henrikel.libraryapi.model.Livro;
import com.henrikel.libraryapi.repository.LivroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LivroServiceImp implements LivroService{

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
    public Livro alterarLivro(Long id, Livro livro){
        Livro livro2 = livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        livro2.setTitulo(livro.getTitulo());
        return livroRepository.save(livro2);
    }

}
