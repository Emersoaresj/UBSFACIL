package br.com.postech.ubsfacil.gateway.database;

import br.com.postech.ubsfacil.api.dto.estoque.TipoAlerta;
import br.com.postech.ubsfacil.api.mapper.AlertaMapper;
import br.com.postech.ubsfacil.domain.Alerta;
import br.com.postech.ubsfacil.domain.exceptions.ErroInternoException;
import br.com.postech.ubsfacil.gateway.database.entity.AlertaEntity;
import br.com.postech.ubsfacil.gateway.database.repository.AlertaRepositoryJpa;
import br.com.postech.ubsfacil.gateway.ports.AlertaRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class AlertaRepositoryImpl implements AlertaRepositoryPort {

    private final AlertaRepositoryJpa alertaRepositoryJpa;

    public AlertaRepositoryImpl(AlertaRepositoryJpa alertaRepositoryJpa) {
        this.alertaRepositoryJpa = alertaRepositoryJpa;
    }


    @Override
    public void salvar(Alerta alerta) {
        try {
            AlertaEntity alertaEntity = AlertaMapper.INSTANCE.domainToEntity(alerta);
            alertaRepositoryJpa.save(alertaEntity);
        } catch (Exception e) {
            log.error("Erro ao cadastrar Estoque", e);
            throw new ErroInternoException("Erro ao cadastrar Estoque: " + e.getMessage());
        }
    }

    @Override
    public List<Alerta> findAllByUbsCnes(String cnes) {
        try {
            List<AlertaEntity> alertaEntities = alertaRepositoryJpa.findAllByUbsCnes(cnes);
            return AlertaMapper.INSTANCE.entityToDomain(alertaEntities);
        } catch (Exception e) {
            log.error("Erro ao buscar alertas da UBS", e);
            throw new ErroInternoException("Erro ao buscar alertas da UBS: " + e.getMessage());
        }
    }

    @Override
    public List<Alerta> findAllByUbsCnesAndTipoAlerta(String cnes, TipoAlerta tipoAlerta) {
        try {
            List<AlertaEntity> alertaEntities = alertaRepositoryJpa.findAllByUbsCnesAndTipoAlerta(cnes, tipoAlerta);
            return AlertaMapper.INSTANCE.entityToDomain(alertaEntities);
        } catch (Exception e) {
            log.error("Erro ao buscar alertas da UBS por tipo", e);
            throw new ErroInternoException("Erro ao buscar alertas da UBS por tipo: " + e.getMessage());
        }
    }
}
