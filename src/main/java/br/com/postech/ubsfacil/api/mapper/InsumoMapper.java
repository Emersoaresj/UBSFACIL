package br.com.postech.ubsfacil.api.mapper;

import br.com.postech.ubsfacil.api.dto.insumos.InsumoRequestDto;
import br.com.postech.ubsfacil.domain.Insumo;
import br.com.postech.ubsfacil.gateway.database.entity.InsumoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InsumoMapper {

    InsumoMapper INSTANCE = Mappers.getMapper(InsumoMapper.class);


    Insumo requestToDomain (InsumoRequestDto insumoRequestDto);

    @Mapping(target = "idInsumo", ignore = true)
    InsumoEntity domainToEntity(Insumo insumo);

    Insumo entityToDomain(InsumoEntity insumoEntity);
}
