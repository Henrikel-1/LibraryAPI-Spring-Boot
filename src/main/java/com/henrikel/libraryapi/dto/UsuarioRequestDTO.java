package com.henrikel.libraryapi.dto;

import com.henrikel.libraryapi.model.Papel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(@NotBlank(message = "Nome é obrigatório")
                                @Size(min = 1, max = 200)
                                String nome,
                                @NotBlank(message = "Email é obrigatório")
                                @Size(min = 1, max = 200)
                                @Email
                                String email,
                                @NotBlank(message = "Senha é obrigatório")
                                @Size(min = 1, max = 200)
                                String senha,
                                @NotNull(message = "Papel é obrigatório")
                                Papel papel) {
}
