package br.com.postech.ubsfacil.api.dto.estoque;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MovimentacaoResponseDto {

    private String message;
    private String ubsCnes;
    private String insumoBarcode;
    private Integer quantidadeTotal;
    private LocalDate insumoDataValidade;
    private TipoMovimentacao tipoMovimentacao;
}
