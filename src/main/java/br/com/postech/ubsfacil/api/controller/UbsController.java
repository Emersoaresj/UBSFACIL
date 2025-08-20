package br.com.postech.ubsfacil.api.controller;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.api.dto.ubs.UbsRequestDto;
import br.com.postech.ubsfacil.api.dto.ubs.UbsResponseDto;
import br.com.postech.ubsfacil.gateway.ports.UbsServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ubs")
@Tag(name = "Unidade Básica de Saúde", description = "Gerenciamento de UBS")
public class UbsController {

    @Autowired
    private UbsServicePort service;

    @Operation(summary = "Cadastrar uma nova UBS",
            description = "Endpoint para cadastrar uma nova Unidade Básica de Saúde")
    @PostMapping("/cadastro")
    public ResponseEntity<ResponseDto> cadastrarUbs(@Valid @RequestBody UbsRequestDto ubsRequestDto) {
        ResponseDto cadastro = service.cadastraUbs(ubsRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastro);
    }

    @GetMapping("/cnes/{cnes}")
    @Operation(summary = "Buscar UBS por CNES",
            description = "Endpoint para buscar uma Unidade Básica de Saúde pelo CNES")
    public ResponseEntity<UbsResponseDto> buscarUbsPorCnes(
            @Parameter(description = "CNES da UBS (apenas números)", example = "1234567")
            @PathVariable("cnes") String cnes) {
        UbsResponseDto response = service.buscarUbsPorCnes(cnes);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    @Operation(summary = "Buscar UBS por Cidade e UF",
            description = "Endpoint para buscar uma Unidade Básica de Saúde por cidade e UF")
    public ResponseEntity<List<UbsResponseDto>> buscarPorCidadeUf(
            @Parameter(description = "Cidade da UBS", example = "São Paulo")
            @RequestParam(value = "cidade", required = false) String cidade,
            @Parameter(description = "UF da UBS (2 letras maiúsculas)", example = "SP")
            @RequestParam(value = "uf", required = false) String uf) {
        List<UbsResponseDto> response = service.buscarPorCidadeUf(cidade, uf);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
