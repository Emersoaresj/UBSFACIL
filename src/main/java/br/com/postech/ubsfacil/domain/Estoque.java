package br.com.postech.ubsfacil.domain;

import br.com.postech.ubsfacil.domain.exceptions.ErroNegocioException;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Estoque {
    private Integer idEstoque;
    private String ubsCnes;
    private String insumoSku;
    private Integer quantidade;
    private LocalDate validade;
    private Integer estoqueMinimo;

    public Estoque(Integer idEstoque, String ubsCnes, String insumoSku, Integer quantidade, LocalDate validade, Integer estoqueMinimo) {
        this.idEstoque = idEstoque;
        this.ubsCnes = ubsCnes;
        this.insumoSku = insumoSku;
        this.quantidade = quantidade;
        this.validade = validade;
        this.estoqueMinimo = estoqueMinimo;
    }


    public Integer getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(Integer idEstoque) {
        this.idEstoque = idEstoque;
    }

    public String getUbsCnes() {
        return ubsCnes;
    }

    public void setUbsCnes(String ubsCnes) {
        this.ubsCnes = ubsCnes;
    }

    public String getInsumoSku() {
        return insumoSku;
    }

    public void setInsumoSku(String insumoSku) {
        this.insumoSku = insumoSku;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public Integer getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    private static final Pattern CNES_PATTERN = Pattern.compile("^\\d{7}$");

    public void validarCamposObrigatorios() {
        if (ubsCnes == null) {
            throw new ErroNegocioException("CNES da UBS é obrigatório.");
        }
        if (insumoSku == null || insumoSku.isBlank()) {
            throw new ErroNegocioException("SKU do insumo é obrigatório.");
        }
        if (quantidade < 0) {
            throw new ErroNegocioException("Quantidade não pode ser negativa.");
        }
        if (estoqueMinimo < 0) {
            throw new ErroNegocioException("Estoque mínimo não pode ser negativo.");
        }
    }

    public static void validarCnes(String ubsCnes) {
        if (ubsCnes == null || !CNES_PATTERN.matcher(ubsCnes).matches()) {
            throw new ErroNegocioException("CNES inválido. Deve conter exatamente 7 dígitos numéricos.");
        }
    }

    public void validarDataValidade() {
        if (validade == null) {
            throw new ErroNegocioException("A data de validade é obrigatória.");
        }
        if (validade.isBefore(LocalDate.now())) {
            throw new ErroNegocioException("A data de validade não pode ser anterior a hoje.");
        }
    }

    public boolean isEstoqueBaixo() {
        return quantidade < estoqueMinimo;
    }

    public boolean isEsgotado() {
        return quantidade == 0;
    }

    public boolean isVencimentoProximo() {
        return validade != null && validade.isBefore(LocalDate.now().plusDays(31));
    }

    public boolean isVencido() {
        return validade != null && validade.isBefore(LocalDate.now());
    }

}