package br.com.postech.ubsfacil.domain;

import br.com.postech.ubsfacil.api.dto.estoque.TipoAlerta;

import java.time.LocalDate;

public class Alerta {
    private Integer idAlerta;
    private String ubsCnes;
    private String insumoBarcode;
    private TipoAlerta tipoAlerta;
    private String detalhe;
    private LocalDate dataAlerta;

    public Alerta(Integer idAlerta, String ubsCnes, String insumoBarcode, TipoAlerta tipoAlerta, String detalhe, LocalDate dataAlerta) {
        this.idAlerta = idAlerta;
        this.ubsCnes = ubsCnes;
        this.insumoBarcode = insumoBarcode;
        this.tipoAlerta = tipoAlerta;
        this.detalhe = detalhe;
        this.dataAlerta = dataAlerta;
    }

    public Integer getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Integer idAlerta) {
        this.idAlerta = idAlerta;
    }

    public String getUbsCnes() {
        return ubsCnes;
    }

    public void setUbsCnes(String ubsCnes) {
        this.ubsCnes = ubsCnes;
    }

    public String getInsumoBarcode() {
        return insumoBarcode;
    }

    public void setInsumoBarcode(String insumoBarcode) {
        this.insumoBarcode = insumoBarcode;
    }

    public TipoAlerta getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(TipoAlerta tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public LocalDate getDataAlerta() {
        return dataAlerta;
    }

    public void setDataAlerta(LocalDate dataAlerta) {
        this.dataAlerta = dataAlerta;
    }


    public static boolean validarTipoAlerta(String tipoAlerta) {
        if (tipoAlerta == null) return false;

        try {
            TipoAlerta.valueOf(tipoAlerta.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
