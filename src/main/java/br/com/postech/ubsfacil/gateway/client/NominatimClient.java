package br.com.postech.ubsfacil.gateway.client;

import br.com.postech.ubsfacil.gateway.client.dto.NominatimResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "nominatimClient", url = "https://nominatim.openstreetmap.org")
public interface NominatimClient {

    @GetMapping("/search")
    List<NominatimResponse> search(
            @RequestParam("postalcode") String cep,
            @RequestParam("country") String country,
            @RequestParam("format") String format
    );
}
