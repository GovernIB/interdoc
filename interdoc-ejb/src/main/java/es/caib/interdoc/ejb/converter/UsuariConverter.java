package es.caib.interdoc.ejb.converter;

import es.caib.interdoc.persistence.model.Usuari;
import es.caib.interdoc.service.model.UsuariDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre Aplicacio i AplicacioDTO. La implementació es generarà automàticament per MapStruct
 *
 * @author jagarcia
 */
@Mapper
public interface UsuariConverter extends Converter<Usuari, UsuariDTO> {

    @Override
    UsuariDTO toDTO(Usuari entity);

    @Override
    Usuari toEntity(UsuariDTO dto);

    @Override
    void updateFromDTO(@MappingTarget Usuari entity, UsuariDTO dto);
}
