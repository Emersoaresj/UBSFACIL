package br.com.postech.ubsfacil.gateway.client;

import br.com.postech.ubsfacil.gateway.client.dto.Coordenada;

public interface GeocordingServicePort {

    Coordenada buscarCoordenadasPorCep(String cep);
}
