package br.com.postech.ubsfacil.domain;

import br.com.postech.ubsfacil.api.dto.insumos.TipoInsumo;
import br.com.postech.ubsfacil.domain.exceptions.ErroNegocioException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Insumo {
    private Integer idInsumo;
    private String sku;
    private String nome;
    private TipoInsumo tipo;
    private boolean validadeControlada;


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
