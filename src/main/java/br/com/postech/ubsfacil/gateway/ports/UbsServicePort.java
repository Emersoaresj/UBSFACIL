package br.com.postech.ubsfacil.gateway.ports;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.api.dto.ubs.UbsRequestDto;
import br.com.postech.ubsfacil.api.dto.ubs.UbsResponseDto;
import br.com.postech.ubsfacil.api.dto.ubs.UbsUpdateDto;

import java.util.List;

public interface UbsServicePort {

    ResponseDto cadastraUbs(UbsRequestDto ubsRequestDto);

    UbsResponseDto buscarUbsPorCnes(String cnes);

    List<UbsResponseDto> buscarPorCidadeUf(String cidade, String uf);

    ResponseDto atualizarUbs(String cnes, UbsUpdateDto ubsRequestDto);

    void deletarUbs(String cnes);
}
