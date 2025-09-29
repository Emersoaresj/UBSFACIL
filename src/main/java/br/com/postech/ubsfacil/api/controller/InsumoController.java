package br.com.postech.ubsfacil.api.controller;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.api.dto.insumos.InsumoRequestDto;
import br.com.postech.ubsfacil.api.dto.insumos.InsumoUpdateDto;
import br.com.postech.ubsfacil.api.dto.insumos.InsumoResponseDto;
import br.com.postech.ubsfacil.api.mapper.InsumoMapper;
import br.com.postech.ubsfacil.domain.Insumo;
import br.com.postech.ubsfacil.gateway.ports.insumo.InsumoServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/barcode/{barcode}")
    @Operation(summary = "Buscar Insumo por barcode", description = "Endpoint para buscar insumo pelo barcode fornecido.")
    public ResponseEntity<InsumoResponseDto> buscarInsumoPorBarcode(
            @Parameter(description = "barcode do insumo", example = "123456789012")
            @PathVariable("barcode") String barcode) {
        Insumo response = servicePort.buscarInsumoPorBarcode(barcode);
        InsumoResponseDto dto = InsumoMapper.INSTANCE.domainToResponse(response);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping()
    @Operation(summary = "Listar insumos",
            description = "Endpoint para listar todos os insumos ou filtrar pelo tipo.")
    public ResponseEntity<List<InsumoResponseDto>> buscarPorTipo(
            @Parameter(description = "Tipo do Insumo", example = "Medicamento")
            @RequestParam(value = "tipo", required = false) String tipo){

        List<Insumo> response;

        if (tipo != null) {
            response = servicePort.buscarPorTipo(tipo);
        } else {
            response = servicePort.buscarTodos();
        }
        List<InsumoResponseDto> dto = InsumoMapper.INSTANCE.listDomainToResponse(response);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PutMapping("/atualizar/{barcode}")
    @Operation(summary = "Atualizar Insumo",
            description = "Endpoint para atualizar os dados de um insumo existente pelo barcode.")
    public ResponseEntity<InsumoResponseDto> atualizarInsumo(
            @Parameter(description = "barcode do insumo", example = "123456789012")
            @PathVariable("barcode") String barcode,
            @Valid @RequestBody InsumoUpdateDto requestDto) {

        Insumo insumo = InsumoMapper.INSTANCE.updateToDomain(requestDto);
        insumo.setBarcode(barcode);
        Insumo atualizado = servicePort.atualizarInsumo(insumo);

        InsumoResponseDto dto = InsumoMapper.INSTANCE.domainToResponse(atualizado);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/deletar/{barcode}")
    @Operation(summary = "Excluir insumo",
            description = "Endpoint para excluir (inativar) um insumo pelo barcode.")
    public ResponseEntity<Void> deletarInsumo(
            @Parameter(description = "barcode do insumo", example = "123456789012")
            @PathVariable("barcode") String barcode) {

        servicePort.deletarInsumo(barcode);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
