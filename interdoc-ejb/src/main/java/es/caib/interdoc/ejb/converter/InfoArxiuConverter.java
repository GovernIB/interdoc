package es.caib.interdoc.ejb.converter;

import es.caib.interdoc.persistence.model.InfoArxiu;
import es.caib.interdoc.service.model.InfoArxiuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre InfoArxiu i InfoArxiuDTO. La implementació es generarà automàticament per MapStruct
 *
 * @author jagarcia
 */
@Mapper
public interface InfoArxiuConverter extends Converter<InfoArxiu, InfoArxiuDTO> {

    @Override
    InfoArxiuDTO toDTO(InfoArxiu entity);

    @Override
    InfoArxiu toEntity(InfoArxiuDTO dto);

    @Override
    void updateFromDTO(@MappingTarget InfoArxiu entity, InfoArxiuDTO dto);
}
