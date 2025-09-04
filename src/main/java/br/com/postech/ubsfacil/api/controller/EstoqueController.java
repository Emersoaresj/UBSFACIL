package br.com.postech.ubsfacil.api.controller;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.api.dto.estoque.EstoqueRequestDto;
import br.com.postech.ubsfacil.api.dto.estoque.EstoqueResponseDto;
import br.com.postech.ubsfacil.api.dto.insumos.InsumoResponseDto;
import br.com.postech.ubsfacil.api.mapper.EstoqueMapper;
import br.com.postech.ubsfacil.api.mapper.InsumoMapper;
import br.com.postech.ubsfacil.domain.Estoque;
import br.com.postech.ubsfacil.domain.Insumo;
import br.com.postech.ubsfacil.gateway.ports.EstoqueServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estoque")
@Tag(name = "Estoques das Unidades Básicas de Saúde", description = "Gerenciamento de Estoque")
public class EstoqueController {

    private final EstoqueServicePort servicePort;

    public EstoqueController(EstoqueServicePort servicePort) {
        this.servicePort = servicePort;
    }


    @Operation(summary = "Cadastrar um novo estoque",
            description = "Endpoint para cadastrar um novo estoque na Unidade Básica de Saúde")
    @PostMapping("/cadastro")
    public ResponseEntity<ResponseDto> cadastrarEstoque(@Valid @RequestBody EstoqueRequestDto estoqueRequestDto) {
        Estoque estoque = EstoqueMapper.INSTANCE.requestToDomain(estoqueRequestDto);
        ResponseDto cadastro = servicePort.cadastrarEstoque(estoque);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastro);
    }

    @GetMapping("/{idEstoque}")
    @Operation(summary = "Buscar Estoque por ID", description = "Endpoint para buscar estoque pelo ID fornecido.")
    public ResponseEntity<EstoqueResponseDto> buscarEstoquePorId(
            @Parameter(description = "ID do estoque", example = "1")
            @PathVariable("idEstoque") Integer idEstoque) {
        Estoque response = servicePort.buscarEstoquePorId(idEstoque);
        EstoqueResponseDto dto = EstoqueMapper.INSTANCE.domainToResponse(response);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping()
    @Operation(summary = "Listar estoques",
            description = "Endpoint para listar todos os estoques ou filtrar por uma UBS e SKU de insumo.")
    public ResponseEntity<List<EstoqueResponseDto>> buscarEstoques(
            @Parameter(description = "CNES da Unidade Básica de Saúde", example = "1234567")
            @RequestParam(value = "cnes", required = false) String cnes,
            @Parameter(description = "SKU do insumo", example = "INS12345")
            @RequestParam(value = "sku", required = false) String sku){

        List<Estoque> response;

        if (cnes != null && sku != null) {
            response = servicePort.buscarPorFiltro(cnes, sku);
        } else {
            response = servicePort.buscarTodos();
        }
        List<EstoqueResponseDto> dto = EstoqueMapper.INSTANCE.listDomainToResponse(response);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
