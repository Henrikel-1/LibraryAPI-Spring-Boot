package com.henrikel.libraryapi.dto.livroDTOS;

import java.time.Year;

public record LivroPatchDTO(
                            String titulo,
                            Year anoPubli,
                            String editora,
                            String escritor) {
}
