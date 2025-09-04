package br.com.postech.ubsfacil.gateway.ports;

import br.com.postech.ubsfacil.domain.Estoque;

public interface AlertaServicePort {

    void verificarEAvisar(Estoque estoque);
}
