package br.com.postech.ubsfacil.domain.exceptions;

public class ErroNegocioException extends RuntimeException {
    public ErroNegocioException(String message) {
        super(message);
    }
}
