package br.com.postech.ubsfacil.gateway.database.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table
@Entity(name = "ubs")
public class UbsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUbs")
    private Integer idUbs;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cnes")
    private String cnes;  // Cadastro Nacional de Estabelecimentos de Saúde

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "numero")
    private String numero;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "uf")
    private String uf;

    @Column(name = "cep")
    private String cep;
}
