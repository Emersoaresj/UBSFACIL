package br.com.postech.ubsfacil.api.mapper;

import br.com.postech.ubsfacil.domain.Movimentacao;
import br.com.postech.ubsfacil.gateway.database.entity.MovimentacaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovimentacaoMapper {

    MovimentacaoMapper INSTANCE = Mappers.getMapper(MovimentacaoMapper.class);

    @Mapping(target = "idMovimentacao", ignore = true)
    MovimentacaoEntity domainToEntity(Movimentacao movimentacao);
}
