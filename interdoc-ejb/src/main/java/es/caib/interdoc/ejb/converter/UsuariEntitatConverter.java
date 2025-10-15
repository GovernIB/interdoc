package es.caib.interdoc.ejb.converter;



import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import es.caib.interdoc.persistence.model.UsuariEntitat;
import es.caib.interdoc.service.model.UsuariEntitatDTO;

/**
 * Conversor entre Usuari i UsuariDTO. La implementació es generarà automàticament per MapStruct
 *
 * @author fbosch
 */
@Mapper
public interface UsuariEntitatConverter extends Converter<UsuariEntitat, UsuariEntitatDTO> {

    @Override
    UsuariEntitatDTO toDTO(UsuariEntitat entity);

    @Override
    UsuariEntitat toEntity(UsuariEntitatDTO dto);

    //@Mapping(target = "usuariId", ignore = true) // no volem que s'actualitzi
    @Override
    void updateFromDTO(@MappingTarget UsuariEntitat usuariEntitat, UsuariEntitatDTO dto);
}
