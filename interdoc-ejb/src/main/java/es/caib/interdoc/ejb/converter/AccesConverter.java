package es.caib.interdoc.ejb.converter;

import es.caib.interdoc.persistence.model.Acces;
import es.caib.interdoc.service.model.AccesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre Aplicacio i AplicacioDTO. La implementació es generarà automàticament per MapStruct
 *
 * @author jagarcia
 */
@Mapper
public interface AccesConverter extends Converter<Acces, AccesDTO> {

    @Override
    AccesDTO toDTO(Acces entity);

    @Override
    Acces toEntity(AccesDTO dto);

    @Override
    void updateFromDTO(@MappingTarget Acces entity, AccesDTO dto);
}
