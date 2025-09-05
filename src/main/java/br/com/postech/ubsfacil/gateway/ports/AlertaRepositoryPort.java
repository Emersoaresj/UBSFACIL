package br.com.postech.ubsfacil.gateway.ports;

import br.com.postech.ubsfacil.api.dto.estoque.TipoAlerta;
import br.com.postech.ubsfacil.domain.Alerta;

import java.util.List;

public interface AlertaRepositoryPort {

    void salvar(Alerta alerta);

    List<Alerta> findAllByUbsCnes(String cnes);

    List<Alerta> findAllByUbsCnesAndTipoAlerta(String cnes, TipoAlerta tipoAlerta);
}
