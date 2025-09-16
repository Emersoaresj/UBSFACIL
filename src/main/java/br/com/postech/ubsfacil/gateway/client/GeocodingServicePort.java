package br.com.postech.ubsfacil.gateway.client;

import br.com.postech.ubsfacil.gateway.client.dto.Coordenada;

public interface GeocodingServicePort {

    Coordenada buscarCoordenadasPorCep(String cep);
}
