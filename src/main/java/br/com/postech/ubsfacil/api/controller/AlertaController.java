package br.com.postech.ubsfacil.api.controller;

import br.com.postech.ubsfacil.api.dto.alertas.AlertasResponseDto;
import br.com.postech.ubsfacil.api.mapper.AlertaMapper;
import br.com.postech.ubsfacil.domain.Alerta;
import br.com.postech.ubsfacil.gateway.ports.alertas.AlertaServicePort;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alertas")
@Tag(name = "Alertas dos estoques das Unidades Básicas de Saúde", description = "Monitoramento de Alertas dos estoque")
public class AlertaController {

    private final AlertaServicePort servicePort;

    public AlertaController(AlertaServicePort servicePort) {
        this.servicePort = servicePort;
    }


    @GetMapping("/buscar-alertas")
    public ResponseEntity<List<AlertasResponseDto>> buscarAlertas(
            @Parameter(description = "CNES da Unidade Básica de Saúde", example = "1234567")
            @RequestParam(value = "cnes", required = false) String cnes,
            @Parameter(description = "Tipo de Alerta", example = "ESTOQUE_BAIXO, ESGOTADO, VENCIDO, VENCIMENTO_PROXIMO")
            @RequestParam(value = "tipoAlerta", required = false) String tipoAlerta) {

        List<Alerta> alertas = obterAlertasPorParametros(cnes, tipoAlerta);
        List<AlertasResponseDto> dto = AlertaMapper.INSTANCE.domainToResponse(alertas);

        return ResponseEntity.ok(dto);
    }

    private List<Alerta> obterAlertasPorParametros(String cnes, String tipoAlerta) {
        if (tipoAlerta == null && (cnes == null || cnes.isEmpty())) {
            return servicePort.buscarTodosAlertas();
        } else if (tipoAlerta != null && cnes == null) {
            return servicePort.buscarAlertasPorTipo(tipoAlerta);
        } else if (tipoAlerta != null && cnes != null) {
            return servicePort.buscarAlertasPorTipoAndCnes(cnes, tipoAlerta);
        } else {
            return servicePort.buscarTodosAlertasUbs(cnes);
        }
}
}
