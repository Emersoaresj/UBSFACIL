package br.com.postech.ubsfacil.gateway.database.repository;

import br.com.postech.ubsfacil.api.dto.ubs.UbsEstoqueProjection;
import br.com.postech.ubsfacil.gateway.database.entity.UbsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UbsRepositoryJPA extends JpaRepository<UbsEntity, Integer> {

    Optional<UbsEntity> findByCnes(String cnes);

    List<UbsEntity> findAllByCidadeAndUf(String cidade, String uf);

    List<UbsEntity> findAllByCidade(String cidade);

    List<UbsEntity> findAllByUf(String uf);

    void deleteByCnes(String cnes);

    @Query(value = """
            select
                a.nome as nomeUbs,
                a.cnes as cnesUbs,
                a.telefone as telefoneUbs,
                a.logradouro as logradouroUbs,
                a.numero as numeroUbs,
                a.bairro as bairroUbs,
                a.cep as cepUbs,
                a.latitude as latitudeUbs,
                a.longitude as longitudeUbs,
                b.quantidade as quantidadeEstoque,
                b.estoque_minimo as estoqueMinimo,
                b.insumo_barcode as barcode,
                c.nome as nomeInsumo
            from ubs a
            join estoque b on a.cnes = b.ubs_cnes
            join insumo c on b.insumo_barcode = c.barcode
            where b.quantidade > b.estoque_minimo
              and c.nome ilike concat('%', :nome, '%');
            """, nativeQuery = true)
    List<UbsEstoqueProjection> buscaUbsComEstoque(String nome);
}
