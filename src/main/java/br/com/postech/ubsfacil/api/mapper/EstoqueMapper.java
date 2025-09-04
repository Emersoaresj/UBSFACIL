package br.com.postech.ubsfacil.api.mapper;

import br.com.postech.ubsfacil.api.dto.estoque.EstoqueRequestDto;
import br.com.postech.ubsfacil.api.dto.estoque.EstoqueResponseDto;
import br.com.postech.ubsfacil.domain.Estoque;
import br.com.postech.ubsfacil.gateway.database.entity.EstoqueEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EstoqueMapper {

    EstoqueMapper INSTANCE = Mappers.getMapper(EstoqueMapper.class);

    @Mapping(target = "idEstoque", ignore = true)
    Estoque requestToDomain (EstoqueRequestDto estoqueRequestDto);

    Estoque entityToDomain(EstoqueEntity entity);

    EstoqueEntity domainToEntity(Estoque estoque);

    EstoqueResponseDto domainToResponse(Estoque estoque);

    List<EstoqueResponseDto> listDomainToResponse(List<Estoque> estoque);

    List<Estoque> listEntityToDomain(List<EstoqueEntity> entities);
}
