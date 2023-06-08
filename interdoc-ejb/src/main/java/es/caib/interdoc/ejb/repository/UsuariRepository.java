package es.caib.interdoc.ejb.repository;


import es.caib.interdoc.persistence.model.Usuari;
import es.caib.interdoc.service.model.RolDTO;
import es.caib.interdoc.service.model.UsuariDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interfície de les operacions bàsiques sobre aplicacions.
 *
 * @author jagarcia
 */
public interface UsuariRepository extends CrudRepository<Usuari, Long> {

    List<UsuariDTO> findPagedByFilterAndOrder(int firstResult, int maxResult);

    List<UsuariDTO> getAll();

    Optional<UsuariDTO> findByIdentificador(String identificador);

    Optional<UsuariDTO> findByDocument(String document);

    List<UsuariDTO> busqueda(Integer pageNumber, String identificador, String nombre, String apellido1, String apellido2, String documento, Long tipoUsuario);

    Integer asociarIdioma();

    void actualizarRoles(Usuari usuario, List<RolDTO> rolesUsuario);

    public List<RolDTO> getByRol(List<String> roles) throws Exception;

}
