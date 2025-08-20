package br.com.postech.ubsfacil.api.dto.ubs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UbsResponseDto {
    @Schema(description = "Nome da Unidade Básica de Saúde", example = "AMA UBS PARQUE SANTO ANTONIO")
    private String nome;

    @Schema(description = "Cadastro Nacional de Estabelecimentos de Saúde", example = "1234567")
    private Integer cnes;

    @Schema(description = "Telefone da Unidade Básica de Saúde", example = "(11) 1234-5678")
    private String telefone;

    @Schema(description = "Cidade da Unidade Básica de Saúde", example = "São Paulo")
    private String cidade;

    @Schema(description = "Estado da Unidade Básica de Saúde (UF, 2 letras)", example = "SP")
    private String uf;
}
