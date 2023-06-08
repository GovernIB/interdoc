package es.caib.interdoc.service.facade;

import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.model.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Servei per els casos d'ús de peticions a tercers que es fan desde INTERDOC a altres sistemes.
 *
 * @author jagarcia
 */
public interface PeticioATercerServiceFacade {

    /**
     * Enregistra una nova peticio a la base de dades.
     *
     * @param dto dades de la petició.
     * @return l'identificador de la nova petició.
     */
    Long create(PeticioATercerDTO dto);

    /**
     * Actualitza les dades d'una petició a la base de dades. El codiDir3 no es sobreescriu.
     *
     * @param dto noves dades de la aplicació.
     * @throws RecursNoTrobatException si la aplicació amb identificador dto.id no existeix.
     */
    void update(PeticioATercerDTO dto) throws RecursNoTrobatException;


    /**
     * Actualitza l'estat d'una petició
     *
     * @param id
     * @param state
     */
    void updateState(Long id, EstatPublicacio state);


    /**
     * Retorna un opcional amb la peitició indicada per l'identificador.
     *
     * @param id identificador
     * @return un opcional amb les dades de la petició indicada o buid si no existeix.
     */
    Optional<PeticioATercerDTO> findById(Long id);

    /**
     * Retorna tots els registres d'aplicacions
     */
    List<PeticioATercerDTO> getAll(Integer filterByState);

    /**
     * Retorna una pàgina de peticions a tercers que compleixen els filtres i les ordenacions indicades
     *
     * @param firstResult primer resultat del rang de la pàgina
     * @param maxResult   nombre d'elements màxim de la pàgina.
     * @param filter      filtres a aplicar
     * @param ordenacio   criteris d'ordenació
     * @return una pàgina amb el nombre d'aplicacions que compleixen els filtres i la llista d'unitats pel rang indicat.
     */
    Pagina<PeticioATercerDTO> findFiltered(int firstResult, int maxResult,
                                           Map<PeticioATercerAtribut, Object> filter, List<Ordre<PeticioATercerAtribut>> ordenacio);
}
