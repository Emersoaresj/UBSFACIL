package br.com.postech.ubsfacil.service;

import br.com.postech.ubsfacil.api.dto.ubs.UbsResponseDto;
import br.com.postech.ubsfacil.api.mapper.UbsMapper;
import br.com.postech.ubsfacil.domain.Ubs;
import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.api.dto.ubs.UbsRequestDto;
import br.com.postech.ubsfacil.domain.exceptions.ErroInternoException;
import br.com.postech.ubsfacil.domain.exceptions.ErroNegocioException;
import br.com.postech.ubsfacil.domain.exceptions.ubs.UbsNotFoundException;
import br.com.postech.ubsfacil.gateway.ports.UbsRepositoryPort;
import br.com.postech.ubsfacil.gateway.ports.UbsServicePort;
import br.com.postech.ubsfacil.utils.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public UbsResponseDto buscarUbsPorCnes(String cnes) {
        if (!cnes.matches("\\d+")) {
            throw new ErroNegocioException("O CNES deve conter apenas números.");
        }

        try {
            Ubs ubs = ubsRepositoryPort.findByCnes(cnes)
                    .orElseThrow(() -> new UbsNotFoundException(ConstantUtils.UBS_NAO_ENCONTRADA));
            return UbsMapper.INSTANCE.domainToDto(ubs);

        } catch (UbsNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar UBS", e);
            throw new ErroInternoException("Erro interno ao tentar buscar UBS: " + e.getMessage());
        }
    }

    @Override
    public List<UbsResponseDto> buscarPorCidadeUf(String cidade, String uf) {

        if ((cidade == null || cidade.isBlank()) && (uf == null || uf.isBlank())) {
            throw new ErroNegocioException("É necessário informar pelo menos cidade ou UF.");
        }

        List<Ubs> ubsList;

        try {
            if (cidade != null && !cidade.isBlank() && uf != null && !uf.isBlank()) {
                // validação da UF (2 letras maiúsculas)
                if (!uf.matches("^[A-Z]{2}$")) {
                    throw new ErroNegocioException(ConstantUtils.ERRO_UF);
                }

                // Busca por cidade e UF
                ubsList = ubsRepositoryPort.findAllByCidadeAndUf(cidade, uf);

            } else if (cidade != null && !cidade.isBlank()) {
                ubsList = ubsRepositoryPort.findAllByCidade(cidade);

            } else {
                if (!uf.matches("^[A-Z]{2}$")) {
                    throw new ErroNegocioException(ConstantUtils.ERRO_UF);
                }
                ubsList = ubsRepositoryPort.findAllByUf(uf);
            }

            if (ubsList.isEmpty()) {
                throw new UbsNotFoundException(ConstantUtils.UBS_NAO_ENCONTRADA);
            }

            return ubsList.stream()
                    .map(UbsMapper.INSTANCE::domainToDto)
                    .toList();

        } catch (UbsNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar UBS", e);
            throw new ErroInternoException("Erro interno ao tentar buscar UBS: " + e.getMessage());
        }
    }
}
