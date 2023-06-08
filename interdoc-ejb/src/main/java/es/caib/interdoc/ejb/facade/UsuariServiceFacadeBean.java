package es.caib.interdoc.ejb.facade;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.converter.UsuariConverter;
import es.caib.interdoc.ejb.interceptor.ExceptionTranslate;
import es.caib.interdoc.ejb.interceptor.Logged;
import es.caib.interdoc.ejb.repository.UsuariRepository;
import es.caib.interdoc.persistence.model.Usuari;
import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.facade.UsuariServiceFacade;
import es.caib.interdoc.service.model.RolDTO;
import es.caib.interdoc.service.model.UsuariDTO;
import es.caib.interdoc.service.model.Pagina;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementació dels casos d'ús de manteniment de aplicacions.
 * És responsabilitat d'aquesta capa definir el limit de les transaccions i la seguretat.
 * Les excepcions específiques es llancen mitjançant l'{@link ExceptionTranslate} que transforma
 * els errors JPA amb les excepcions de servei com la {@link RecursNoTrobatException}
 *
 * @author jagarcia
 */
@Logged
@ExceptionTranslate
@Stateless
@Local(UsuariServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UsuariServiceFacadeBean implements UsuariServiceFacade {

    @Inject
    private UsuariRepository repository;

    @Inject
    private UsuariConverter converter;

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public Long create(UsuariDTO dto) {

        Usuari usuari = converter.toEntity(dto);
        repository.create(usuari);
        return usuari.getId();
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void update(UsuariDTO dto) throws RecursNoTrobatException {
        Usuari item = repository.getReference(dto.getId());
        converter.updateFromDTO(item, dto);
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void delete(Long id) throws RecursNoTrobatException {
        Usuari item = repository.getReference(id);
        repository.delete(item);
    }

    @Override
    @RolesAllowed({Constants.ITD_USER, Constants.ITD_ADMIN})
    public Optional<UsuariDTO> findById(Long id) {
        Usuari item = repository.findById(id);
        UsuariDTO itemDTO = converter.toDTO(item);
        return Optional.ofNullable(itemDTO);
    }

    @Override
    @PermitAll
    public List<UsuariDTO> getAll() {
        List<UsuariDTO> items = new ArrayList<UsuariDTO>();
        items = repository.getAll();
        return items;
    }

    @Override
    @PermitAll
    public Pagina<UsuariDTO> findFiltered(int firstResult, int maxResult) {

        List<UsuariDTO> items = repository.findPagedByFilterAndOrder(firstResult, maxResult);
        return new Pagina<>(items, items.size());
    }

    @Override
    public Optional<UsuariDTO> findByIdentificador(String identificador) throws Exception {
        return repository.findByIdentificador(identificador);
    }

    @Override
    public Optional<UsuariDTO> findByDocument(String document) throws Exception {
        return repository.findByDocument(document);
    }

    @Override
    public Pagina<UsuariDTO> busqueda(Integer pageNumber, String identificador, String nombre, String apellido1, String apellido2, String documento, Long tipoUsuario) throws Exception {

        List<UsuariDTO> resultats = repository.busqueda(pageNumber, identificador, nombre, apellido1, apellido2, documento, tipoUsuario);
        return new Pagina<>(resultats, resultats.size());
    }

    @Override
    public Integer asociarIdioma() throws Exception {
        return repository.asociarIdioma();
    }

    @Override
    public void actualizarRoles(UsuariDTO usuario, List<RolDTO> rolesUsuario) throws Exception {
        repository.actualizarRoles(converter.toEntity(usuario), rolesUsuario);
    }

    @Override
    public List<RolDTO> getByRol(List<String> roles) throws Exception {
        return repository.getByRol(roles);
    }
}
