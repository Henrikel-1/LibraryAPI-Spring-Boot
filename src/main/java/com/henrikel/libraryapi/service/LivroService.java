package com.henrikel.libraryapi.service;

import com.henrikel.libraryapi.model.Livro;

import java.util.List;

public interface LivroService {
    List<Livro> listarTodos();
    Livro salvar(Livro livro);
    Livro deletar(Long id);
    Livro buscarLivro(Long id);
    Livro alterarLivro(Long id, Livro livro);

}
