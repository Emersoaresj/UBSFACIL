package br.com.postech.ubsfacil.gateway.ports;

import br.com.postech.ubsfacil.domain.Movimentacao;

public interface MovimentacaoRepositoryPort {

    void salvarMovimentacao(Movimentacao movimentacao);
}
