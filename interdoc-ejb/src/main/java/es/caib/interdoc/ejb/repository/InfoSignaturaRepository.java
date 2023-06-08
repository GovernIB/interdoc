package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.InfoSignatura;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.InfoSignaturaAtribut;
import es.caib.interdoc.service.model.InfoSignaturaDTO;

import java.util.List;
import java.util.Map;

/**
 * Interfície de les operacions bàsiques sobre InfoSignatura.
 *
 * @author jagarcia
 */
public interface InfoSignaturaRepository extends CrudRepository<InfoSignatura, Long> {

    List<InfoSignaturaDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                 Map<InfoSignaturaAtribut, Object> filter,
                                                 List<Ordre<InfoSignaturaAtribut>> ordenacio);

    List<InfoSignatura> getAll();

    long countByFilter(Map<InfoSignaturaAtribut, Object> filter);

}
