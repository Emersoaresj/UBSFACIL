package br.com.postech.ubsfacil.api.dto.ubs;

public interface UbsEstoqueProjection {
    String getNomeUbs();
    String getCnesUbs();
    String getTelefoneUbs();
    String getLogradouroUbs();
    String getNumeroUbs();
    String getBairroUbs();
    String getCepUbs();
    Double getLatitudeUbs();
    Double getLongitudeUbs();
    Integer getQuantidadeEstoque();
    Integer getEstoqueMinimo();
    String getNomeRemedio();
}
