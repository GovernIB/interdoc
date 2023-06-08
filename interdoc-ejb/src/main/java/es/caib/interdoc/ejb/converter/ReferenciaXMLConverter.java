package es.caib.interdoc.ejb.converter;

import es.caib.interdoc.persistence.model.ReferenciaXML;
import es.caib.interdoc.service.model.ReferenciaXMLDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversor entre Aplicacio i AplicacioDTO. La implementació es generarà automàticament per MapStruct
 *
 * @author jagarcia
 */
@Mapper
public interface ReferenciaXMLConverter extends Converter<ReferenciaXML, ReferenciaXMLDTO> {

    @Override
    ReferenciaXMLDTO toDTO(ReferenciaXML entity);

    @Override
    ReferenciaXML toEntity(ReferenciaXMLDTO dto);

    @Override
    void updateFromDTO(@MappingTarget ReferenciaXML entity, ReferenciaXMLDTO dto);
}
