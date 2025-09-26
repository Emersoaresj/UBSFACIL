package br.com.postech.ubsfacil.gateway.database.entity;

import br.com.postech.ubsfacil.api.dto.estoque.TipoAlerta;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "alertas")
@Data
public class AlertaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Integer idAlerta;

    @Column(name = "ubs_cnes")
    private String ubsCnes;

    @Column(name = "insumo_barcode")
    private String insumoBarcode;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_alerta")
    private TipoAlerta tipoAlerta;

    @Column(name = "detalhe")
    private String detalhe;

    @Column(name = "data_alerta")
    private LocalDate dataAlerta;
}