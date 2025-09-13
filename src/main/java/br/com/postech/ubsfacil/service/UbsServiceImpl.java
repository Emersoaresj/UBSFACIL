package br.com.postech.ubsfacil.service;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.api.dto.ubs.UbsResponseDto;
import br.com.postech.ubsfacil.api.mapper.UbsMapper;
import br.com.postech.ubsfacil.domain.Ubs;
import br.com.postech.ubsfacil.domain.exceptions.ErroExternoException;
import br.com.postech.ubsfacil.domain.exceptions.ErroInternoException;
import br.com.postech.ubsfacil.domain.exceptions.ErroNegocioException;
import br.com.postech.ubsfacil.domain.exceptions.ubs.UbsNotFoundException;
import br.com.postech.ubsfacil.gateway.client.GeocordingServicePort;
import br.com.postech.ubsfacil.gateway.client.dto.Coordenada;
import br.com.postech.ubsfacil.gateway.ports.ubs.UbsRepositoryPort;
import br.com.postech.ubsfacil.gateway.ports.ubs.UbsServicePort;
import br.com.postech.ubsfacil.utils.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UbsServiceImpl implements UbsServicePort {


    private final UbsRepositoryPort ubsRepositoryPort;
    private final GeocordingServicePort geocodingServicePort;

    public UbsServiceImpl(UbsRepositoryPort ubsRepositoryPort, GeocordingServicePort geocodingServicePort) {
        this.ubsRepositoryPort = ubsRepositoryPort;
        this.geocodingServicePort = geocodingServicePort;
    }


    @Override
    public ResponseDto cadastraUbs(Ubs ubs) {
        try {

            if (ubsRepositoryPort.findByCnes(ubs.getCnes()).isPresent()) {
                log.error("UBS com CNES {} já cadastrada", ubs.getCnes());
                throw new ErroNegocioException("UBS com CNES " + ubs.getCnes() + " já cadastrada");
            }
           ubs.validarUbs();

            Coordenada coordenada = geocodingServicePort.buscarCoordenadasPorCep(ubs.getCep());
            ubs.setLatitude(coordenada.getLatitude());
            ubs.setLongitude(coordenada.getLongitude());

            Ubs saved = ubsRepositoryPort.cadastraUbs(ubs);

            return montaResponse(saved, "cadastro");

        } catch (ErroInternoException | ErroNegocioException | ErroExternoException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao cadastrar UBS", e);
            throw new ErroInternoException("Erro interno ao tentar cadastrar UBS: " + e.getMessage());
        }
    }

    @Override
    public UbsResponseDto buscarUbsPorCnes(String cnes) {
        Ubs.validarCnes(cnes);

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

        try {
            List<Ubs> ubsList;

            if (isNotBlank(cidade) && isNotBlank(uf)) {
                Ubs.validarUf(uf);
                ubsList = ubsRepositoryPort.findAllByCidadeAndUf(cidade, uf);

            } else if (isNotBlank(cidade)) {
                ubsList = ubsRepositoryPort.findAllByCidade(cidade);

            } else {
                Ubs.validarUf(uf);
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

    @Override
    public Ubs atualizarUbs(String cnes, Ubs novosDados) {
        Ubs.validarCnes(cnes);

        try {
            Ubs ubs = ubsRepositoryPort.findByCnes(cnes)
                    .orElseThrow(() -> new UbsNotFoundException(ConstantUtils.UBS_NAO_ENCONTRADA));

            atualizarDados(ubs, novosDados);
            ubs.validarUbs();

            return ubsRepositoryPort.atualizarUbs(ubs);
        } catch (UbsNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao atualizar UBS", e);
            throw new ErroInternoException("Erro interno ao tentar atualizar UBS: " + e.getMessage());
        }
    }

    @Override
    public void deletarUbs(String cnes) {
        Ubs.validarCnes(cnes);

        try {
            Ubs ubs = ubsRepositoryPort.findByCnes(cnes)
                    .orElseThrow(() -> new UbsNotFoundException(ConstantUtils.UBS_NAO_ENCONTRADA));

            ubsRepositoryPort.deletarUbs(ubs.getCnes());
        } catch (UbsNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao deletar UBS", e);
            throw new ErroInternoException("Erro interno ao tentar deletar UBS: " + e.getMessage());
        }
    }

    private void atualizarDados(Ubs ubs, Ubs novosDados) {
        if (novosDados.getNome() != null) ubs.setNome(novosDados.getNome());
        if (novosDados.getTelefone() != null) ubs.setTelefone(novosDados.getTelefone());
        if (novosDados.getLogradouro() != null) ubs.setLogradouro(novosDados.getLogradouro());
        if (novosDados.getNumero() != null) ubs.setNumero(novosDados.getNumero());
        if (novosDados.getCep() != null) ubs.setCep(novosDados.getCep());
        if (novosDados.getBairro() != null) ubs.setBairro(novosDados.getBairro());
        if (novosDados.getCidade() != null) ubs.setCidade(novosDados.getCidade());
        if (novosDados.getUf() != null) ubs.setUf(novosDados.getUf());
    }

    private ResponseDto montaResponse(Ubs ubs, String acao) {
        ResponseDto response = new ResponseDto();

        response.setMessage("cadastro".equals(acao) ? ConstantUtils.UBS_CADASTRADA : ConstantUtils.UBS_ATUALIZADA);

        Map<String, Object> data = new HashMap<>();
        data.put("nomeUbs", ubs.getNome());
        data.put("cidadeUbs", ubs.getCidade());
        data.put("bairroUbs", ubs.getBairro());
        data.put("telefoneUbs", ubs.getTelefone());

        response.setData(data);
        return response;
    }

    private boolean isNotBlank(String str) {
        return StringUtils.hasText(str);
    }
}
