package br.com.postech.ubsfacil.domain;

import br.com.postech.ubsfacil.api.dto.insumos.TipoInsumo;
import br.com.postech.ubsfacil.domain.exceptions.ErroNegocioException;

import java.time.LocalDate;

public class Insumo {
    private Integer idInsumo;
    private String sku;
    private String nome;
    private TipoInsumo tipo;
    private boolean validadeControlada;
    private LocalDate dataValidade;
    private String barcode;


    public Insumo(Integer idInsumo, String sku, String nome, TipoInsumo tipo, LocalDate dataValidade, boolean validadeControlada, String barcode) {
        this.idInsumo = idInsumo;
        this.sku = sku;
        this.nome = nome;
        this.tipo = tipo;
        this.dataValidade = dataValidade;
        this.validadeControlada = validadeControlada;
        this.barcode = barcode;
    }

    public Integer getIdInsumo() {
        return idInsumo;
    }

    public String getSku() {
        return sku;
    }

    public String getNome() {
        return nome;
    }

    public TipoInsumo getTipo() {
        return tipo;
    }

    public boolean isValidadeControlada() {
        return validadeControlada;
    }

    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(TipoInsumo tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public void setValidadeControlada(boolean validadeControlada) {
        this.validadeControlada = validadeControlada;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void validarCamposObrigatorios() {
        if (barcode == null || barcode.isBlank()) {
            throw new ErroNegocioException("barcode do insumo é obrigatório");
        }
        if (nome == null || nome.length() < 3) {
            throw new ErroNegocioException("O nome do insumo deve ter ao menos 3 caracteres");
        }
    }

    public void validarDataValidade() {
        if (dataValidade == null) {
            throw new ErroNegocioException("A data de validade é obrigatória.");
        }
        if (dataValidade.isBefore(LocalDate.now())) {
            throw new ErroNegocioException("A data de validade não pode ser anterior a hoje.");
        }
    }

    public void validarRegraPorTipo() {
        if (tipo == TipoInsumo.MEDICAMENTO && !validadeControlada) {
            throw new ErroNegocioException("Insumos do tipo 'Medicamento' devem ter validade controlada!");
        }
    }

    public static void validarTiposInsumo(String tipo) {
        try {
            TipoInsumo.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ErroNegocioException("Tipo de insumo inválido. Tipos válidos: MEDICAMENTO, MATERIAL_HOSPITALAR, ODONTOLOGICO, OUTROS");
        }
    }
}
