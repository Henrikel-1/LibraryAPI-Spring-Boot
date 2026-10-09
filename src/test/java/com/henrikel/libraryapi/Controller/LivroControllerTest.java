package com.henrikel.libraryapi.Controller;

import com.henrikel.libraryapi.controller.LivroController;

import com.henrikel.libraryapi.service.LivroService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;


@WebMvcTest(LivroController.class)
class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LivroService livroService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve listar os livros com status 200")
    void deveListarLivros() throws Exception {
        // Arrange: configure o mock do service pra devolver uma Page de exemplo

        // Act + Assert: dispara um GET /livros e confere o status e o corpo da resposta
    }
}