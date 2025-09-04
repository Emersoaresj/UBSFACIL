package br.com.postech.ubsfacil.api.controller;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.api.dto.insumos.InsumoRequestDto;
import br.com.postech.ubsfacil.api.dto.insumos.InsumoResponseDto;
import br.com.postech.ubsfacil.api.dto.ubs.UbsResponseDto;
import br.com.postech.ubsfacil.api.mapper.InsumoMapper;
import br.com.postech.ubsfacil.domain.Insumo;
import br.com.postech.ubsfacil.gateway.ports.InsumoServicePort;
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

    @GetMapping("/sku/{sku}")
    @Operation(summary = "Buscar Insumo por SKU", description = "Endpoint para buscar insumo pelo SKU fornecido.")
    public ResponseEntity<InsumoResponseDto> buscarInsumoPorSku(
            @Parameter(description = "SKU do insumo", example = "DIP500")
            @PathVariable("sku") String sku) {
        Insumo response = servicePort.buscarInsumoPorSku(sku);
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


}
