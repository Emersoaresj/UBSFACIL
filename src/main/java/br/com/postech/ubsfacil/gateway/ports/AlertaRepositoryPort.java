package br.com.postech.ubsfacil.gateway.ports;

import br.com.postech.ubsfacil.domain.Alerta;

public interface AlertaRepositoryPort {

    void salvar(Alerta alerta);
}
