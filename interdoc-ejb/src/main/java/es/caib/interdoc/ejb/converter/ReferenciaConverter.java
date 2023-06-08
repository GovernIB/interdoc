package es.caib.interdoc.ejb.converter;

import es.caib.interdoc.persistence.model.Referencia;
import es.caib.interdoc.service.model.ReferenciaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre Aplicacio i AplicacioDTO. La implementació es generarà automàticament per MapStruct
 *
 * @author jagarcia
 */
@Mapper
public interface ReferenciaConverter extends Converter<Referencia, ReferenciaDTO> {

    @Override
    ReferenciaDTO toDTO(Referencia entity);

    @Override
    Referencia toEntity(ReferenciaDTO dto);

    //@Mapping(target = "codiDir3", ignore = true) // no volem que s'actualitzi
    @Override
    void updateFromDTO(@MappingTarget Referencia entity, ReferenciaDTO dto);
}
