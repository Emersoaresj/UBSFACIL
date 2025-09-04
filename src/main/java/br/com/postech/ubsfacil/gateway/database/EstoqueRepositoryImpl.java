package br.com.postech.ubsfacil.gateway.database;

import br.com.postech.ubsfacil.api.mapper.EstoqueMapper;
import br.com.postech.ubsfacil.domain.Estoque;
import br.com.postech.ubsfacil.domain.exceptions.ErroInternoException;
import br.com.postech.ubsfacil.gateway.database.entity.EstoqueEntity;
import br.com.postech.ubsfacil.gateway.database.repository.EstoqueRepositoryJPA;
import br.com.postech.ubsfacil.gateway.ports.EstoqueRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
public class EstoqueRepositoryImpl implements EstoqueRepositoryPort {

    private final EstoqueRepositoryJPA estoqueRepositoryJPA;

    public EstoqueRepositoryImpl(EstoqueRepositoryJPA estoqueRepositoryJPA) {
        this.estoqueRepositoryJPA = estoqueRepositoryJPA;
    }

    @Override
    public Estoque cadastrarEstoque(Estoque estoque) {
        try {
            EstoqueEntity estoqueEntity = EstoqueMapper.INSTANCE.domainToEntity(estoque);
            estoqueRepositoryJPA.save(estoqueEntity);
            return EstoqueMapper.INSTANCE.entityToDomain(estoqueEntity);
        } catch (Exception e) {
            log.error("Erro ao cadastrar Estoque", e);
            throw new ErroInternoException("Erro ao cadastrar Estoque: " + e.getMessage());
        }
    }

    @Override
    public Optional<Estoque> findByIdEstoque(Integer id) {
        try {
            return estoqueRepositoryJPA.findById(id)
                    .map(EstoqueMapper.INSTANCE::entityToDomain);
        } catch (Exception e) {
            log.error("Erro ao buscar Estoque por ID", e);
            throw new ErroInternoException("Erro ao buscar Estoque por ID: " + e.getMessage());
        }
    }

    @Override
    public Optional<Estoque> findByInsumoSku(String insumoSku) {
        try {
            return estoqueRepositoryJPA.findByInsumoSku(insumoSku)
                    .map(EstoqueMapper.INSTANCE::entityToDomain);
        } catch (Exception e) {
            log.error("Erro ao buscar SKU", e);
            throw new ErroInternoException("Erro ao buscar SKU: " + e.getMessage());
        }
    }
}
