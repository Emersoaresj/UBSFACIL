package br.com.postech.ubsfacil.gateway.ports.estoque;

import br.com.postech.ubsfacil.domain.Estoque;

import java.util.List;
import java.util.Optional;

public interface EstoqueRepositoryPort {

    void deletarTodosPorBarcode (String barcode);

    Estoque cadastrarEstoque(Estoque estoque);

    Optional<Estoque> findByIdEstoque(Integer id);

    Optional<Estoque> findByCnesAndInsumoBarcode(String cnes, String sku);

    List<Estoque> findAll();

    Estoque atualizarEstoque(Estoque estoque);

    void deletarEstoque(Integer idEstoque);

    List<Estoque> findByUbsCnes(String cnes);

    List<Estoque> buscaPorInsumoBarcode(String barcode);
}
