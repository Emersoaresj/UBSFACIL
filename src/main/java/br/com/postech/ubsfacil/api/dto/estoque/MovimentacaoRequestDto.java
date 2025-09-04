package br.com.postech.ubsfacil.api.dto.estoque;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MovimentacaoRequestDto {
    private String ubsCnes;
    private String insumoSku;
    private Integer quantidade;
    private LocalDate validade;
    private Integer estoqueMinimo;
    private String tipoMovimentacao;
    private String motivo;
}
