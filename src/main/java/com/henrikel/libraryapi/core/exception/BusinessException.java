package com.henrikel.libraryapi.core.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final TipoErro tipo;

    public BusinessException( TipoErro tipo, String message) {
        super(message);
        this.tipo = tipo;
    }
}
