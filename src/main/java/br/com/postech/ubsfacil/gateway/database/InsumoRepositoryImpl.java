package br.com.postech.ubsfacil.gateway.database;

import br.com.postech.ubsfacil.api.mapper.InsumoMapper;
import br.com.postech.ubsfacil.domain.Insumo;
import br.com.postech.ubsfacil.domain.exceptions.ErroInternoException;
import br.com.postech.ubsfacil.gateway.database.entity.InsumoEntity;
import br.com.postech.ubsfacil.gateway.database.repository.InsumoRepositoryJPA;
import br.com.postech.ubsfacil.gateway.ports.InsumoRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
public class InsumoRepositoryImpl implements InsumoRepositoryPort {

    private final InsumoRepositoryJPA insumoRepositoryJPA;

    public InsumoRepositoryImpl(InsumoRepositoryJPA insumoRepositoryJPA) {
        this.insumoRepositoryJPA = insumoRepositoryJPA;
    }


    @Override
    public Insumo cadastrarInsumo(Insumo insumo) {
        try {
            InsumoEntity insumoEntity = InsumoMapper.INSTANCE.domainToEntity(insumo);
            insumoRepositoryJPA.save(insumoEntity);
            return InsumoMapper.INSTANCE.entityToDomain(insumoEntity);
        } catch (Exception e) {
            log.error("Erro ao cadastrar Insumo", e);
            throw new ErroInternoException("Erro ao cadastrar Insumo: " + e.getMessage());
        }
    }

    @Override
    public Optional<Insumo> findBySku(String sku) {
        try {
            return insumoRepositoryJPA.findBySku(sku)
                    .map(InsumoMapper.INSTANCE::entityToDomain);
        } catch (Exception e) {
            log.error("Erro ao buscar Insumo", e);
            throw new ErroInternoException("Erro ao buscar Insumo: " + e.getMessage());
        }
    }

}
