package com.henrikel.libraryapi.controller;

import com.henrikel.libraryapi.model.Livro;
import com.henrikel.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
@AllArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @GetMapping
    public List<Livro> listarLivros(){
        return livroService.listarTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Livro salvarLivro(@RequestBody @Valid Livro livro){
        livroService.salvar(livro);
        return livro;
    }
    @DeleteMapping("/{id}")
    public Livro excluirLivro(@PathVariable long id){
        return livroService.deletar(id);
    }
}
