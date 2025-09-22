package es.caib.interdoc.ejb.converter;

import es.caib.interdoc.persistence.model.Usuari;
import es.caib.interdoc.service.model.UsuariDTO;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre Usuari i UsuariDTO. La implementació es generarà automàticament per MapStruct
 *
 * @author fbosch
 */
@Mapper
public interface UsuariConverter extends Converter<Usuari, UsuariDTO> {

    @Override
    UsuariDTO toDTO(Usuari entity);

    @Override
    Usuari toEntity(UsuariDTO dto);

    //@Mapping(target = "usuariId", ignore = true) // no volem que s'actualitzi
    @Override
    void updateFromDTO(@MappingTarget Usuari usuari, UsuariDTO dto);
}
