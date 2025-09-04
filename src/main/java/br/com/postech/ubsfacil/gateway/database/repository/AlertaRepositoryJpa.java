package br.com.postech.ubsfacil.gateway.database.repository;

import br.com.postech.ubsfacil.gateway.database.entity.AlertaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaRepositoryJpa extends JpaRepository<AlertaEntity, Integer> {
}
