package br.com.postech.ubsfacil.gateway.client;

import br.com.postech.ubsfacil.domain.exceptions.ErroExternoException;
import br.com.postech.ubsfacil.gateway.client.dto.Coordenada;
import br.com.postech.ubsfacil.gateway.client.dto.NominatimResponse;
import feign.FeignException;
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

        try {
            List<NominatimResponse> response = nominatimClient.search(cep, "Brazil", "json");

            if (response != null && !response.isEmpty()) {
                var result = response.get(0);
                return new Coordenada(
                        Double.parseDouble(result.getLat()),
                        Double.parseDouble(result.getLon())
                );
            }
            throw new ErroExternoException("Nenhum resultado encontrado para o CEP: " + cep);
        } catch (ErroExternoException e) {
            throw e;
        } catch (FeignException e) {
            throw new ErroExternoException("Erro ao buscar coordenadas: " + e.getMessage());
        } catch (Exception e) {
            throw new ErroExternoException("Erro inesperado ao buscar coordenadas: " + e.getMessage());
        }
    }
}

