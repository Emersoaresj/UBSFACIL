package br.com.postech.ubsfacil.api.dto.ubs;

import lombok.Data;

@Data
public class UbsRequestDto {

    private String nome;
    private String cnes;
    private String telefone;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
}
