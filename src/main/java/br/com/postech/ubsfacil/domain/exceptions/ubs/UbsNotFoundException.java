package br.com.postech.ubsfacil.domain.exceptions.ubs;

public class UbsNotFoundException extends RuntimeException {
    public UbsNotFoundException(String message) {
        super(message);
    }
}
