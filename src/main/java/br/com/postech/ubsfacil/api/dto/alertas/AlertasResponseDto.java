package br.com.postech.ubsfacil.api.dto.alertas;

import br.com.postech.ubsfacil.api.dto.estoque.TipoAlerta;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AlertasResponseDto {
    private String ubsCnes;
    private String insumoBarcode;
    private TipoAlerta tipoAlerta;
    private String detalhe;
    private LocalDate dataAlerta;
}
