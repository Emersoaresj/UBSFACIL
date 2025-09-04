package br.com.postech.ubsfacil.api.dto.estoque;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EstoqueResponseDto {
    private Integer idEstoque;
    private String ubsCnes;
    private String insumoSku;
    private Integer quantidade;
    private LocalDate validade;
    private Integer estoqueMinimo;

}
