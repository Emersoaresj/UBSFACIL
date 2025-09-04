package br.com.postech.ubsfacil.gateway.database.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table
@Entity(name = "insumo")
public class InsumoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_insumo")
    private Integer idInsumo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sku")
    private String sku;

    @Column(name = "tipo")
    private String tipo; // Medicamento, material hospitalar, etc.

    @Column(name = "validade_controlada")
    private boolean validadeControlada;
}
