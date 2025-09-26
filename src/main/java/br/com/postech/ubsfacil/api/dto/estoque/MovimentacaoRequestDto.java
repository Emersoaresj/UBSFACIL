package br.com.postech.ubsfacil.api.dto.estoque;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MovimentacaoRequestDto {
    private String ubsCnes;
    private String insumoBarcode;
    private Integer quantidadeMovimentada;
    private LocalDate insumoDataValidade;
    private Integer estoqueMinimo;
    private String tipoMovimentacao;
    private String motivo;
}
