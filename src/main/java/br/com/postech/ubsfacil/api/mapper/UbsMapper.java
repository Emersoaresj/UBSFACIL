package br.com.postech.ubsfacil.api.mapper;

import br.com.postech.ubsfacil.api.dto.ubs.UbsRequestDto;
import br.com.postech.ubsfacil.api.dto.ubs.UbsResponseDto;
import br.com.postech.ubsfacil.domain.Ubs;
import br.com.postech.ubsfacil.gateway.database.entity.UbsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UbsMapper {

    UbsMapper INSTANCE = Mappers.getMapper(UbsMapper.class);

    @Mapping(target = "idUbs", ignore = true)
    UbsEntity domainToEntity(Ubs ubs);

    @Mapping(target = "idUbs", ignore = true)
    Ubs requestCreateToDomain(UbsRequestDto ubsRequestDto);

    Ubs entityToDomain (UbsEntity ubsEntity);

    List<Ubs> listEntityToDomain (List<UbsEntity> ubsEntities);

    UbsResponseDto domainToDto(Ubs ubs);

    UbsEntity updateDomainToEntity(Ubs ubs);
}
