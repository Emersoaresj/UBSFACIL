package br.com.postech.ubsfacil.api.controller;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.api.dto.insumos.InsumoRequestDto;
import br.com.postech.ubsfacil.api.mapper.InsumoMapper;
import br.com.postech.ubsfacil.domain.Insumo;
import br.com.postech.ubsfacil.gateway.ports.InsumoServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/insumos")
@Tag(name = "Insumos da Unidade Básica de Saúde", description = "Gerenciamento de Insumos")
public class InsumoController {

    private final InsumoServicePort servicePort;

    public InsumoController(InsumoServicePort servicePort) {
        this.servicePort = servicePort;
    }

    @Operation(summary = "Cadastrar um novo insumo",
            description = "Endpoint para cadastrar um novo insumo na Unidade Básica de Saúde")
    @PostMapping("/cadastro")
    public ResponseEntity<ResponseDto> cadastrarInsumo(@Valid @RequestBody InsumoRequestDto insumosRequestDto) {
        Insumo insumo = InsumoMapper.INSTANCE.requestToDomain(insumosRequestDto);
        ResponseDto cadastro = servicePort.cadastrarInsumo(insumo);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastro);
    }
}
