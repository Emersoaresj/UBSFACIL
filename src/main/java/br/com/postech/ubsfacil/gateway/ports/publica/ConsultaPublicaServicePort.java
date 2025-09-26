package br.com.postech.ubsfacil.gateway.ports.publica;

import br.com.postech.ubsfacil.api.dto.UbsDisponivelResponse;

import java.util.List;

public interface ConsultaPublicaServicePort {
    List<UbsDisponivelResponse> consultaRemedioProximo(String cep, String nomeRemedio, Double raioKm);
}
