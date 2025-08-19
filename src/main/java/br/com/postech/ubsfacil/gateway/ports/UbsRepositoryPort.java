package br.com.postech.ubsfacil.gateway.ports;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.domain.Ubs;

import java.util.Optional;

public interface UbsRepositoryPort {

    ResponseDto cadastraUbs(Ubs ubs);

    Optional<Ubs> findByCnes(String cnes);
}
