package com.henrikel.libraryapi.controller;

import com.henrikel.libraryapi.dto.livroDTOS.LivroPatchDTO;
import com.henrikel.libraryapi.dto.livroDTOS.LivroRequestDTO;
import com.henrikel.libraryapi.dto.livroDTOS.LivroResponseDTO;

import com.henrikel.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
@AllArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @GetMapping
    public Page<LivroResponseDTO> listarLivros(@PageableDefault(size = 10, sort = "titulo")Pageable pageable){
        return livroService.listarTodos(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LivroResponseDTO salvarLivro(@RequestBody @Valid LivroRequestDTO dto){
        return livroService.salvar(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirLivro(@PathVariable Long id){
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/titulo")
    public List<LivroResponseDTO> buscarLivroQuery(@RequestParam("titulo") String titulo){
        return livroService.buscarLivroQuery(titulo);
    }

    @GetMapping("/{id}")
    public LivroResponseDTO buscarLivro(@PathVariable Long id){
        return livroService.buscarLivro(id);
    }

    @PutMapping("/{id}")
    public LivroResponseDTO alterarLivro(@PathVariable Long id, @RequestBody @Valid LivroRequestDTO dto){
        return livroService.alterarLivro(id, dto);
    }
    @PatchMapping("/{id}")
    public LivroResponseDTO alterarAtributo(@PathVariable Long id, @RequestBody LivroPatchDTO dto){
        return livroService.alterarAtributo(id, dto);
    }
}
