package br.com.postech.ubsfacil.gateway.client;

import br.com.postech.ubsfacil.gateway.client.dto.Coordenada;
import br.com.postech.ubsfacil.gateway.client.dto.NominatimResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeocodingServiceImpl implements GeocordingServicePort {

    private final NominatimClient nominatimClient;

    public GeocodingServiceImpl(NominatimClient nominatimClient) {
        this.nominatimClient = nominatimClient;
    }

    @Override
    public Coordenada buscarCoordenadasPorCep(String cep) {
        List<NominatimResponse> response = nominatimClient.search(cep, "Brazil", "json");

        if (response != null && !response.isEmpty()) {
            var result = response.get(0);
            return new Coordenada(
                    Double.parseDouble(result.getLat()),
                    Double.parseDouble(result.getLon())
            );
        }

        throw new RuntimeException("Não foi possível obter coordenadas para o CEP: " + cep);
    }
}

