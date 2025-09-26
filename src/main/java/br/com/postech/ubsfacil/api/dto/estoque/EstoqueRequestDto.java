package br.com.postech.ubsfacil.api.dto.estoque;

import lombok.Data;

@Data
public class EstoqueRequestDto {
    private String ubsCnes;
    private String insumoBarcode;
    private Integer quantidade;
    private Integer estoqueMinimo;
}
