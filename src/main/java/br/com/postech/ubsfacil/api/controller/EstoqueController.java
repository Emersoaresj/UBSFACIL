package br.com.postech.ubsfacil.api.controller;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.api.dto.estoque.EstoqueRequestDto;
import br.com.postech.ubsfacil.api.mapper.EstoqueMapper;
import br.com.postech.ubsfacil.domain.Estoque;
import br.com.postech.ubsfacil.gateway.ports.EstoqueServicePort;
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

}
