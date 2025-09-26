package br.com.postech.ubsfacil.api.controller;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.api.dto.estoque.*;
import br.com.postech.ubsfacil.api.mapper.EstoqueMapper;
import br.com.postech.ubsfacil.domain.Estoque;
import br.com.postech.ubsfacil.gateway.ports.estoque.EstoqueServicePort;
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
            description = "Endpoint para listar todos os estoques ou filtrar por uma UBS e Barcode de insumo.")
    public ResponseEntity<List<EstoqueResponseDto>> buscarEstoques(
            @Parameter(description = "CNES da Unidade Básica de Saúde", example = "1234567")
            @RequestParam(value = "cnes", required = false) String cnes,
            @Parameter(description = "barcode do insumo", example = "123456789012")
            @RequestParam(value = "barcode", required = false) String barcode){

        List<Estoque> responseList;
        Estoque responseFiltro;

        if (cnes != null && barcode != null) {
            responseFiltro = servicePort.buscarPorFiltro(cnes, barcode).orElse(null);
            EstoqueResponseDto filtro = EstoqueMapper.INSTANCE.domainToResponse(responseFiltro);
            return ResponseEntity.status(HttpStatus.OK).body(List.of(filtro));

        } else if (cnes != null) {
            responseList = servicePort.buscarPorCnes(cnes);
            return ResponseEntity.ok(EstoqueMapper.INSTANCE.listDomainToResponse(responseList));

        } else if (barcode != null) {
            responseList = servicePort.buscarPorBarcode(barcode);
            return ResponseEntity.ok(EstoqueMapper.INSTANCE.listDomainToResponse(responseList));

        } else {
            responseList = servicePort.buscarTodos();
            List<EstoqueResponseDto> dto = EstoqueMapper.INSTANCE.listDomainToResponse(responseList);

            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
    }

    @PutMapping("/atualizar/{idEstoque}")
    @Operation(summary = "Atualizar Estoque",
            description = "Endpoint para atualizar os dados de um estoque existente pelo ID.")
    public ResponseEntity<ResponseDto> atualizarEstoque(
            @Parameter(description = "ID do estoque a ser atualizado", example = "1", required = true)
            @PathVariable ("idEstoque") Integer idEstoque,
            @Parameter(description = "Dados do estoque a ser atualizado", required = true)
            @Valid @RequestBody EstoqueRequestUpdateDto requestDto) {

        Estoque estoque = EstoqueMapper.INSTANCE.requestUpdateToDomain(requestDto);

        ResponseDto atualizado = servicePort.atualizarEstoque(idEstoque, estoque);

        return ResponseEntity.status(HttpStatus.OK).body(atualizado);
    }

    @DeleteMapping("/deletar/{idEstoque}")
    @Operation(summary = "Excluir estoque",
            description = "Endpoint para excluir um estoque pelo IdEstoque.")
    public ResponseEntity<Void> deletarEstoque(
            @Parameter(description = "ID do estoque", example = "1")
            @PathVariable("idEstoque") Integer idEstoque) {

        servicePort.deletarEstoque(idEstoque);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/movimentacoes")
    @Operation(summary = "Registrar movimentação de estoque",
            description = "Endpoint para registrar uma entrada ou saída de insumos em uma UBS.")
    public ResponseEntity<MovimentacaoResponseDto> registrarMovimentacao(
            @Valid @RequestBody MovimentacaoRequestDto requestDto) {

        Estoque estoque = EstoqueMapper.INSTANCE.movimentacaoRequestToDomain(requestDto);
        MovimentacaoResponseDto response = servicePort.registrarMovimentacao(estoque, requestDto.getTipoMovimentacao(), requestDto.getMotivo());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
