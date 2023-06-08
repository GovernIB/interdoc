package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Fitxer;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.FitxerAtribut;
import es.caib.interdoc.service.model.FitxerDTO;

import java.util.List;
import java.util.Map;

/**
 * Interfície de les operacions bàsiques sobre entitats.
 *
 * @author jagarcia
 */
public interface FitxerRepository extends CrudRepository<Fitxer, Long> {

    List<FitxerDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                 Map<FitxerAtribut, Object> filter,
                                                 List<Ordre<FitxerAtribut>> ordenacio);

    List<Fitxer> getAll();

    long countByFilter(Map<FitxerAtribut, Object> filter);

}
