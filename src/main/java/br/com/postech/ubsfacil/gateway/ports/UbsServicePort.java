package br.com.postech.ubsfacil.gateway.ports;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.api.dto.UbsRequestDto;

public interface UbsServicePort {

    ResponseDto cadastraUbs(UbsRequestDto ubsRequestDto);
}
