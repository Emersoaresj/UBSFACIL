package br.com.postech.ubsfacil.gateway.ports;

import br.com.postech.ubsfacil.domain.Insumo;

import java.util.List;
import java.util.Optional;

public interface InsumoRepositoryPort {

    Insumo cadastrarInsumo(Insumo insumo);

    Optional<Insumo> findBySku(String sku);

    List<Insumo> findByTipo(String tipo);

    List<Insumo> buscarTodos();
}
