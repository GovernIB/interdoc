package es.caib.interdoc.ejb.converter;

import es.caib.interdoc.persistence.model.Rol;
import es.caib.interdoc.service.model.RolDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre Aplicacio i AplicacioDTO. La implementació es generarà automàticament per MapStruct
 *
 * @author jagarcia
 */
@Mapper
public interface RolConverter extends Converter<Rol, RolDTO> {

    @Override
    RolDTO toDTO(Rol entity);

    @Override
    Rol toEntity(RolDTO dto);

    @Override
    void updateFromDTO(@MappingTarget Rol entity, RolDTO dto);
}
