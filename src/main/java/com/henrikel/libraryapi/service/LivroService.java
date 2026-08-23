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
}
