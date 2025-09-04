package br.com.postech.ubsfacil.gateway.ports;

import br.com.postech.ubsfacil.domain.Estoque;

import java.util.Optional;

public interface EstoqueRepositoryPort {

    Optional<Estoque> findByInsumoSku(String insumoSku);

    Estoque cadastrarEstoque(Estoque estoque);
}
