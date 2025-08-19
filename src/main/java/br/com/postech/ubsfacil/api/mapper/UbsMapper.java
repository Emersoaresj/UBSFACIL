package br.com.postech.ubsfacil.api.mapper;

import br.com.postech.ubsfacil.api.dto.UbsRequestDto;
import br.com.postech.ubsfacil.domain.Ubs;
import br.com.postech.ubsfacil.gateway.database.entity.UbsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UbsMapper {

    UbsMapper INSTANCE = Mappers.getMapper(UbsMapper.class);

    @Mapping(target = "idUbs", ignore = true)
    UbsEntity domainToEntity(Ubs ubs);

    Ubs requestCreateToDomain(UbsRequestDto ubsRequestDto);

    Ubs entityToDomain (UbsEntity ubsEntity);
}
