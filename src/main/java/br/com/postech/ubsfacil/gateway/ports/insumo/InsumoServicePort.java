package br.com.postech.ubsfacil.gateway.ports.insumo;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.domain.Insumo;

import java.util.List;

public interface InsumoServicePort {

    ResponseDto cadastrarInsumo(Insumo insumo);

    Insumo buscarInsumoPorBarcode (String barcode);

    List<Insumo> buscarPorTipo(String tipo);

    List<Insumo> buscarTodos();

    Insumo atualizarInsumo(Insumo insumo);

    void deletarInsumo(String barcode);
}
