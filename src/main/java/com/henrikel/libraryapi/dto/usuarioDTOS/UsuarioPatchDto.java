package com.henrikel.libraryapi.dto.usuarioDTOS;

import com.henrikel.libraryapi.model.Papel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UsuarioPatchDto(
                              @Size(min = 1, max = 200)
                              String nome,

                              @Size(min = 1, max = 200)
                              @Email
                              String email,

                              @Size(min = 1, max = 200)
                              String senha,

                              Papel papel) {
}
