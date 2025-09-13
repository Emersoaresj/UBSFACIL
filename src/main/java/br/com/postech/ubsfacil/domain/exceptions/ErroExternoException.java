package br.com.postech.ubsfacil.domain.exceptions;

public class ErroExternoException extends RuntimeException {
    public ErroExternoException(String message) {
        super(message);
    }
}
