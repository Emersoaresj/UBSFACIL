package br.com.postech.ubsfacil.gateway.ports;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.api.dto.ubs.UbsRequestDto;
import br.com.postech.ubsfacil.api.dto.ubs.UbsResponseDto;

public interface UbsServicePort {

    ResponseDto cadastraUbs(UbsRequestDto ubsRequestDto);

    UbsResponseDto buscarUbsPorCnes(String cnes);
}
