package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Referencia;
import es.caib.interdoc.service.model.ReferenciaAtribut;
import es.caib.interdoc.service.model.ReferenciaDTO;
import es.caib.interdoc.service.model.Ordre;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interfície de les operacions bàsiques sobre aplicacions.
 *
 * @author jagarcia
 */
public interface ReferenciaRepository extends CrudRepository<Referencia, Long> {

    List<ReferenciaDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                 Map<ReferenciaAtribut, Object> filter,
                                                 List<Ordre<ReferenciaAtribut>> ordenacio);
    
    List<ReferenciaDTO> findByReferencia(String referencia);
    
    List<ReferenciaDTO> findByUUID(String uuid);
    
    List<ReferenciaDTO> findByCSV(String csvId);
    
    List<Referencia> findBetweenDates(LocalDate inici, LocalDate fi);

    long countByFilter(Map<ReferenciaAtribut, Object> filter);

}
