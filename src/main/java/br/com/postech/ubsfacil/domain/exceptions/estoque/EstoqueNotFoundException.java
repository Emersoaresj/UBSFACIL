package br.com.postech.ubsfacil.domain.exceptions.estoque;

public class EstoqueNotFoundException extends RuntimeException {
    public EstoqueNotFoundException(String message) {
        super(message);
    }
}
