package br.com.postech.ubsfacil.api.dto.ubs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UbsUpdateDto {

    @Schema(description = "Nome da Unidade Básica de Saúde", example = "AMA UBS PARQUE SANTO ANTONIO")
    private String nome;

    @Schema(description = "Telefone da Unidade Básica de Saúde", example = "(11) 1234-5678")
    @Pattern(regexp = "^\\(\\d{2}\\) ?\\d{4}-\\d{4}$", message = "O telefone deve estar no formato (XX) XXXX-XXXX")
    private String telefone;

    @Schema(description = "Logradouro da Unidade Básica de Saúde", example = "Rua das Flores")
    private String logradouro;

    @Schema(description = "Número do endereço da Unidade Básica de Saúde", example = "123")
    private String numero;

    @Schema(description = "Bairro da Unidade Básica de Saúde", example = "Centro")
    private String bairro;

    @Schema(description = "Cidade da Unidade Básica de Saúde", example = "São Paulo")
    private String cidade;

    @Schema(description = "Estado da Unidade Básica de Saúde (UF, 2 letras)", example = "SP")
    @Pattern(regexp = "^[A-Z]{2}$", message = "UF deve ter 2 letras maiúsculas")
    private String uf;

    @Schema(description = "CEP da Unidade Básica de Saúde", example = "12345-678")
    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP deve estar no formato 00000-000 ou 00000000")
    private String cep;
}
