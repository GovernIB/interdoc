package es.caib.interdoc.ejb.facade;

import es.caib.interdoc.commons.i18n.I18NException;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

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
    
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
    protected final Map<Long, Object> pluginsCache = new HashMap<Long, Object>();


	
    @Inject
    protected PluginRepository repository;

    @Inject
    private PluginConverter converter;

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public Long create(PluginDTO dto) throws I18NException {
        // Validar que si s'està creant com actiu, no hi ha un altre plugin actiu del mateix tipus i entitat
        if (dto.getActiu() == es.caib.interdoc.service.model.Estat.ACTIU) {
            try {
                Plugin existingActive = repository.findActiveByEntitatTipus(dto.getEntitatId(), dto.getTipus());
                if (existingActive != null) {
                    throw new I18NException(
                        "error.plugin.ja_existeix_actiu",
                        "Ja existeix un altre plugin actiu d'aquest tipus per aquesta entitat. Desactiva'l primer."
                    );
                }
            } catch (I18NException e) {
                // Re-llançar l'excepció d'I18N
                throw e;
            }
        }
        
        Plugin plugin = converter.toEntity(dto);
        repository.create(plugin);
        return plugin.getId();
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void update(PluginDTO dto) throws RecursNoTrobatException, I18NException {
        if(dto != null) {
            deleteOfCache(dto.getId());
            Plugin plugin = repository.getReference(dto.getId());
            
            // Validar que si s'està activant, no hi ha un altre plugin actiu del mateix tipus i entitat
            if (dto.getActiu() == es.caib.interdoc.service.model.Estat.ACTIU) {
                try {
                    Plugin existingActive = repository.findActiveByEntitatTipus(dto.getEntitatId(), dto.getTipus());
                    if (existingActive != null && !existingActive.getId().equals(dto.getId())) {
                        throw new I18NException(
                            "error.plugin.ja_existeix_actiu",
                            "Ja existeix un altre plugin actiu d'aquest tipus per aquesta entitat. Desactiva'l primer."
                        );
                    }
                } catch (I18NException e) {
                    // Re-llançar l'excepció d'I18N
                    throw e;
                }
            }
            
            converter.updateFromDTO(plugin, dto);
        }
    }

    @Override
    @RolesAllowed(Constants.ITD_ADMIN)
    public void delete(Long id) throws RecursNoTrobatException {
        log.info("***** Accedit a delete()");
        deleteOfCache(id);
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
    public List<PluginDTO> getByTipus(Long tipus, Long entitatId){
    	List<PluginDTO> llistaPlugins = new ArrayList<PluginDTO>();
    	
    	List<Plugin> plugins = repository.getByTipus(tipus, entitatId);
    	for(Plugin p : plugins) {
    		llistaPlugins.add(converter.toDTO(p));
    	}
    	return llistaPlugins;
    }
    

    @Override
    @PermitAll
    public Pagina<PluginDTO> findFiltered(int firstResult, int maxResult, Map<PluginAtribut, Object> filter,
                                             List<Ordre<PluginAtribut>> ordenacio) {

        List<PluginDTO> items = repository.findPagedByFilterAndOrder(firstResult, maxResult, filter, ordenacio);
        long total = repository.countByFilter(filter);

        return new Pagina<>(items, total);
    }

	

	@Override
	@PermitAll
	public Properties getPropertiesPlugin(Long idEntitat, Long tipusPlugin) throws Exception {
		
		try {
            List<Plugin> plugins;
            plugins = repository.findByEntitatTipus(idEntitat, tipusPlugin);

            if (plugins.size() > 0) {
                return carregarPropietats(plugins.get(0));
            }
        } catch (Exception e) {
            throw new I18NException(e, "error.desconegut", e.getMessage());
        }
		return null;
	}
    
    
    
    private Properties carregarPropietats(Plugin plugin) throws Exception {
    	
    	if (plugin == null) {
            return null;
        }
    
    	Properties prop = new Properties();
    	
    	if (plugin.getPropietats() != null && plugin.getPropietats().trim().length() > 0) {
       	 prop.load(new StringReader(plugin.getPropietats()));
        }
    	
    	return prop;
    }
    
    
    public boolean deleteOfCache(Long pluginID) {
        log.info("***** Accedit a deleteOfCache()");
        synchronized (pluginsCache) {
            Object p = pluginsCache.remove(pluginID);
            return p != null;
          }
    }

    
    
    
    
    
}
