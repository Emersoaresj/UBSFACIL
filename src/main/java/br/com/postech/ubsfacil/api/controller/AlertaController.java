package br.com.postech.ubsfacil.api.controller;

import br.com.postech.ubsfacil.api.dto.alertas.AlertasResponseDto;
import br.com.postech.ubsfacil.api.mapper.AlertaMapper;
import br.com.postech.ubsfacil.domain.Alerta;
import br.com.postech.ubsfacil.gateway.ports.AlertaServicePort;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
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
            @RequestParam(value = "cnes") String cnes,
            @Parameter(description = "Tipo de Alerta", example = "ESTOQUE_BAIXO, ESGOTADO, VENCIDO, VENCIMENTO_PROXIMO")
            @RequestParam(value = "tipoAlerta", required = false) String tipoAlerta) {

        List<Alerta> alertas;

        if (tipoAlerta != null) {
            alertas = servicePort.buscarAlertasPorTipo(cnes, tipoAlerta);
            List<AlertasResponseDto> dto = AlertaMapper.INSTANCE.domainToResponse(alertas);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } else {
            alertas = servicePort.buscarTodosAlertasUbs(cnes);
            List<AlertasResponseDto> dto = AlertaMapper.INSTANCE.domainToResponse(alertas);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
    }

}
