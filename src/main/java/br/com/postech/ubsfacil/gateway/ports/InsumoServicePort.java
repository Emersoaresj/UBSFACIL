package br.com.postech.ubsfacil.gateway.ports;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.domain.Insumo;

public interface InsumoServicePort {

    ResponseDto cadastrarInsumo(Insumo insumo);
}
