package br.com.postech.ubsfacil.gateway.ports.alertas;

import br.com.postech.ubsfacil.api.dto.estoque.TipoAlerta;
import br.com.postech.ubsfacil.domain.Alerta;

import java.util.List;

public interface AlertaRepositoryPort {

    void salvar(Alerta alerta);

    List<Alerta> findAllByUbsCnes(String cnes);

    List<Alerta> findAllByUbsCnesAndTipoAlerta(String cnes, TipoAlerta tipoAlerta);

    List<Alerta> findAll();

    List<Alerta> findAllByTipoAlerta(TipoAlerta tipoAlerta);
}
