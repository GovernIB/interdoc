package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.ReferenciaXML;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.ReferenciaXMLAtribut;
import es.caib.interdoc.service.model.ReferenciaXMLDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interfície de les operacions bàsiques sobre aplicacions.
 *
 * @author jagarcia
 */
public interface ReferenciaXMLRepository extends CrudRepository<ReferenciaXML, Long> {

    List<ReferenciaXMLDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                 Map<ReferenciaXMLAtribut, Object> filter,
                                                 List<Ordre<ReferenciaXMLAtribut>> ordenacio);

    long countByFilter(Map<ReferenciaXMLAtribut, Object> filter);
    
    ReferenciaXMLDTO findByReferenciaId(Long referenciaId);

}
