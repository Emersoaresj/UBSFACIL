package br.com.postech.ubsfacil.gateway.ports.ubs;

import br.com.postech.ubsfacil.domain.Ubs;

import java.util.List;
import java.util.Optional;

public interface UbsRepositoryPort {

    Ubs cadastraUbs(Ubs ubs);

    Optional<Ubs> findByCnes(String cnes);

    List<Ubs> findAllByCidadeAndUf(String cidade, String uf);

    List<Ubs> findAllByCidade(String cidade);

    List<Ubs> findAllByUf(String uf);

    Ubs atualizarUbs(Ubs ubs);

    void deletarUbs(String cnes);
}
