package br.com.postech.ubsfacil.gateway.database.repository;

import br.com.postech.ubsfacil.api.dto.estoque.TipoAlerta;
import br.com.postech.ubsfacil.gateway.database.entity.AlertaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaRepositoryJpa extends JpaRepository<AlertaEntity, Integer> {

    List<AlertaEntity> findAllByUbsCnes(String cnes);

    List<AlertaEntity> findAllByUbsCnesAndTipoAlerta(String cnes, TipoAlerta tipoAlerta);
}
