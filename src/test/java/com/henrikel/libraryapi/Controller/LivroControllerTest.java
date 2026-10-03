package com.henrikel.libraryapi.Controller;

import com.henrikel.libraryapi.controller.LivroController;
import com.henrikel.libraryapi.dto.livroDTOS.LivroRequestDTO;
import com.henrikel.libraryapi.model.Livro;
import com.henrikel.libraryapi.service.LivroService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Year;

@ExtendWith(MockitoExtension.class
)
public class LivroControllerTest {

    @Mock
    private LivroService livroService;

    @InjectMocks
    private LivroController livroController;

    @Test
    @DisplayName("Deve listar todos os livros")
    void deveListarTodosOsLivros(){
        // Arrange:
        LivroRequestDTO dto = new LivroRequestDTO("Teste", Year.of(2005), "Teste", "Testador");
        livroService.salvar(dto);
        // Act:
        livroService.listarTodos();
        // Assert:
    }
}
