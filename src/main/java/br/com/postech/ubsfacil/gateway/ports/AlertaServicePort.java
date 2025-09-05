package br.com.postech.ubsfacil.gateway.ports;

import br.com.postech.ubsfacil.domain.Alerta;
import br.com.postech.ubsfacil.domain.Estoque;

import java.util.List;

public interface AlertaServicePort {

    void verificarEAvisar(Estoque estoque);

    List<Alerta> buscarTodosAlertasUbs(String cnes);

    List<Alerta> buscarAlertasPorTipo(String cnes, String tipoAlerta);
}
