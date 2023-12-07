package es.caib.interdoc.service.facade;

import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.model.AccesAtribut;
import es.caib.interdoc.service.model.AccesDTO;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.Pagina;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Gestió de les dades d'accés a una referència
 * @author jagarcia
 */
public interface AccesServiceFacade {
	
	public static final String JNDI_NAME = "java:app/interdoc-ejb/AccesServiceFacadeBean!es.caib.interdoc.service.facade.AccesServiceFacade";

    /**
     * Enregistrar un nou accés a la base de dades.
     *
     * @param dto dades d'accés.
     * @return l'identificador del nou registre.
     */
    Long create(AccesDTO dto);

    /**
     * Esborra una aplicació de la base de dades.
     *
     * @param id identificador de la aplicació a esborrar
     * @throws RecursNoTrobatException si la aplicació amb identificador id no existeix.
     */
    void delete(Long id) throws RecursNoTrobatException;

    /**
     * Retorna un opcional amb les dades de l'accés indicat per l'identificador.
     *
     * @param id identificador
     * @return un opcional amb les dades del accés o buid si no existeix.
     */
    Optional<AccesDTO> findById(Long id);
    
    /**
     * Retorna un opcional amb les dades dels accesos que s'han fer a la referencia indicada per paràmetre
     * @param referenciaId
     * @return un opcional amb les dades dels accesos o buid si no existeixen
     */
    Optional<List<AccesDTO>> findByRefenciaId(Long referenciaId);

    /**
     * Retorna una pàgina d'accesos que compleixen els filtres i les ordenacions indicades
     *
     * @param firstResult primer resultat del rang de la pàgina
     * @param maxResult   nombre d'elements màxim de la pàgina.
     * @param filter      filtres a aplicar
     * @param ordenacio   criteris d'ordenació
     * @return una pàgina amb el nombre d'accesos que compleixen els filtres i la llista d'unitats pel rang indicat.
     */
    Pagina<AccesDTO> findFiltered(int firstResult, int maxResult,
                                      Map<AccesAtribut, Object> filter, List<Ordre<AccesAtribut>> ordenacio);
}
