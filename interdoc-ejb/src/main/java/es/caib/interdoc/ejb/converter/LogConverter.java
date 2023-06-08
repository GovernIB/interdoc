package es.caib.interdoc.ejb.converter;

import es.caib.interdoc.persistence.model.Log;
import es.caib.interdoc.service.model.LogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre Log i LogDTO. La implementació es generarà automàticament per MapStruct
 *
 * @author jagarcia
 */
@Mapper
public interface LogConverter extends Converter<Log, LogDTO> {

    @Override
    LogDTO toDTO(Log entity);

    @Override
    Log toEntity(LogDTO dto);

    @Override
    void updateFromDTO(@MappingTarget Log entity, LogDTO dto);
}
