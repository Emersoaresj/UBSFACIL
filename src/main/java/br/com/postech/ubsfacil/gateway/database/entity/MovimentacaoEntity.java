package br.com.postech.ubsfacil.gateway.database.entity;

import br.com.postech.ubsfacil.api.dto.estoque.TipoMovimentacao;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "movimentacao")
public class MovimentacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimentacao")
    private Integer idMovimentacao;

    @Column(name = "ubs_cnes")
    private String ubsCnes;

    @Column(name = "insumo_sku")
    private String insumoSku;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimentacao")
    private TipoMovimentacao tipoMovimentacao;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "data_movimentacao")
    private LocalDate dataMovimentacao;

}
