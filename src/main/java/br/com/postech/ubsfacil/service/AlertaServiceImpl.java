package br.com.postech.ubsfacil.service;

import br.com.postech.ubsfacil.api.dto.estoque.TipoAlerta;
import br.com.postech.ubsfacil.domain.Alerta;
import br.com.postech.ubsfacil.domain.Estoque;
import br.com.postech.ubsfacil.domain.Ubs;
import br.com.postech.ubsfacil.domain.exceptions.ErroNegocioException;
import br.com.postech.ubsfacil.domain.exceptions.alertas.TipoAlertaException;
import br.com.postech.ubsfacil.domain.exceptions.ubs.UbsNotFoundException;
import br.com.postech.ubsfacil.gateway.ports.alertas.AlertaRepositoryPort;
import br.com.postech.ubsfacil.gateway.ports.alertas.AlertaServicePort;
import br.com.postech.ubsfacil.gateway.ports.ubs.UbsRepositoryPort;
import br.com.postech.ubsfacil.utils.ConstantUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlertaServiceImpl implements AlertaServicePort {

    private final AlertaRepositoryPort alertaRepositoryPort;
    private final UbsRepositoryPort ubsRepositoryPort;

    public AlertaServiceImpl(AlertaRepositoryPort alertaRepositoryPort, UbsRepositoryPort ubsRepositoryPort) {
        this.alertaRepositoryPort = alertaRepositoryPort;
        this.ubsRepositoryPort = ubsRepositoryPort;
    }


    @Override
    public void verificarEAvisar(Estoque estoque) {
        if (estoque.isEsgotado()) {
            gerarAlerta(estoque, TipoAlerta.ESGOTADO, "Estoque esgotado");
        } else if (estoque.isEstoqueBaixo()) {
            gerarAlerta(estoque, TipoAlerta.ESTOQUE_BAIXO, "Estoque abaixo do mínimo");
        }

        if (estoque.getInsumoDataValidade() != null) {
            if (estoque.isVencido()) {
                gerarAlerta(estoque, TipoAlerta.VENCIDO, "Validade vencida");
            } else if (estoque.isVencimentoProximo()) {
                gerarAlerta(estoque, TipoAlerta.VENCIMENTO_PROXIMO, "Validade próxima do vencimento");
            }
        }
    }

    @Override
    public List<Alerta> buscarTodosAlertasUbs(String cnes) {
        try {
            Ubs.validarCnes(cnes);
            if (ubsRepositoryPort.findByCnes(cnes).isEmpty()) {
                throw new UbsNotFoundException(ConstantUtils.UBS_NAO_ENCONTRADA);
            }
            return alertaRepositoryPort.findAllByUbsCnes(cnes);
        } catch (UbsNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ErroNegocioException("Erro ao buscar alertas da UBS: " + e.getMessage());
        }
    }

    @Override
    public List<Alerta> buscarAlertasPorTipoAndCnes(String cnes, String tipoAlerta) {
        try {
            Ubs.validarCnes(cnes);
            boolean isTipoAlertaOk = Alerta.validarTipoAlerta(tipoAlerta);
            if (!isTipoAlertaOk) {
                throw new TipoAlertaException("Tipo de alerta inválido. Valores aceitos: ESTOQUE_BAIXO, ESGOTADO, VENCIDO, VENCIMENTO_PROXIMO");
            } else if (ubsRepositoryPort.findByCnes(cnes).isEmpty()) {
                throw new UbsNotFoundException(ConstantUtils.UBS_NAO_ENCONTRADA);
            }
            TipoAlerta tipoAlertaEnum = TipoAlerta.valueOf(tipoAlerta.toUpperCase());
            return alertaRepositoryPort.findAllByUbsCnesAndTipoAlerta(cnes, tipoAlertaEnum);
        } catch (TipoAlertaException | UbsNotFoundException | ErroNegocioException e) {
            throw e;
        } catch (Exception e) {
            throw new ErroNegocioException("Erro ao buscar alertas da UBS: " + e.getMessage());
        }
    }

    @Override
    public List<Alerta> buscarTodosAlertas() {
        try {
            return alertaRepositoryPort.findAll();
        } catch (Exception e) {
            throw new ErroNegocioException("Erro ao buscar todos os alertas: " + e.getMessage());
        }
    }

    @Override
    public List<Alerta> buscarAlertasPorTipo(String tipoAlerta) {
        try {
            boolean isTipoAlertaOk = Alerta.validarTipoAlerta(tipoAlerta);
            if (!isTipoAlertaOk) {
                throw new TipoAlertaException("Tipo de alerta inválido. Valores aceitos: ESTOQUE_BAIXO, ESGOTADO, VENCIDO, VENCIMENTO_PROXIMO");
            }
            TipoAlerta tipoAlertaEnum = TipoAlerta.valueOf(tipoAlerta.toUpperCase());
            return alertaRepositoryPort.findAllByTipoAlerta(tipoAlertaEnum);
        } catch (TipoAlertaException e) {
            throw e;
        } catch (Exception e) {
            throw new ErroNegocioException("Erro ao buscar alertas por tipo: " + e.getMessage());
        }
    }

    private void gerarAlerta(Estoque estoque, TipoAlerta tipo, String detalhe) {
        Alerta alerta = new Alerta(
                null,
                estoque.getUbsCnes(),
                estoque.getInsumoBarcode(),
                tipo,
                detalhe,
                LocalDate.now()
        );

        alertaRepositoryPort.salvar(alerta);
    }
}

