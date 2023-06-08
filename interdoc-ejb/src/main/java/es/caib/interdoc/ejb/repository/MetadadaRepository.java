package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Metadada;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.MetadadaAtribut;
import es.caib.interdoc.service.model.MetadadaDTO;

import java.util.List;
import java.util.Map;

/**
 * Interfície de les operacions bàsiques sobre aplicacions.
 *
 * @author jagarcia
 */
public interface MetadadaRepository extends CrudRepository<Metadada, Long> {

    List<MetadadaDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                 Map<MetadadaAtribut, Object> filter,
                                                 List<Ordre<MetadadaAtribut>> ordenacio);

    long countByFilter(Map<MetadadaAtribut, Object> filter);

}
