package br.com.postech.ubsfacil.gateway.ports;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.domain.Estoque;

public interface EstoqueServicePort {

    ResponseDto cadastrarEstoque(Estoque estoque);

    Estoque buscarEstoquePorId(Integer idEstoque);
}
