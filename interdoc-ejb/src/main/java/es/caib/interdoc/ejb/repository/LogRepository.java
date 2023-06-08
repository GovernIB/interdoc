package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Log;
import es.caib.interdoc.service.model.LogAtribut;
import es.caib.interdoc.service.model.LogDTO;
import es.caib.interdoc.service.model.Ordre;

import java.util.List;
import java.util.Map;

/**
 * Interfície de les operacions bàsiques sobre logs.
 *
 * @author jagarcia
 */
public interface LogRepository extends CrudRepository<Log, Long> {

    List<LogDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                 Map<LogAtribut, Object> filter,
                                                 List<Ordre<LogAtribut>> ordenacio);

    long countByFilter(Map<LogAtribut, Object> filter);

}
