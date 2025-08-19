package br.com.postech.ubsfacil.gateway.database.repository;

import br.com.postech.ubsfacil.gateway.database.entity.UbsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UbsRepositoryJPA extends JpaRepository<UbsEntity, Integer> {

    Optional<UbsEntity> findByCnes(String cnes);
}
