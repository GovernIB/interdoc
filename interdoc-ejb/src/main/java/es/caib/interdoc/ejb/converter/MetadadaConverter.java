package es.caib.interdoc.ejb.converter;

import es.caib.interdoc.persistence.model.Metadada;
import es.caib.interdoc.service.model.MetadadaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre Aplicacio i AplicacioDTO. La implementació es generarà automàticament per MapStruct
 *
 * @author jagarcia
 */
@Mapper
public interface MetadadaConverter extends Converter<Metadada, MetadadaDTO> {

    @Override
    MetadadaDTO toDTO(Metadada entity);

    @Override
    Metadada toEntity(MetadadaDTO dto);

    @Override
    void updateFromDTO(@MappingTarget Metadada entity, MetadadaDTO dto);
}
