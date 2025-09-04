package br.com.postech.ubsfacil.gateway.ports;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.api.dto.estoque.EstoqueResponseDto;
import br.com.postech.ubsfacil.domain.Estoque;

import java.util.List;
import java.util.Optional;

public interface EstoqueServicePort {

    ResponseDto cadastrarEstoque(Estoque estoque);

    Estoque buscarEstoquePorId(Integer idEstoque);

    Optional<Estoque> buscarPorFiltro(String cnes, String sku);

    List<Estoque> buscarTodos();

    ResponseDto atualizarEstoque(Integer idEstoque, Estoque estoque);

    void deletarEstoque(Integer idEstoque);

    EstoqueResponseDto registrarMovimentacao(Estoque estoque, String tipoMovimentacao, String motivo);
}
