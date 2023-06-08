package es.caib.interdoc.ejb.converter;

import es.caib.interdoc.persistence.model.Entitat;
import es.caib.interdoc.service.model.EntitatDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre Entitat i EntitatDTO. La implementació es generarà automàticament per MapStruct
 *
 * @author jagarcia
 */
@Mapper
public interface EntitatConverter extends Converter<Entitat, EntitatDTO> {

    @Override
    EntitatDTO toDTO(Entitat entity);

    @Override
    Entitat toEntity(EntitatDTO dto);

    //@Mapping(target = "codiDir3", ignore = true) // no volem que s'actualitzi
    @Override
    void updateFromDTO(@MappingTarget Entitat entity, EntitatDTO dto);
}
