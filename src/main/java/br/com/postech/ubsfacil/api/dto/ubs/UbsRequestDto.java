package br.com.postech.ubsfacil.api.dto.ubs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UbsRequestDto {

    @NotBlank(message = "O nome da UBS é obrigatório")
    private String nome;

    @NotBlank(message = "O código CNES é obrigatório")
    @Pattern(regexp = "\\d{7}", message = "O CNES deve conter exatamente 7 dígitos")
    private String cnes;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "^\\(\\d{2}\\) ?\\d{4}-\\d{4}$", message = "O telefone deve estar no formato (XX) XXXX-XXXX")
    private String telefone;

    @NotBlank(message = "O logradouro é obrigatório")
    private String logradouro;

    @NotBlank(message = "O número é obrigatório")
    private String numero;

    @NotBlank(message = "O bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "O estado (UF) é obrigatório")
    @Pattern(regexp = "^[A-Z]{2}$", message = "UF deve ter 2 letras maiúsculas")
    private String uf;

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP deve estar no formato 00000-000 ou 00000000")
    private String cep;
}
