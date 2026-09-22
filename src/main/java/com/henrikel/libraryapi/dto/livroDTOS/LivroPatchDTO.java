package com.henrikel.libraryapi.dto.livroDTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.Year;

public record LivroPatchDTO(
        @Size(min = 1, max = 200)
        String titulo,
        @PastOrPresent(message = "O ano de publicação não pode estar no futuro")
        Year anoPubli,
        @Size(min = 1, max = 200)
        String editora,
        @Size(min = 1, max = 200)
        String escritor) {
}
