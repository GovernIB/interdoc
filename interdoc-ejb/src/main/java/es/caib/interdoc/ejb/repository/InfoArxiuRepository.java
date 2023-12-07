package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.InfoArxiu;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.InfoArxiuAtribut;
import es.caib.interdoc.service.model.InfoArxiuDTO;

import java.util.List;
import java.util.Map;

/**
 * Interfície de les operacions bàsiques sobre infoArxiu.
 *
 * @author jagarcia
 */
public interface InfoArxiuRepository extends CrudRepository<InfoArxiu, Long> {

    List<InfoArxiuDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                 Map<InfoArxiuAtribut, Object> filter,
                                                 List<Ordre<InfoArxiuAtribut>> ordenacio);

    List<InfoArxiu> getAll();
    
    List<InfoArxiuDTO> getExpedientsOberts(String estat);

    long countByFilter(Map<InfoArxiuAtribut, Object> filter);

}
