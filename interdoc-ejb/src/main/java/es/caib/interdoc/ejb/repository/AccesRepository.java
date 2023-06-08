package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Acces;
import es.caib.interdoc.service.model.AccesAtribut;
import es.caib.interdoc.service.model.AccesDTO;
import es.caib.interdoc.service.model.Ordre;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interfície de les operacions bàsiques sobre aplicacions.
 *
 * @author jagarcia
 */
public interface AccesRepository extends CrudRepository<Acces, Long> {

    List<AccesDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                 Map<AccesAtribut, Object> filter,
                                                 List<Ordre<AccesAtribut>> ordenacio);

    long countByFilter(Map<AccesAtribut, Object> filter);

}
