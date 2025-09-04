package br.com.postech.ubsfacil.gateway.ports;

import br.com.postech.ubsfacil.domain.Estoque;

import java.util.List;
import java.util.Optional;

public interface EstoqueRepositoryPort {

    Optional<Estoque> findByInsumoSku(String insumoSku);

    Estoque cadastrarEstoque(Estoque estoque);

    Optional<Estoque> findByIdEstoque(Integer id);

    Optional<Estoque> findByCnesAndSku(String cnes, String sku);

    List<Estoque> findAll();

    Estoque atualizarEstoque(Estoque estoque);

    void deletarEstoque(Integer idEstoque);
}
