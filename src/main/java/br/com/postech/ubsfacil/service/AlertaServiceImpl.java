package br.com.postech.ubsfacil.service;

import br.com.postech.ubsfacil.api.dto.estoque.TipoAlerta;
import br.com.postech.ubsfacil.domain.Alerta;
import br.com.postech.ubsfacil.domain.Estoque;
import br.com.postech.ubsfacil.gateway.ports.AlertaRepositoryPort;
import br.com.postech.ubsfacil.gateway.ports.AlertaServicePort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AlertaServiceImpl implements AlertaServicePort {

    private final AlertaRepositoryPort alertaRepositoryPort;

    public AlertaServiceImpl(AlertaRepositoryPort alertaRepositoryPort) {
        this.alertaRepositoryPort = alertaRepositoryPort;
    }


    @Override
    public void verificarEAvisar(Estoque estoque) {
        if (estoque.isEsgotado()) {
            gerarAlerta(estoque, TipoAlerta.ESGOTADO, "Estoque esgotado");
        } else if (estoque.isEstoqueBaixo()) {
            gerarAlerta(estoque, TipoAlerta.ESTOQUE_BAIXO, "Estoque abaixo do mínimo");
        }

        if (estoque.getValidade() != null) {
            if (estoque.isVencido()) {
                gerarAlerta(estoque, TipoAlerta.VENCIDO, "Validade vencida");
            } else if (estoque.isVencimentoProximo()) {
                gerarAlerta(estoque, TipoAlerta.VENCIMENTO_PROXIMO, "Validade próxima do vencimento");
            }
        }
    }

    private void gerarAlerta(Estoque estoque, TipoAlerta tipo, String detalhe) {
        Alerta alerta = new Alerta(
                null,
                estoque.getUbsCnes(),
                estoque.getInsumoSku(),
                tipo,
                detalhe,
                LocalDate.now()
        );

        alertaRepositoryPort.salvar(alerta);
    }
}

