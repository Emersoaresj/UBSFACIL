package br.com.postech.ubsfacil.domain;

import br.com.postech.ubsfacil.api.dto.estoque.TipoMovimentacao;
import br.com.postech.ubsfacil.domain.exceptions.ErroNegocioException;

import java.time.LocalDate;

public class Movimentacao {

    private Integer idMovimentacao;
    private String ubsCnes;
    private String insumoBarcode;
    private TipoMovimentacao tipoMovimentacao;
    private Integer quantidade;
    private String motivo;
    private LocalDate dataMovimentacao;


    public Movimentacao(Integer idMovimentacao, String ubsCnes, String insumoBarcode, TipoMovimentacao tipoMovimentacao, Integer quantidade, String motivo, LocalDate dataMovimentacao) {
        this.idMovimentacao = idMovimentacao;
        this.ubsCnes = ubsCnes;
        this.insumoBarcode = insumoBarcode;
        this.tipoMovimentacao = tipoMovimentacao;
        this.quantidade = quantidade;
        this.motivo = motivo;
        this.dataMovimentacao = dataMovimentacao;
        validar();
    }

    private void validar() {
        if (ubsCnes == null || ubsCnes.isBlank()) {
            throw new ErroNegocioException("CNES da UBS é obrigatório.");
        }
        if (insumoBarcode == null || insumoBarcode.isBlank()) {
            throw new ErroNegocioException("SKU do insumo é obrigatório.");
        }
        if (tipoMovimentacao == null) {
            throw new ErroNegocioException("Tipo da movimentação é obrigatório.");
        }
        if (!tipoMovimentacao.equals(TipoMovimentacao.ENTRADA) && !tipoMovimentacao.equals(TipoMovimentacao.SAIDA)){
            throw new ErroNegocioException("Tipo da movimentação inválido. Deve ser 'ENTRADA' ou 'SAIDA'.");
        }
        if (quantidade == null || quantidade <= 0) {
            throw new ErroNegocioException("A quantidade da movimentação deve ser maior que zero.");
        }
        if (dataMovimentacao == null) {
            throw new ErroNegocioException("A data da movimentação é obrigatória.");
        }
    }


    public static void validarTipoMovimentacao(String tipo) {
        if (tipo == null || (!tipo.equalsIgnoreCase("ENTRADA") && !tipo.equalsIgnoreCase("SAIDA"))) {
            throw new ErroNegocioException("Tipo da movimentação inválido. Deve ser 'ENTRADA' ou 'SAIDA'.");
        }
    }

    public Integer getIdMovimentacao() {
        return idMovimentacao;
    }

    public void setIdMovimentacao(Integer idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
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

    public TipoMovimentacao getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDate dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }
}
