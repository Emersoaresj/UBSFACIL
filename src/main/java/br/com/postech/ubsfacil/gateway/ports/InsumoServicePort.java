package br.com.postech.ubsfacil.gateway.ports;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.domain.Insumo;

import java.util.List;

public interface InsumoServicePort {

    ResponseDto cadastrarInsumo(Insumo insumo);

    Insumo buscarInsumoPorSku(String sku);

    List<Insumo> buscarPorTipo(String tipo);

    List<Insumo> buscarTodos();
}
