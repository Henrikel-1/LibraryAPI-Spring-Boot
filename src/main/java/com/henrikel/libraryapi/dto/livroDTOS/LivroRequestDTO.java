package com.henrikel.libraryapi.dto.livroDTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.Year;

public record LivroRequestDTO(@NotBlank(message = "O Titulo é obrigatório")
                              @Size(min = 1, max = 200)
                              String titulo,
                              @NotNull(message = "O ano de publicação é obrigatório")
                              @PastOrPresent(message = "O ano de publicação não pode estar no futuro")
                              Year anoPubli,
                              @NotBlank(message = "A editora é obrigatório")
                              @Size(min = 1, max = 200)
                              String editora,
                              @NotBlank(message = "O escritor é obrigatório")
                              @Size(min = 1, max = 200)
                              String escritor) {

}
