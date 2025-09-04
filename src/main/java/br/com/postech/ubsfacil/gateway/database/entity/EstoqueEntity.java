package br.com.postech.ubsfacil.gateway.database.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Table
@Entity(name = "estoque")
public class EstoqueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estoque")
    private Integer idEstoque;

    @Column(name = "ubs_cnes")
    private String ubsCnes;

    @Column(name = "insumo_sku")
    private String insumoSku;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "validade")
    private LocalDate validade;

    @Column(name = "estoque_minimo")
    private Integer estoqueMinimo;
}
