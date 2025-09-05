package br.com.postech.ubsfacil.api.mapper;

import br.com.postech.ubsfacil.api.dto.alertas.AlertasResponseDto;
import br.com.postech.ubsfacil.domain.Alerta;
import br.com.postech.ubsfacil.gateway.database.entity.AlertaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AlertaMapper {

    AlertaMapper INSTANCE = Mappers.getMapper(AlertaMapper.class);

    @Mapping(target = "idAlerta", ignore = true)
    AlertaEntity domainToEntity(Alerta alerta);

    List<AlertasResponseDto> domainToResponse(List<Alerta> alerta);


    List<Alerta> entityToDomain(List<AlertaEntity> alertaEntities);
}
