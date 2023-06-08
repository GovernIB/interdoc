package es.caib.interdoc.ejb.converter;

import es.caib.interdoc.persistence.model.Traza;
import es.caib.interdoc.service.model.TrazaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre Aplicacio i AplicacioDTO. La implementació es generarà automàticament per MapStruct
 *
 * @author jagarcia
 */
@Mapper
public interface TrazaConverter extends Converter<Traza, TrazaDTO> {

    @Override
    TrazaDTO toDTO(Traza entity);

    @Override
    Traza toEntity(TrazaDTO dto);

    @Override
    void updateFromDTO(@MappingTarget Traza entity, TrazaDTO dto);
}
