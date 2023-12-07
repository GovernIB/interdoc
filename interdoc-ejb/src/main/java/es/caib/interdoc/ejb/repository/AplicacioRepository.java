package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Aplicacio;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.AplicacioAtribut;
import es.caib.interdoc.service.model.AplicacioDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interfície de les operacions bàsiques sobre aplicacions.
 *
 * @author jagarcia
 */
public interface AplicacioRepository extends CrudRepository<Aplicacio, Long> {

    Optional<Aplicacio> findByCodiDir3(String codiDir3);
    
    Optional<Aplicacio> findByUserName(String username);

    List<AplicacioDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                 Map<AplicacioAtribut, Object> filter,
                                                 List<Ordre<AplicacioAtribut>> ordenacio);

    List<Aplicacio> getAll();

    long countByFilter(Map<AplicacioAtribut, Object> filter);

}
