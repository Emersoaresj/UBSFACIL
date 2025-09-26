package br.com.postech.ubsfacil.api.controller;

import br.com.postech.ubsfacil.api.dto.UbsDisponivelResponse;
import br.com.postech.ubsfacil.gateway.ports.publica.ConsultaPublicaServicePort;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/consulta")
@Tag(name = "Consulta pública de Insumos", description = "Consulta Pública de Insumos próximos do CEP")
public class ConsultaPublicaController {

    private final ConsultaPublicaServicePort consultaPublicaServicePort;

    public ConsultaPublicaController(ConsultaPublicaServicePort consultaPublicaServicePort) {
        this.consultaPublicaServicePort = consultaPublicaServicePort;
    }

    @GetMapping
    public ResponseEntity<List<UbsDisponivelResponse>> consultaRemedioProximo(
            @RequestParam(value = "cepPaciente") String cepPaciente,
            @RequestParam(value = "nomeRemedio") String nomeRemedio,
            @RequestParam(defaultValue = "5", required = false) Double raioKm) {
        List<UbsDisponivelResponse> resultado = consultaPublicaServicePort.consultaRemedioProximo(cepPaciente, nomeRemedio, raioKm);
        return ResponseEntity.status(HttpStatus.OK).body(resultado);
    }
}
