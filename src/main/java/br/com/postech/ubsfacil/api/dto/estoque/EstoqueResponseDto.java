package br.com.postech.ubsfacil.api.dto.estoque;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EstoqueResponseDto {
    private Integer idEstoque;
    private String ubsCnes;
    private String insumoBarcode;
    private Integer quantidade;
    private LocalDate insumoDataValidade;
    private Integer estoqueMinimo;

}
