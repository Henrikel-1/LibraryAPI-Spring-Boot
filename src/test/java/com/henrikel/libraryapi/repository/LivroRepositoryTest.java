package com.henrikel.libraryapi.repository;

import com.henrikel.libraryapi.model.Livro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LivroRepositoryTest {
    @Autowired
    private LivroRepository livroRepository;

    @Test
    @DisplayName("Deve retornar true quando existir um livro com mesmo titulo (ignorando case)")
    void existsByTituloIgnoreCase_deveRetornarTrueQuandoExistir(){
        // Arrange:
        Livro livro = new Livro(null, "titulo", Year.of(2005), "teste", "teste");
        livroRepository.save(livro);
        // Act:
        boolean existe = livroRepository.existsByTituloIgnoreCase("TITULO");
        // Assert:
        assertTrue(existe);
    }
    @Test
    @DisplayName("Deve retornar false quando não existir um livro com mesmo titulo")
    void existsByTituloIgnoreCase_deveRetornarFalseQuandoNaoExistir(){
        // Arrange:
        // Act:
        boolean existe = livroRepository.existsByTituloIgnoreCase("TITULO");
        // Assert:
        assertFalse(existe);
    }
    @Test
    @DisplayName("Deve retornar true quando existir um livro com mesmo titulo (ignorando case) de Id diferente")
    void existsByTituloIgnoreCaseAndIdNot_deveRetornarTrueQuandoExistirMesmoTituloEOIdDiferente(){
        // Arrange:
        Livro livro = new Livro(null, "titulo", Year.of(2005), "teste", "teste");
        Livro livro2 = new Livro(null, "titulo", Year.of(2005), "teste", "teste");
        livroRepository.save(livro2);
        Livro salvo = livroRepository.save(livro);
        // Act:
        boolean existe = livroRepository.existsByTituloIgnoreCaseAndIdNot("TITULO", salvo.getId());
        // Assert:
        assertTrue(existe);
    }
    @Test
    @DisplayName("Deve retornar false quando não existir um livro com mesmo titulo (ignorando case) de Id diferente")
    void existsByTituloIgnoreCaseAndIdNot_deveRetornarFalseQuandoNaoExistirMesmoTituloEOIdDiferente(){
        // Arrange:
        Livro livro = new Livro(null, "titulo", Year.of(2005), "teste", "teste");
        Livro salvo = livroRepository.save(livro);
        // Act:
        boolean existe = livroRepository.existsByTituloIgnoreCaseAndIdNot("TITULO", salvo.getId());
        // Assert:
        assertFalse(existe);
    }
    @Test
    @DisplayName("Deve retornar uma lista de livros com mesmo titulo")
    void findByTituloIgnoreCase_deveRetornarUmaListaDeLivrosComMesmoTitulo(){
        // Arrange:
        Livro livro = new Livro(null, "titulo", Year.of(2005), "teste", "teste");
        Livro salvo = livroRepository.save(livro);
        // Act:
        List<Livro> lista = livroRepository.findByTituloIgnoreCase("titulo");
        // Assert:
        assertEquals(1, lista.size());
        assertEquals(lista.getFirst().getId(), salvo.getId());
        assertEquals(lista.getFirst().getTitulo(), salvo.getTitulo());
        assertEquals(lista.getFirst().getAnoPubli(), salvo.getAnoPubli());
        assertEquals(lista.getFirst().getEscritor(), salvo.getEscritor());
        assertEquals(lista.getFirst().getEditora(), salvo.getEditora());
    }
    @Test
    @DisplayName("Deve retornar lista vazia quando não existir livro com o titulo")
    void findByTituloIgnoreCase_deveRetornarListaVaziaQuandoNaoExistir(){
        // Act:
        List<Livro> lista = livroRepository.findByTituloIgnoreCase("titulo");
        // Assert:
        assertTrue(lista.isEmpty());
    }
}