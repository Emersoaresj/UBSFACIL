package br.com.postech.ubsfacil.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Resposta padrão de mensagens do sistema.")
public class ResponseDto {

    @Schema(description = "Mensagem de resposta")
    private String message;
    @Schema(description = "Objeto do response, se houver")
    private Object data;
}
