package br.com.postech.ubsfacil.domain;

import br.com.postech.ubsfacil.api.dto.insumos.TipoInsumo;
import br.com.postech.ubsfacil.domain.exceptions.ErroNegocioException;

public class Insumo {
    private Integer idInsumo;
    private String sku;
    private String nome;
    private TipoInsumo tipo;
    private boolean validadeControlada;


    public Insumo(Integer idInsumo, String sku, String nome, TipoInsumo tipo, boolean validadeControlada) {
        this.idInsumo = idInsumo;
        this.sku = sku;
        this.nome = nome;
        this.tipo = tipo;
        this.validadeControlada = validadeControlada;
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

    public void setValidadeControlada(boolean validadeControlada) {
        this.validadeControlada = validadeControlada;
    }

    public void validarCamposObrigatorios() {
        if (sku == null || sku.isBlank()) {
            throw new ErroNegocioException("SKU do insumo é obrigatório");
        }
        if (nome == null || nome.length() < 3) {
            throw new ErroNegocioException("O nome do insumo deve ter ao menos 3 caracteres");
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
