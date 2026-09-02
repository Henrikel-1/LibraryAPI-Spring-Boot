package com.henrikel.libraryapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LivroRequestDTO(@NotBlank(message = "Titulo é obrigatório")
                              @Size(min = 1, max = 200)
                              String titulo) {

}
