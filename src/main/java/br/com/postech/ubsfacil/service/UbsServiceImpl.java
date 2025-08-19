package br.com.postech.ubsfacil.service;

import br.com.postech.ubsfacil.api.mapper.UbsMapper;
import br.com.postech.ubsfacil.domain.Ubs;
import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.api.dto.UbsRequestDto;
import br.com.postech.ubsfacil.domain.exceptions.ErroInternoException;
import br.com.postech.ubsfacil.domain.exceptions.ErroNegocioException;
import br.com.postech.ubsfacil.gateway.ports.UbsRepositoryPort;
import br.com.postech.ubsfacil.gateway.ports.UbsServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UbsServiceImpl implements UbsServicePort {

    @Autowired
    private UbsRepositoryPort ubsRepositoryPort;


    @Override
    public ResponseDto cadastraUbs(UbsRequestDto ubsRequestDto) {
        try {

            if (ubsRepositoryPort.findByCnes(ubsRequestDto.getCnes()).isPresent()) {
                log.error("UBS com CNES {} já cadastrada", ubsRequestDto.getCnes());
                throw new ErroNegocioException("UBS com CNES " + ubsRequestDto.getCnes() + " já cadastrada");
            }

            Ubs ubs = UbsMapper.INSTANCE.requestCreateToDomain(ubsRequestDto);
            return ubsRepositoryPort.cadastraUbs(ubs);

        } catch (ErroInternoException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao cadastrar UBS", e);
            throw new ErroInternoException("Erro interno ao tentar cadastrar UBS: " + e.getMessage());
        }
    }
}
