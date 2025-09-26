package br.com.postech.ubsfacil.domain;

import br.com.postech.ubsfacil.domain.exceptions.ErroNegocioException;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Estoque {
    private Integer idEstoque;
    private String ubsCnes;
    private String insumoBarcode;
    private LocalDate insumoDataValidade;
    private Integer quantidade;
    private Integer estoqueMinimo;

    public Estoque(Integer idEstoque, String ubsCnes, String insumoBarcode, LocalDate insumoDataValidade, Integer quantidade, Integer estoqueMinimo) {
        this.idEstoque = idEstoque;
        this.ubsCnes = ubsCnes;
        this.insumoDataValidade = insumoDataValidade;
        this.insumoBarcode = insumoBarcode;
        this.quantidade = quantidade;
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

    public LocalDate getInsumoDataValidade() {
        return insumoDataValidade;
    }

    public void setInsumoDataValidade(LocalDate validadeInsumo) {
        this.insumoDataValidade = validadeInsumo;
    }

    public String getInsumoBarcode() {
        return insumoBarcode;
    }

    public void setInsumoBarcode(String insumoBarcode) {
        this.insumoBarcode = insumoBarcode;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
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
        if (insumoBarcode == null || insumoBarcode.isBlank()) {
            throw new ErroNegocioException("Barcode do insumo é obrigatório.");
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

    public boolean isVencimentoProximo() {
        return insumoDataValidade != null && insumoDataValidade.isBefore(LocalDate.now().plusDays(31));
    }

    public boolean isVencido() {
        return insumoDataValidade != null && insumoDataValidade.isBefore(LocalDate.now());
    }
    public boolean isEstoqueBaixo() {
        return quantidade < estoqueMinimo;
    }

    public boolean isEsgotado() {
        return quantidade == 0;
    }

}