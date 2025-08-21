package br.com.postech.ubsfacil.gateway.database;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.api.mapper.UbsMapper;
import br.com.postech.ubsfacil.domain.Ubs;
import br.com.postech.ubsfacil.domain.exceptions.ErroInternoException;
import br.com.postech.ubsfacil.gateway.database.entity.UbsEntity;
import br.com.postech.ubsfacil.gateway.database.repository.UbsRepositoryJPA;
import br.com.postech.ubsfacil.gateway.ports.UbsRepositoryPort;
import br.com.postech.ubsfacil.utils.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class UbsRepositoryImpl implements UbsRepositoryPort {

    @Autowired
    private UbsRepositoryJPA ubsRepositoryJPA;

    @Override
    public ResponseDto cadastraUbs(Ubs ubs) {
        try {
            UbsEntity ubsEntity = UbsMapper.INSTANCE.domainToEntity(ubs);
            ubsRepositoryJPA.save(ubsEntity);
            return montaResponse(ubsEntity, "cadastro");
        } catch (Exception e) {
            log.error("Erro ao cadastrar UBS", e);
            throw new ErroInternoException("Erro ao cadastrar UBS: " + e.getMessage());
        }
    }

    @Override
    public Optional<Ubs> findByCnes(String cnes) {
        try {
            return ubsRepositoryJPA.findByCnes(cnes)
                    .map(UbsMapper.INSTANCE::entityToDomain);
        } catch (Exception e) {
            log.error("Erro ao buscar CNES", e);
            throw new ErroInternoException("Erro ao buscar CNES: " + e.getMessage());
        }
    }

    @Override
    public List<Ubs> findAllByCidadeAndUf(String cidade, String uf) {
        try {
            List<UbsEntity> entities = ubsRepositoryJPA.findAllByCidadeAndUf(cidade, uf);
            return UbsMapper.INSTANCE.listEntityToDomain(entities);
        } catch (Exception e) {
            log.error("Erro ao buscar UBS por cidade e UF", e);
            throw new ErroInternoException("Erro ao buscar UBS por cidade e UF: " + e.getMessage());
        }
    }

    @Override
    public List<Ubs> findAllByCidade(String cidade) {
        try {
            List<UbsEntity> entities = ubsRepositoryJPA.findAllByCidade(cidade);
            return UbsMapper.INSTANCE.listEntityToDomain(entities);
        } catch (Exception e) {
            log.error("Erro ao buscar UBS por cidade", e);
            throw new ErroInternoException("Erro ao buscar UBS por cidade: " + e.getMessage());
        }
    }

    @Override
    public List<Ubs> findAllByUf(String uf) {
        try {
            List<UbsEntity> entities = ubsRepositoryJPA.findAllByUf(uf);
            return UbsMapper.INSTANCE.listEntityToDomain(entities);
        } catch (Exception e) {
            log.error("Erro ao buscar UBS por UF", e);
            throw new ErroInternoException("Erro ao buscar UBS por UF: " + e.getMessage());
        }
    }

    @Override
    public ResponseDto atualizarUbs(Ubs ubs) {
        try {
            UbsEntity ubsEntity = UbsMapper.INSTANCE.updateDomainToEntity(ubs);
            ubsRepositoryJPA.save(ubsEntity);
            return montaResponse(ubsEntity, "update");
        } catch (Exception e) {
            log.error("Erro ao atualizar ubs", e);
            throw new ErroInternoException("Erro ao atualizar ubs: " + e.getMessage());
        }
    }


    private ResponseDto montaResponse(UbsEntity ubsEntity, String acao) {
        ResponseDto response = new ResponseDto();

        response.setMessage("cadastro".equals(acao) ? ConstantUtils.UBS_CADASTRADA : ConstantUtils.UBS_ATUALIZADA);

        Map<String, Object> data = new HashMap<>();
        data.put("nomeUbs", ubsEntity.getNome());
        data.put("cidadeUbs", ubsEntity.getCidade());
        data.put("bairroUbs", ubsEntity.getBairro());
        data.put("telefoneUbs", ubsEntity.getTelefone());

        response.setData(data);
        return response;
    }
}
