package es.caib.interdoc.ejb.converter;

import es.caib.interdoc.persistence.model.Fitxer;
import es.caib.interdoc.service.model.FitxerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre Fitxer i FitxerDTO. La implementació es generarà automàticament per MapStruct
 *
 * @author jagarcia
 */
@Mapper
public interface FitxerConverter extends Converter<Fitxer, FitxerDTO> {

    @Override
    FitxerDTO toDTO(Fitxer entity);

    @Override
    Fitxer toEntity(FitxerDTO dto);

    @Override
    void updateFromDTO(@MappingTarget Fitxer entity, FitxerDTO dto);
}
