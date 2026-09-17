package com.henrikel.libraryapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.Year;

public record LivroPatchDTO(
                            String titulo,
                            Year anoPubli,
                            String editora,
                            String escritor) {
}
