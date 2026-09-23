package com.henrikel.libraryapi.service;

import com.henrikel.libraryapi.core.exception.BusinessException;
import com.henrikel.libraryapi.core.exception.TipoErro;
import com.henrikel.libraryapi.dto.livroDTOS.LivroRequestDTO;
import com.henrikel.libraryapi.dto.livroDTOS.LivroResponseDTO;
import com.henrikel.libraryapi.model.Livro;
import com.henrikel.libraryapi.repository.LivroRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.Optional;

import static com.henrikel.libraryapi.core.exception.TipoErro.CONFLITO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {
    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroServiceImpl livroService;

    @Test
    @DisplayName("Deve salvar quando não existir um livro com o mesmo titulo!")
    void deveSalvarQuandoNaoExistirLivroComMesmoTitulo(){
        // Arrange:
        LivroRequestDTO dto = new LivroRequestDTO("Teste", Year.of(2005), "Teste", "Testador");
        when(livroRepository.existsByTituloIgnoreCase(dto.titulo())).thenReturn(false);
        // Act:
        LivroResponseDTO resultado = livroService.salvar(dto);
        // Assert:
        assertEquals(dto.titulo(), resultado.titulo());
        assertEquals(dto.anoPubli(), resultado.anoPubli());
        assertEquals(dto.editora(), resultado.editora());
        assertEquals(dto.escritor(), resultado.escritor());
        verify(livroRepository).save(any(Livro.class));
    }
    @Test
    @DisplayName("Não deve salvar quando existir um livro com o mesmo titulo!")
    void naoDeveSalvarQuandoExistirUmLivroComMesmoTitulo(){
        // Arrange:
        LivroRequestDTO dto = new LivroRequestDTO("Teste", Year.of(2005), "Teste", "Testador");
        when(livroRepository.existsByTituloIgnoreCase(dto.titulo())).thenReturn(true);
        // Act + Assert:
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            livroService.salvar(dto);
        });
        // extra Asserts:
        verify(livroRepository, never()).save(any(Livro.class));
        assertEquals(TipoErro.CONFLITO, exception.getTipo());
    }
    @Test
    @DisplayName("Deve retornar livro quando o id existir")
    void deveRetornarLivroQuandoIdExistri(){
        // Arrange:
        Livro livro = new Livro(1L, "teste", Year.of(2005), "teste", "testador");
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        // Act:
        LivroResponseDTO resultado = livroService.buscarLivro(1L);
        // Assert:
        assertEquals(livro.getTitulo(), resultado.titulo());
        assertEquals(livro.getAnoPubli(), resultado.anoPubli());
        assertEquals(livro.getEditora(), resultado.editora());
        assertEquals(livro.getEscritor(), resultado.escritor());
    }
    @Test
    @DisplayName("Deve lançar excecao quando o id não existir")
    void deveLancarExcecaoQuandoIdNaoExistir(){
        // Arrange:
        when(livroRepository.findById(1L)).thenReturn(Optional.empty());
        // Act + assert:
        BusinessException exception = assertThrows(BusinessException.class, () -> livroService.buscarLivro(1L));
        // Extra assert :
        assertEquals(TipoErro.RECURSO_NAO_ENCONTRADO, exception.getTipo());
    }
}
