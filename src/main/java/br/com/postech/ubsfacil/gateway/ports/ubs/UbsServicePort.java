package br.com.postech.ubsfacil.gateway.ports.ubs;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.api.dto.ubs.UbsResponseDto;
import br.com.postech.ubsfacil.domain.Ubs;

import java.util.List;

public interface UbsServicePort {

    ResponseDto cadastraUbs(Ubs ubs);

    UbsResponseDto buscarUbsPorCnes(String cnes);

    List<UbsResponseDto> buscarPorCidadeUf(String cidade, String uf);

    Ubs atualizarUbs(String cnes, Ubs ubsRequestDto);

    void deletarUbs(String cnes);
}
