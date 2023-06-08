package es.caib.interdoc.ejb.converter;

import es.caib.interdoc.persistence.model.Plugin;
import es.caib.interdoc.service.model.PluginDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre Plugin i PluginDTO. La implementació es generarà automàticament per MapStruct
 *
 * @author jagarcia
 */
@Mapper
public interface PluginConverter extends Converter<Plugin, PluginDTO> {

    @Override
    PluginDTO toDTO(Plugin entity);

    @Override
    Plugin toEntity(PluginDTO dto);

    //@Mapping(target = "codiDir3", ignore = true) // no volem que s'actualitzi
    @Override
    void updateFromDTO(@MappingTarget Plugin entity, PluginDTO dto);
}
