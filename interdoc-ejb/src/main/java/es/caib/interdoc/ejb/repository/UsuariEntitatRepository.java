package es.caib.interdoc.ejb.repository;


import es.caib.interdoc.persistence.model.UsuariEntitat;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.UsuariEntitatAtribut;
import es.caib.interdoc.service.model.UsuariEntitatDTO;

import java.util.List;
import java.util.Map;

/**
 * Interfície de les operacions bàsiques sobre entitats.
 *
 * @author jagarcia
 */
public interface UsuariEntitatRepository extends CrudRepository<UsuariEntitat, Long> {

    List<UsuariEntitatDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                 Map<UsuariEntitatAtribut, Object> filter,
                                                 List<Ordre<UsuariEntitatAtribut>> ordenacio);

    UsuariEntitatDTO findByUsuariId(Long usuariId);
    
    List<UsuariEntitat> getAll();

    long countByFilter(Map<UsuariEntitatAtribut, Object> filter);

}
