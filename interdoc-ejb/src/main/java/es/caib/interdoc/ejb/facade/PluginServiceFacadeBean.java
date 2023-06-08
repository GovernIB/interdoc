package es.caib.interdoc.ejb.facade;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.converter.PluginConverter;
import es.caib.interdoc.ejb.interceptor.ExceptionTranslate;
import es.caib.interdoc.ejb.interceptor.Logged;
import es.caib.interdoc.ejb.repository.PluginRepository;
import es.caib.interdoc.persistence.model.Plugin;
import es.caib.interdoc.service.exception.RecursNoTrobatException;
import es.caib.interdoc.service.facade.PluginServiceFacade;
import es.caib.interdoc.service.model.PluginAtribut;
import es.caib.interdoc.service.model.PluginDTO;
import es.caib.interdoc.service.model.Ordre;
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
@Local(PluginServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PluginServiceFacadeBean implements PluginServiceFacade {

    @Inject
    private PluginRepository repository;

    @Inject
    private PluginConverter converter;

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public Long create(PluginDTO dto){
        Plugin plugin = converter.toEntity(dto);
        repository.create(plugin);
        return plugin.getId();
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void update(PluginDTO dto) throws RecursNoTrobatException {
    	Plugin plugin = repository.getReference(dto.getId());
        converter.updateFromDTO(plugin, dto);
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void delete(Long id) throws RecursNoTrobatException {
    	Plugin plugin = repository.getReference(id);
        repository.delete(plugin);
    }

    @Override
    @RolesAllowed({Constants.ITD_USER, Constants.ITD_ADMIN})
    public Optional<PluginDTO> findById(Long id) {
    	Plugin plugin = repository.findById(id);
    	PluginDTO dto = converter.toDTO(plugin);
        return Optional.ofNullable(dto);
    }

    @Override
    @PermitAll
    public List<PluginDTO> getAll() {
        List<PluginDTO> items = new ArrayList<PluginDTO>();
        List<Plugin> llista = repository.getAll();
        for (Plugin a : llista ) {
            items.add(converter.toDTO(a));
        }
        return items;
    }

    @Override
    @PermitAll
    public Pagina<PluginDTO> findFiltered(int firstResult, int maxResult, Map<PluginAtribut, Object> filter,
                                             List<Ordre<PluginAtribut>> ordenacio) {

        List<PluginDTO> items = repository.findPagedByFilterAndOrder(firstResult, maxResult, filter, ordenacio);
        long total = repository.countByFilter(filter);

        return new Pagina<>(items, total);
    }
    
}
