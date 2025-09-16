package br.com.postech.ubsfacil.gateway.database;

import br.com.postech.ubsfacil.api.dto.ubs.UbsEstoqueProjection;
import br.com.postech.ubsfacil.api.mapper.UbsMapper;
import br.com.postech.ubsfacil.domain.Ubs;
import br.com.postech.ubsfacil.domain.exceptions.ErroInternoException;
import br.com.postech.ubsfacil.gateway.database.entity.UbsEntity;
import br.com.postech.ubsfacil.gateway.database.repository.UbsRepositoryJPA;
import br.com.postech.ubsfacil.gateway.ports.ubs.UbsRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UbsRepositoryImpl implements UbsRepositoryPort {

    @Autowired
    private UbsRepositoryJPA ubsRepositoryJPA;

    @Transactional
    @Override
    public Ubs cadastraUbs(Ubs ubs) {
        try {
            UbsEntity ubsEntity = UbsMapper.INSTANCE.domainToEntity(ubs);
            ubsRepositoryJPA.save(ubsEntity);
            return UbsMapper.INSTANCE.entityToDomain(ubsEntity);
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

    @Transactional
    @Override
    public Ubs atualizarUbs(Ubs ubs) {
        try {
            UbsEntity ubsEntity = UbsMapper.INSTANCE.updateDomainToEntity(ubs);
            ubsRepositoryJPA.save(ubsEntity);
            return UbsMapper.INSTANCE.entityToDomain(ubsEntity);
        } catch (Exception e) {
            log.error("Erro ao atualizar ubs", e);
            throw new ErroInternoException("Erro ao atualizar ubs: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deletarUbs(String cnes) {
        try {
            ubsRepositoryJPA.deleteByCnes(cnes);
        } catch (Exception e) {
            log.error("Erro ao deletar UBS", e);
            throw new ErroInternoException("Erro ao deletar UBS: " + e.getMessage());
        }
    }

    @Override
    public List<UbsEstoqueProjection> buscaUbsComEstoque(String sku) {
        try {
            return ubsRepositoryJPA.buscaUbsComEstoque(sku);
        } catch (Exception e) {
            log.error("Erro ao buscar lista de UBS por SKU", e);
            throw new ErroInternoException("Erro ao buscar UBS por SKU: " + e.getMessage());
        }
    }

}
