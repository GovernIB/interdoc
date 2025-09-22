package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Usuari;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.UsuariAtribut;
import es.caib.interdoc.service.model.UsuariDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interfície de les operacions bàsiques sobre entitats.
 *
 * @author jagarcia
 */
public interface UsuariRepository extends CrudRepository<Usuari, Long> {

    List<UsuariDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                 Map<UsuariAtribut, Object> filter,
                                                 List<Ordre<UsuariAtribut>> ordenacio);

    UsuariDTO findByUsername(String username);
    
    List<Usuari> getAll();

    long countByFilter(Map<UsuariAtribut, Object> filter);

}
