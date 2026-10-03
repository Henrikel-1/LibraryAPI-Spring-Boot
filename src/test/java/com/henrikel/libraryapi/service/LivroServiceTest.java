package com.henrikel.libraryapi.service;

import com.henrikel.libraryapi.core.exception.BusinessException;
import com.henrikel.libraryapi.core.exception.TipoErro;
import com.henrikel.libraryapi.dto.livroDTOS.LivroPatchDTO;
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

import static org.junit.jupiter.api.Assertions.*;
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
        when(livroRepository.save(any(Livro.class))).thenAnswer(invocation -> invocation.getArgument(0));
        // Act:
        LivroResponseDTO resultado = livroService.salvar(dto);
        // Assert:
        assertEquals(dto.titulo(), resultado.titulo());
        assertEquals(dto.anoPubli(), resultado.anoPubli());
        assertEquals(dto.editora(), resultado.editora());
        assertEquals(dto.escritor(), resultado.escritor());

        verify(livroRepository, times(1)).save(any(Livro.class));
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
    void deveRetornarLivroQuandoIdExistir(){
        // Arrange:
        Livro livro = new Livro(1L, "teste", Year.of(2005), "teste", "testador");
        when(livroRepository.findById(livro.getId())).thenReturn(Optional.of(livro));
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
    @Test
    @DisplayName("Deve deletar o livro quando o id existir")
    void deletarLivroSeExistriId(){
        // Arrange:
        Livro livro = new Livro(1L, "teste", Year.of(2005), "teste", "testador");
        when(livroRepository.findById(livro.getId())).thenReturn(Optional.of(livro));
        // Act:
        livroService.deletar(livro.getId());
        // Assert:
        verify(livroRepository).delete(livro);

    }
    @Test
    @DisplayName("Deve lancar excecao quando nao existir o id")
    void deveLancarExcecaoCasoIdNaoExista(){
        // Arrange:
        when(livroRepository.findById(1L)).thenReturn(Optional.empty());
        // Act + Assert:
        BusinessException exception = assertThrows(BusinessException.class, () -> livroService.deletar(1L));
        // Extra assert:
        assertEquals(TipoErro.RECURSO_NAO_ENCONTRADO, exception.getTipo());
    }
    @Test
    @DisplayName("Deve alterar atributos do livro caso não exista outro livro com o mesmo titulo")
    void deveAlterarAtributosCasoNaoExistaOutroLivroMesmoTitulo(){
        // Arrange:
        LivroRequestDTO livro = new LivroRequestDTO("Teste2", Year.of(2005), "Teste2", "Testador2");
        Livro livro2 = new Livro(1L, "teste", Year.of(2005), "teste", "testador");
        when(livroRepository.existsByTituloIgnoreCaseAndIdNot(livro.titulo(), 1L)).thenReturn(false);
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro2));
        // Act:
        livroService.alterarLivro(1L, livro);
        // Assert:
        assertEquals(livro.titulo(), livro2.getTitulo());
        assertEquals(livro.anoPubli(),livro2.getAnoPubli());
        assertEquals(livro.editora(),livro2.getEditora());
        assertEquals(livro.escritor(),livro2.getEscritor());
    }
    @Test
    @DisplayName("Deve lancar excecao caso já exista um livro com este titulo")
    void deveLancarExcecaoCasoExistaUmLivroComEsteTitulo(){
        // Arrange:
        LivroRequestDTO livro = new LivroRequestDTO("Teste2", Year.of(2005), "Teste2", "Testador2");
        when(livroRepository.existsByTituloIgnoreCaseAndIdNot(livro.titulo(), 1L)).thenReturn(true);
        // Act + assert:
        BusinessException exception = assertThrows(BusinessException.class, () -> livroService.alterarLivro(1L, livro));
        // Extra asset:
        assertEquals(TipoErro.CONFLITO, exception.getTipo());
    }
    @Test
    @DisplayName("Deve lancar excecao caso o livro não seja encontrato")
    void deveLancarExcecaoCasoLivroNaoEncontrato(){
        // Arrange:
        LivroRequestDTO livro = new LivroRequestDTO("Teste2", Year.of(2005), "Teste2", "Testador2");
        when(livroRepository.findById(1L)).thenReturn(Optional.empty());
        // Act + assert
        BusinessException exception = assertThrows(BusinessException.class, () -> livroService.alterarLivro(1L, livro));
        // Extra assert:
        assertEquals(TipoErro.RECURSO_NAO_ENCONTRADO, exception.getTipo());
    }
    @Test
    @DisplayName("Deve alterar todos os Atributos do livro")
    void deveAlterarTodosAtributosDoLivro(){
        //Arrange:
        LivroPatchDTO livroPatchDTO = new LivroPatchDTO("Teste2", Year.of(2005), "Teste2", "Testador2");
        Livro livro = new Livro(1L, "teste", Year.of(2005), "teste", "testador");
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(livroRepository.existsByTituloIgnoreCaseAndIdNot(livroPatchDTO.titulo(), 1L)).thenReturn(false);
        //Act:
        LivroResponseDTO resultado = livroService.alterarAtributo(1L, livroPatchDTO);
        //Assert:
        assertEquals(resultado.titulo(), livroPatchDTO.titulo());
        assertEquals(resultado.anoPubli(), livroPatchDTO.anoPubli());
        assertEquals(resultado.editora(),livroPatchDTO.editora());
        assertEquals(resultado.escritor(), livroPatchDTO.escritor());
    }
    @Test
    @DisplayName("Deve alterar parcialmente os atributos do livro")
    void deveAlterarParcialmenteOsAtributosDoLivro(){
        //Arrange:
        LivroPatchDTO livroPatchDTO = new LivroPatchDTO("Teste2", null, null, null);
        Livro livro = new Livro(1L, "teste", Year.of(2005), "teste", "testador");
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(livroRepository.existsByTituloIgnoreCaseAndIdNot(livroPatchDTO.titulo(), 1L)).thenReturn(false);
        Year anoOriginal = livro.getAnoPubli();
        String editoraOriginal = livro.getEditora();
        String escritorOriginal = livro.getEscritor();
        //Act:
        LivroResponseDTO resultado = livroService.alterarAtributo(1L, livroPatchDTO);
        //Assert:
        assertEquals(resultado.titulo(), livroPatchDTO.titulo());
        assertEquals(resultado.anoPubli(), anoOriginal);
        assertEquals(resultado.editora(), editoraOriginal);
        assertEquals(resultado.escritor(), escritorOriginal);
    }
    @Test
    @DisplayName("Deve lancar excecao caso id nao encontrado")
    void deveLancarExcecaoCasoIdNaoEncontrado(){
        //Arrange:
        LivroPatchDTO livroPatchDTO = new LivroPatchDTO("Teste2", null, null, null);
        when(livroRepository.findById(1L)).thenReturn(Optional.empty());
        //Act + assert:
        BusinessException exception = assertThrows(BusinessException.class, () -> livroService.alterarAtributo(1L, livroPatchDTO));
        //Extra assert:
        assertEquals(TipoErro.RECURSO_NAO_ENCONTRADO, exception.getTipo());
    }
    @Test
    @DisplayName("Deve lancar excecao caso já exista um livro com mesmo titulo")
    void deveLancarExcecaoCasoExistaLivroComMesmoTitulo(){
        //Arrange:
        LivroPatchDTO livroPatchDTO = new LivroPatchDTO("Teste2", null, null, null);
        Livro livro = new Livro(1L, "teste", Year.of(2005), "teste", "testador");
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(livroRepository.existsByTituloIgnoreCaseAndIdNot(livroPatchDTO.titulo(), 1L)).thenReturn(true);
        //Act + assert:
        BusinessException exception = assertThrows(BusinessException.class, () -> livroService.alterarAtributo(1L, livroPatchDTO));
        //Extra assert:
        assertEquals(TipoErro.CONFLITO, exception.getTipo());
    }

}








