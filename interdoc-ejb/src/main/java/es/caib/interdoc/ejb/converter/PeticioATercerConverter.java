package es.caib.interdoc.ejb.converter;

import org.mapstruct.*;

import es.caib.interdoc.persistence.model.PeticioATercer;
import es.caib.interdoc.service.model.PeticioATercerDTO;
import es.caib.interdoc.service.model.PeticioEstatDTO;
import es.caib.interdoc.service.model.TipusIdentificacioDTO;

/**
 * Conversor entre PeticioATercer i PeticioATercerDTO.
 *
 * @author jagarcia
 */
@Mapper
public interface PeticioATercerConverter extends Converter<PeticioATercer, PeticioATercerDTO> {

    @Override
    PeticioATercerDTO toDTO(PeticioATercer entity);

    @Override
    PeticioATercer toEntity(PeticioATercerDTO dto);

    @Override
    default void updateFromDTO(@MappingTarget PeticioATercer entity, PeticioATercerDTO dto) { }

}
