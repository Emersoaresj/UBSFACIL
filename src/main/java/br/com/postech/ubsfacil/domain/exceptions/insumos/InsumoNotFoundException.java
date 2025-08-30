package br.com.postech.ubsfacil.domain.exceptions.insumos;

public class InsumoNotFoundException extends RuntimeException {
    public InsumoNotFoundException(String message) {
        super(message);
    }
}
