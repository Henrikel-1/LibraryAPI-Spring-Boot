package com.henrikel.libraryapi.dto.usuarioDTOS;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record LoginRequestDto(@Email String email,
                              @NotBlank @Size(min = 8, max = 100)
                              String senha) {
}
