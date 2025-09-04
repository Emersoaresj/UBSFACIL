package br.com.postech.ubsfacil.utils;

import lombok.Data;

@Data
public class ConstantUtils {

    private ConstantUtils() {
        throw new IllegalStateException("Classe Utilitária");
    }

    //ERROS
    public static final String UBS_NAO_ENCONTRADA = "UBS não encontrada com as informações fornecidas.";
    public static final String ERRO_UF = "A UF deve conter exatamente 2 letras maiúsculas.";
    public static final String INSUMO_NAO_ENCONTRADO = "Insumo não encontrado com as informações fornecidas.";

    //SUCESSO
    public static final String UBS_CADASTRADA = "UBS cadastrada com sucesso!";
    public static final String UBS_ATUALIZADA = "UBS atualizada com sucesso!";
    public static final String INSUMO_CADASTRADO = "Insumo cadastrado com sucesso!";
    public static final String INSUMO_ATUALIZADO = "Insumo atualizado com sucesso!";
}
