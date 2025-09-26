package br.com.postech.ubsfacil.service;

import br.com.postech.ubsfacil.api.dto.UbsDisponivelResponse;
import br.com.postech.ubsfacil.api.dto.ubs.UbsEstoqueProjection;
import br.com.postech.ubsfacil.domain.Ubs;
import br.com.postech.ubsfacil.gateway.client.GeocodingServicePort;
import br.com.postech.ubsfacil.gateway.client.dto.Coordenada;
import br.com.postech.ubsfacil.gateway.ports.estoque.EstoqueRepositoryPort;
import br.com.postech.ubsfacil.gateway.ports.publica.ConsultaPublicaServicePort;
import br.com.postech.ubsfacil.gateway.ports.ubs.UbsRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ConsultaPublicaServiceImpl implements ConsultaPublicaServicePort {


    private final GeocodingServicePort geocodingServicePort;
    private final UbsRepositoryPort ubsRepositoryPort;
    private final EstoqueRepositoryPort estoqueRepositoryPort;

    public ConsultaPublicaServiceImpl(GeocodingServicePort geocodingServicePort, UbsRepositoryPort ubsRepositoryPort, EstoqueRepositoryPort estoqueRepositoryPort) {
        this.geocodingServicePort = geocodingServicePort;
        this.ubsRepositoryPort = ubsRepositoryPort;
        this.estoqueRepositoryPort = estoqueRepositoryPort;
    }


    @Override
    public List<UbsDisponivelResponse> consultaRemedioProximo(String cepPaciente, String nomeRemedio, Double raioKm) {
        Ubs.validarCep(cepPaciente);

        Coordenada pacienteCoord = geocodingServicePort.buscarCoordenadasPorCep(cepPaciente);

        List<UbsEstoqueProjection> ubsComEstoque = ubsRepositoryPort.buscaUbsComEstoque(nomeRemedio);


        return ubsComEstoque.stream()
                .map(ubs -> {
                    double distancia = calcularDistanciaKm(
                            pacienteCoord.getLatitude(), pacienteCoord.getLongitude(),
                            ubs.getLatitudeUbs(), ubs.getLongitudeUbs());

                    String enderecoCompleto = String.format("%s, %s - %s, CEP: %s",
                            ubs.getLogradouroUbs(),
                            ubs.getNumeroUbs(),
                            ubs.getBairroUbs(),
                            ubs.getCepUbs());

                    return new UbsDisponivelResponse(
                            ubs.getNomeUbs(),
                            enderecoCompleto,
                            ubs.getNumeroUbs(),
                            ubs.getBairroUbs(),
                            ubs.getTelefoneUbs(),
                            Math.round(distancia * 100.0) / 100.0,
                            ubs.getQuantidadeEstoque()
                    );
                })
                .filter(resp -> resp.getDistanciaKm() <= raioKm)
                .sorted(Comparator.comparingDouble(UbsDisponivelResponse::getDistanciaKm))
                .toList();
    }

    private double calcularDistanciaKm(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // raio da Terra em km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
