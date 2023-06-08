package es.caib.interdoc.service.facade;

import es.caib.interdoc.service.exception.AplicacioDuplicadaException;
import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.model.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Servei per els casos d'ús de mateniment d'una Aplicació.
 *
 * @author jagarcia
 */
public interface UsuariServiceFacade {

    String JNDI_NAME = "java:app/interdoc/service/UsuariServiceFacadeBean";
    // java:app/interdoc-ejb/UsuariServiceFacadeBean!es.caib.interdoc.service.facade.UsuariServiceFacade

    /**
     * Crea una nova aplicació a la base de dades.
     *
     * @param dto dades de la aplicació.
     * @return l'identificador de la nova aplicació.
     */
    Long create(UsuariDTO dto);

    /**
     * Actualitza les dades d'una aplicació a la base de dades. El codiDir3 no es sobreescriu.
     *
     * @param dto noves dades de la aplicació.
     * @throws RecursNoTrobatException si la aplicació amb identificador dto.id no existeix.
     */
    void update(UsuariDTO dto) throws RecursNoTrobatException;

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
    Optional<UsuariDTO> findById(Long id);

    Optional<UsuariDTO> findByIdentificador(String identificador) throws Exception;

    Optional<UsuariDTO> findByDocument(String document) throws Exception;




    /**
     * Retorna tots els registres d'aplicacions
     */
    List<UsuariDTO> getAll();

    /**
     * Retorna una pàgina d'aplicacions que compleixen els filtres i les ordenacions indicades
     *
     * @param firstResult primer resultat del rang de la pàgina
     * @param maxResult   nombre d'elements màxim de la pàgina.
     * @return una pàgina amb el nombre d'aplicacions que compleixen els filtres i la llista d'unitats pel rang indicat.
     */
    Pagina<UsuariDTO> findFiltered(int firstResult, int maxResult);

    Pagina<UsuariDTO> busqueda(Integer pageNumber, String identificador, String nombre, String apellido1, String apellido2, String documento, Long tipoUsuario) throws Exception;

    Integer asociarIdioma() throws Exception;
    void actualizarRoles(UsuariDTO usuario, List<RolDTO> rolesUsuario) throws Exception;

    List<RolDTO> getByRol(List<String> roles) throws Exception;

}
