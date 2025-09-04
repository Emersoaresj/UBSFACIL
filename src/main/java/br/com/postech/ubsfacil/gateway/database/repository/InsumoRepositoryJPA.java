package br.com.postech.ubsfacil.gateway.database.repository;

import br.com.postech.ubsfacil.gateway.database.entity.InsumoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InsumoRepositoryJPA extends JpaRepository<InsumoEntity, Integer> {

    Optional<InsumoEntity> findBySku(String sku);

    List<InsumoEntity> findByTipo(String tipo);
}
