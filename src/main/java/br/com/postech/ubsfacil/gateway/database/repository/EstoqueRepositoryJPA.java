package br.com.postech.ubsfacil.gateway.database.repository;

import br.com.postech.ubsfacil.gateway.database.entity.EstoqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueRepositoryJPA extends JpaRepository<EstoqueEntity, Integer> {

    Optional<EstoqueEntity> findByInsumoSku(String insumoSku);
}
