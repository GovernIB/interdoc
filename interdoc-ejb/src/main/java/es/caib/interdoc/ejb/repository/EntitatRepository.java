package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Entitat;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.EntitatAtribut;
import es.caib.interdoc.service.model.EntitatDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interfície de les operacions bàsiques sobre entitats.
 *
 * @author jagarcia
 */
public interface EntitatRepository extends CrudRepository<Entitat, Long> {

    List<EntitatDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                 Map<EntitatAtribut, Object> filter,
                                                 List<Ordre<EntitatAtribut>> ordenacio);

    EntitatDTO findByCodiDir3(String codi);
    
    List<Entitat> getAll();

    long countByFilter(Map<EntitatAtribut, Object> filter);

}
