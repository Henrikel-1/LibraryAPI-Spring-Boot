package com.henrikel.libraryapi.dto.usuarioDTOS;

import com.henrikel.libraryapi.model.Papel;


public record UsuarioResponseDTO(String nome,
                                 String email,
                                 Papel papel) {
}
