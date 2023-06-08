package es.caib.interdoc.ejb.converter;

import es.caib.interdoc.persistence.model.Aplicacio;
import es.caib.interdoc.service.model.AplicacioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre Aplicacio i AplicacioDTO. La implementació es generarà automàticament per MapStruct
 *
 * @author jagarcia
 */
@Mapper
public interface AplicacioConverter extends Converter<Aplicacio, AplicacioDTO> {

    @Override
    AplicacioDTO toDTO(Aplicacio entity);

    @Override
    Aplicacio toEntity(AplicacioDTO dto);

    //@Mapping(target = "codiDir3", ignore = true) // no volem que s'actualitzi
    @Override
    void updateFromDTO(@MappingTarget Aplicacio entity, AplicacioDTO dto);
}
