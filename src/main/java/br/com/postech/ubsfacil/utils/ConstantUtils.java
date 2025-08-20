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

    //SUCESSO
    public static final String UBS_CADASTRADA = "UBS cadastrada com sucesso!";
    public static final String UBS_ATUALIZADA = "UBS atualizada com sucesso!";
}
