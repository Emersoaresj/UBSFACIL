package br.com.postech.ubsfacil.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ubs {
    private String nome;
    private String cnes;  // Cadastro Nacional de Estabelecimentos de Saúde
    private String telefone;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
}
