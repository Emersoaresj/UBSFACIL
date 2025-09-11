package br.com.postech.ubsfacil.gateway.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordenada {
    private Double latitude;
    private Double longitude;
}
