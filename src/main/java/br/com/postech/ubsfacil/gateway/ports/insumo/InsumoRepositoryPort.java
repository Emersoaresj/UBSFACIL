package br.com.postech.ubsfacil.gateway.ports.insumo;

import br.com.postech.ubsfacil.domain.Insumo;

import java.util.List;
import java.util.Optional;

public interface InsumoRepositoryPort {

    Insumo cadastrarInsumo(Insumo insumo);

    Optional<Insumo> findByBarcode(String barcode);

    List<Insumo> findByTipo(String tipo);

    List<Insumo> buscarTodos();

    Insumo atualizarInsumo(Insumo insumo);

    void deletarInsumo(String barcode);
}
