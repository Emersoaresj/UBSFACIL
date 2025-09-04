package br.com.postech.ubsfacil.api.dto.estoque;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EstoqueRequestUpdateDto {
    private Integer quantidade;
    private LocalDate validade;
    private Integer estoqueMinimo;
}
