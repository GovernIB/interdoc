package es.caib.interdoc.ejb.converter;

import es.caib.interdoc.persistence.model.InfoSignatura;
import es.caib.interdoc.service.model.InfoSignaturaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre InfoSignatura i InfoSignaturaDTO. La implementació es generarà automàticament per MapStruct
 *
 * @author jagarcia
 */
@Mapper
public interface InfoSignaturaConverter extends Converter<InfoSignatura, InfoSignaturaDTO> {

    @Override
    InfoSignaturaDTO toDTO(InfoSignatura entity);

    @Override
    InfoSignatura toEntity(InfoSignaturaDTO dto);

    @Override
    void updateFromDTO(@MappingTarget InfoSignatura entity, InfoSignaturaDTO dto);
}
