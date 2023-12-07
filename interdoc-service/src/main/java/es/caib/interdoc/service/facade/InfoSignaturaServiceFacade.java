package es.caib.interdoc.service.facade;

import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.model.InfoSignaturaAtribut;
import es.caib.interdoc.service.model.InfoSignaturaDTO;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.Pagina;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Servei per els casos d'ús de mateniment d'una informació de signatura.
 *
 * @author jagarcia
 */
public interface InfoSignaturaServiceFacade {
	
	public static final String JNDI_NAME = "java:app/interdoc-ejb/InfoSignaturaServiceFacadeBean!es.caib.interdoc.service.facade.InfoSignaturaServiceFacade";

    /**
     * Crea una nova aplicació a la base de dades.
     *
     * @param dto dades de la aplicació.
     * @return l'identificador de la nova aplicació.
     * @throws AplicacioDuplicadaException si ja existeix una aplicacio amb el mateix codiDir3
     */
    Long create(InfoSignaturaDTO dto);

    /**
     * @param dto noves dades de la aplicació.
     * @throws RecursNoTrobatException si la aplicació amb identificador dto.id no existeix.
     */
    void update(InfoSignaturaDTO dto) throws RecursNoTrobatException;

    /**
     * Esborra una aplicació de la base de dades.
     *
     * @param id identificador de la aplicació a esborrar
     * @throws RecursNoTrobatException si la aplicació amb identificador id no existeix.
     */
    void delete(Long id) throws RecursNoTrobatException;

    /**
     * Retorna un opcional amb la aplicació indicada per l'identificador.
     *
     * @param id identificador de la aplicació a cercar
     * @return un opcional amb les dades de la aplicació indicada o buid si no existeix.
     */
    Optional<InfoSignaturaDTO> findById(Long id);
    
    /**
     * Retorna tots els registres d'aplicacions
     */
    List<InfoSignaturaDTO> getAll();

    /**
     * Retorna una pàgina d'aplicacions que compleixen els filtres i les ordenacions indicades
     *
     * @param firstResult primer resultat del rang de la pàgina
     * @param maxResult   nombre d'elements màxim de la pàgina.
     * @param filter      filtres a aplicar
     * @param ordenacio   criteris d'ordenació
     * @return una pàgina amb el nombre d'aplicacions que compleixen els filtres i la llista d'unitats pel rang indicat.
     */
    Pagina<InfoSignaturaDTO> findFiltered(int firstResult, int maxResult,
                                      Map<InfoSignaturaAtribut, Object> filter, List<Ordre<InfoSignaturaAtribut>> ordenacio);
    
    
}
