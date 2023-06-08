package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.PeticioATercer;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.PeticioATercerAtribut;
import es.caib.interdoc.service.model.PeticioATercerDTO;

import java.util.List;
import java.util.Map;

/**
 * Interfície de les operacions bàsiques sobre peticions a tecers.
 *
 * @author jagarcia
 */

public interface PeticioATercerRepository extends CrudRepository<PeticioATercer, Long> {

    List<PeticioATercerDTO> getAll(Integer state);

    List<PeticioATercerDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                      Map<PeticioATercerAtribut, Object> filter, List<Ordre<PeticioATercerAtribut>> ordenacio);

    long countByFilter(Map<PeticioATercerAtribut, Object> filter);

}
