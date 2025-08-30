package br.com.postech.ubsfacil.api.dto.insumos;

import lombok.Data;

@Data
public class InsumoResponseDto {
    private String sku;
    private String nome;
    private TipoInsumo tipo;
    private boolean validadeControlada;
}
