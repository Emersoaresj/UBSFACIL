package br.com.postech.ubsfacil.gateway.database.repository;

import br.com.postech.ubsfacil.gateway.database.entity.EstoqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstoqueRepositoryJPA extends JpaRepository<EstoqueEntity, Integer> {

    void deleteAllByInsumoBarcode(String barcode);

    EstoqueEntity findByUbsCnesAndInsumoBarcode(String ubsCnes, String barcode);

    List<EstoqueEntity> findAllByUbsCnes(String cnes);

    List<EstoqueEntity> findAllByInsumoBarcode(String barcode);
}
