package es.caib.interdoc.service.facade;

import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.model.FitxerAtribut;
import es.caib.interdoc.service.model.FitxerDTO;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.Pagina;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Servei per els casos d'ús de mateniment d'una Fitxer.
 *
 * @author jagarcia
 */
public interface FitxerServiceFacade {
	
	public static final String JNDI_NAME = "java:app/interdoc-ejb/FitxerServiceFacadeBean!es.caib.interdoc.service.facade.FitxerServiceFacade";

    /**
     * Crea una nou fitxer a la base de dades.
     *
     * @param dto dades del fitxer.
     * @return l'identificador del fitxer.
     */
    Long create(FitxerDTO dto);

    /**
     * Actualitza les dades d'una entitat a la base de dades. 
     *
     * @param dto noves dades de la aplicació.
     * @throws RecursNoTrobatException si la aplicació amb identificador dto.id no existeix.
     */
    void update(FitxerDTO dto) throws RecursNoTrobatException;

    /**
     * Esborra una entitat de la base de dades.
     *
     * @param id identificador de la entitat a esborrar
     * @throws RecursNoTrobatException si la entitat amb identificador id no existeix.
     */
    void delete(Long id) throws RecursNoTrobatException;

    /**
     * Retorna un opcional amb la entitat indicada per l'identificador.
     *
     * @param id identificador de la entitat a cercar
     * @return un opcional amb les dades de la entitat indicada o buid si no existeix.
     */
    Optional<FitxerDTO> findById(Long id);
    

    /**
     * Retorna tots els registres d'aplicacions
     */
    List<FitxerDTO> getAll();

    /**
     * Retorna una pàgina d'aplicacions que compleixen els filtres i les ordenacions indicades
     *
     * @param firstResult primer resultat del rang de la pàgina
     * @param maxResult   nombre d'elements màxim de la pàgina.
     * @param filter      filtres a aplicar
     * @param ordenacio   criteris d'ordenació
     * @return una pàgina amb el nombre d'aplicacions que compleixen els filtres i la llista d'unitats pel rang indicat.
     */
    Pagina<FitxerDTO> findFiltered(int firstResult, int maxResult,
                                      Map<FitxerAtribut, Object> filter, List<Ordre<FitxerAtribut>> ordenacio);
    
}
