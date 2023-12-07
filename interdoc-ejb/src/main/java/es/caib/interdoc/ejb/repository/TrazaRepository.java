package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Traza;
import es.caib.interdoc.service.model.TrazaAtribut;
import es.caib.interdoc.service.model.TrazaDTO;
import es.caib.interdoc.service.model.Ordre;

import java.util.List;
import java.util.Map;

/**
 * Interfície de les operacions bàsiques sobre aplicacions.
 *
 * @author jagarcia
 */
public interface TrazaRepository extends CrudRepository<Traza, Long> {

    List<TrazaDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                 Map<TrazaAtribut, Object> filter,
                                                 List<Ordre<TrazaAtribut>> ordenacio);

    long countByFilter(Map<TrazaAtribut, Object> filter);
    
    List<TrazaDTO> findByReferenciaId(Long referenciaId);

}
