package es.caib.interdoc.service.facade;

import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.Pagina;
import es.caib.interdoc.service.model.UsuariEntitatAtribut;
import es.caib.interdoc.service.model.UsuariEntitatDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Servei per els casos d'ús de mateniment d'un Usuari.
 *
 * @author jagarcia
 */
public interface UsuariEntitatServiceFacade {
	
	public static final String JNDI_NAME = "java:app/interdoc-ejb/UsuariEntitatServiceFacadeBean!es.caib.interdoc.service.facade.UsuariEntitatServiceFacade";

    /**
     * Crea un nou usuari a la base de dades.
     *
     * @param dto dades del usuari.
     * @return l'identificador del nou usuari
     * @throws AplicacioDuplicadaException si ja existeix un usuari amb el mateix identificador
     */
    Long create(UsuariEntitatDTO dto);

    /**
     * Actualitza les dades d'un usuari a la base de dades. 
     *
     * @param dto noves dades de la aplicació.
     * @throws RecursNoTrobatException si la aplicació amb identificador dto.id no existeix.
     */
    void update(UsuariEntitatDTO dto) throws RecursNoTrobatException;

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
    Optional<UsuariEntitatDTO> findById(Long usuariEntitatId);
    
    /**
     * Retorna un opcional l'usuari amb el username indicat.
     *
     * @param Codi d'usuari a cercar
     * @return un opcional amb les dades de l'usuari indicat o buid si no existeix.
     */
    Optional<UsuariEntitatDTO> findByUsuariId(Long usuariId);

    /**
     * Retorna tots els usuaris
     */
    List<UsuariEntitatDTO> getAll();

    /**
     * Retorna una pàgina d'usuaris que compleixen els filtres i les ordenacions indicades
     *
     * @param firstResult primer resultat del rang de la pàgina
     * @param maxResult   nombre d'elements màxim de la pàgina.
     * @param filter      filtres a aplicar
     * @param ordenacio   criteris d'ordenació
     * @return una pàgina amb el nombre d'usuaris que compleixen els filtres i la llista d'unitats pel rang indicat.
     */
    Pagina<UsuariEntitatDTO> findFiltered(int firstResult, int maxResult,
                                      Map<UsuariEntitatAtribut, Object> filter, List<Ordre<UsuariEntitatAtribut>> ordenacio);
    
}
