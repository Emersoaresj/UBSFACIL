package br.com.postech.ubsfacil.api.dto.insumos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InsumoRequestDto {

    @NotBlank(message = "O SKU é obrigatório")
    private String sku;

    @NotBlank(message = "O nome do insumo é obrigatório")
    @Size(min = 3, message = "O nome do insumo deve ter ao menos 3 caracteres")
    private String nome;

    @NotBlank(message = "O tipo do insumo é obrigatório")
    private String tipo;

    private LocalDate dataValidade;

    private boolean validadeControlada;

    private String barcode;
}
