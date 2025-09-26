package br.com.postech.ubsfacil.gateway.database;

import br.com.postech.ubsfacil.api.mapper.EstoqueMapper;
import br.com.postech.ubsfacil.domain.Estoque;
import br.com.postech.ubsfacil.domain.exceptions.ErroInternoException;
import br.com.postech.ubsfacil.gateway.database.entity.EstoqueEntity;
import br.com.postech.ubsfacil.gateway.database.repository.EstoqueRepositoryJPA;
import br.com.postech.ubsfacil.gateway.ports.estoque.EstoqueRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class EstoqueRepositoryImpl implements EstoqueRepositoryPort {

    private final EstoqueRepositoryJPA estoqueRepositoryJPA;

    public EstoqueRepositoryImpl(EstoqueRepositoryJPA estoqueRepositoryJPA) {
        this.estoqueRepositoryJPA = estoqueRepositoryJPA;
    }

    @Transactional
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
    public Optional<Estoque> findByCnesAndInsumoBarcode(String cnes, String barcode) {
        try {
            EstoqueEntity entities = estoqueRepositoryJPA.findByUbsCnesAndInsumoBarcode(cnes, barcode);
            return Optional.ofNullable(EstoqueMapper.INSTANCE.entityToDomain(entities));
        } catch (Exception e) {
            log.error("Erro ao buscar Estoques por CNES e Barcode", e);
            throw new ErroInternoException("Erro ao buscar Estoques por CNES e Barcode: " + e.getMessage());
        }
    }

    @Override
    public List<Estoque> findAll() {
        try {
            List<EstoqueEntity> entities = estoqueRepositoryJPA.findAll();
            return EstoqueMapper.INSTANCE.listEntityToDomain(entities);
        } catch (Exception e) {
            log.error("Erro ao buscar todos os Estoques", e);
            throw new ErroInternoException("Erro ao buscar todos os Estoques: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public Estoque atualizarEstoque(Estoque estoque) {
        try {
            EstoqueEntity estoqueEntity = EstoqueMapper.INSTANCE.domainToEntity(estoque);
            estoqueRepositoryJPA.save(estoqueEntity);
            return EstoqueMapper.INSTANCE.entityToDomain(estoqueEntity);
        } catch (Exception e) {
            log.error("Erro ao atualizar Estoque", e);
            throw new ErroInternoException("Erro ao atualizar Estoque: " + e.getMessage());
        }
    }

    @Override
    public void deletarTodosPorInsumoSku(String insumoSku) {
        try {
            estoqueRepositoryJPA.deleteAllByInsumoBarcode(insumoSku);
        } catch (Exception e) {
            log.error("Erro ao deletar todos os estoques pelo SKU {}", insumoSku, e);
            throw new ErroInternoException("Erro ao deletar estoques pelo SKU: " + e.getMessage());
        }
    }



    @Transactional
    @Override
    public void deletarEstoque(Integer idEstoque) {
        try {
            estoqueRepositoryJPA.deleteById(idEstoque);
        } catch (Exception e) {
            log.error("Erro ao deletar Estoque", e);
            throw new ErroInternoException("Erro ao deletar Estoque: " + e.getMessage());
        }
    }

    @Override
    public List<Estoque> findByUbsCnes(String cnes) {
        try {
            List<EstoqueEntity> entities = estoqueRepositoryJPA.findAllByUbsCnes(cnes);
            return EstoqueMapper.INSTANCE.listEntityToDomain(entities);
        } catch (Exception e) {
            log.error("Erro ao buscar todos os Estoques", e);
            throw new ErroInternoException("Erro ao buscar todos os Estoques: " + e.getMessage());
        }
    }

    @Override
    public List<Estoque> buscaPorInsumoBarcode(String sku) {
        try {
            List<EstoqueEntity> entities = estoqueRepositoryJPA.findAllByInsumoBarcode(sku);
            return EstoqueMapper.INSTANCE.listEntityToDomain(entities);
        } catch (Exception e) {
            log.error("Erro ao buscar todos os Estoques", e);
            throw new ErroInternoException("Erro ao buscar todos os Estoques: " + e.getMessage());
        }
    }
}
