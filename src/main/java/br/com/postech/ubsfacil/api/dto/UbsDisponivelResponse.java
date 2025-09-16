package br.com.postech.ubsfacil.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UbsDisponivelResponse {
    private String nomeUbs;
    private String enderecoCompleto;
    private String numero;
    private String bairro;
    private String telefone;
    private Double distanciaKm;
    private Integer quantidadeDisponivel;
}
