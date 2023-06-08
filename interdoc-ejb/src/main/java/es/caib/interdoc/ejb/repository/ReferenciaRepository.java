package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Referencia;
import es.caib.interdoc.service.model.ReferenciaAtribut;
import es.caib.interdoc.service.model.ReferenciaDTO;
import es.caib.interdoc.service.model.Ordre;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interfície de les operacions bàsiques sobre aplicacions.
 *
 * @author jagarcia
 */
public interface ReferenciaRepository extends CrudRepository<Referencia, Long> {

    List<ReferenciaDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                 Map<ReferenciaAtribut, Object> filter,
                                                 List<Ordre<ReferenciaAtribut>> ordenacio);
    
    List<ReferenciaDTO> findByReferencia(String referencia);
    
    List<ReferenciaDTO> findByUUID(String uuid);
    
    List<ReferenciaDTO> findByCSV(String csvId);

    long countByFilter(Map<ReferenciaAtribut, Object> filter);

}
