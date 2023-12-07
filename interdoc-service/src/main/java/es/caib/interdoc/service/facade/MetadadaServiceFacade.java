package es.caib.interdoc.service.facade;

import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.Pagina;
import es.caib.interdoc.service.model.MetadadaAtribut;
import es.caib.interdoc.service.model.MetadadaDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Servei per els casos d'ús de mateniment d'una Aplicació.
 *
 * @author jagarcia
 */
public interface MetadadaServiceFacade {

	
	public static final String JNDI_NAME = "java:app/interdoc-ejb/MetadadaServiceFacadeBean!es.caib.interdoc.service.facade.MetadadaServiceFacade";

	
    /**
     * Crea una nova aplicació a la base de dades.
     *
     * @param dto dades de la aplicació.
     * @return l'identificador de la nova aplicació.
     */
    Long create(MetadadaDTO dto);

    /**
     * Actualitza les dades d'una aplicació a la base de dades. El codiDir3 no es sobreescriu.
     *
     * @param dto noves dades de la aplicació.
     * @throws RecursNoTrobatException si la aplicació amb identificador dto.id no existeix.
     */
    void update(MetadadaDTO dto) throws RecursNoTrobatException;

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
    Optional<MetadadaDTO> findById(Long id);
    
    /**
     * Retorna un opcional amb la llista de Metadades relacionades amb una referenciaId.
     * 
     * @param referenciaId
     * @return Optional<List<MetadadaDTO>>
     */
    Optional<List<MetadadaDTO>> findByReferenciaId(Long referenciaId);

    /**
     * Retorna una pàgina d'aplicacions que compleixen els filtres i les ordenacions indicades
     *
     * @param firstResult primer resultat del rang de la pàgina
     * @param maxResult   nombre d'elements màxim de la pàgina.
     * @param filter      filtres a aplicar
     * @param ordenacio   criteris d'ordenació
     * @return una pàgina amb el nombre d'aplicacions que compleixen els filtres i la llista d'unitats pel rang indicat.
     */
    Pagina<MetadadaDTO> findFiltered(int firstResult, int maxResult,
                                      Map<MetadadaAtribut, Object> filter, List<Ordre<MetadadaAtribut>> ordenacio);
}
