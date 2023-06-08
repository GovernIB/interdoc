package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.commons.i18n.I18NException;
import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.persistence.model.Plugin;
import es.caib.interdoc.persistence.model.Plugin_;
import es.caib.interdoc.service.model.PluginAtribut;
import es.caib.interdoc.service.model.PluginDTO;
import es.caib.interdoc.service.model.Ordre;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * Implementació del repositori de Plugins.
 *
 * @author areus
 */
@Stateless
@Local(PluginRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class PluginRepositoryBean extends AbstractCrudRepository<Plugin, Long>
        implements PluginRepository {

    protected PluginRepositoryBean() {
        super(Plugin.class);
    }

    
    @Override
    public List<PluginDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                        Map<PluginAtribut, Object> filter,
                                                        List<Ordre<PluginAtribut>> ordenacio) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PluginDTO> criteriaQuery = builder.createQuery(PluginDTO.class);
        Root<Plugin> root = criteriaQuery.from(Plugin.class);

        criteriaQuery.select(builder.construct(PluginDTO.class,
                root.get(Plugin_.id),
                root.get(Plugin_.nom),
                root.get(Plugin_.classe),
                root.get(Plugin_.propietats),
                root.get(Plugin_.dataCreacio),
                root.get(Plugin_.actiu)));

        PluginCriteriaHelper pluginCriteriaHelper = new PluginCriteriaHelper(builder, root);
        criteriaQuery.where(pluginCriteriaHelper.getPredicates(filter));
        criteriaQuery.orderBy(pluginCriteriaHelper.getOrderList(ordenacio));

        TypedQuery<PluginDTO> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public List<Plugin> getAll() {
        TypedQuery<Plugin> query = entityManager.createNamedQuery(Plugin.GET_ALL, Plugin.class);
        return query.getResultList();
    }

    @Override
    public long countByFilter(Map<PluginAtribut, Object> filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Plugin> root = criteriaQuery.from(Plugin.class);

        criteriaQuery.select(builder.count(root));

        PluginCriteriaHelper pluginCriteriaHelper = new PluginCriteriaHelper(builder, root);
        criteriaQuery.where(pluginCriteriaHelper.getPredicates(filter));

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
    
    @Override
    public Object getPlugin(Long id) throws I18NException {
    	
    	try {
    		
            Plugin p = findById(id);
            return carregarPlugin(p);
            
    	} catch (Exception e) {
            throw new I18NException(e, "error.desconegut", new String(e.getMessage()));
        }
    	
    }
    
    @Override
    public Properties getPropertiesPlugin(Long id) throws I18NException {
    	
    	try {
            Plugin p = findById(id);
            return carregarPropietats(p);

        } catch (Exception e) {
            throw new I18NException(e, "error.desconegut", new String(e.getMessage()));
        }

    }
    
    @Override
    public boolean existPlugin(Long id) throws I18NException {
    	
    	try {
            Plugin p = findById(id);
            return (p != null);
            
        } catch (Exception e) {
            throw new I18NException(e, "error.desconegut", new String(e.getMessage()));
        }
    	
    }
    
    @Override
    public List<Object> getPlugins(Long tipoPlugin) throws I18NException{
    	
    	TypedQuery<Plugin> query = entityManager.createNamedQuery(Plugin.GET_ALL, Plugin.class);
        List<Plugin> resultats = query.getResultList();

        if (resultats.size() > 0) {
            List<Object> pluginsCarregats = new ArrayList<Object>();
            for (Plugin plugin : resultats) {
            	pluginsCarregats.add(carregarPlugin(plugin));
            }
            return pluginsCarregats;
        }
        return null;
    }
    
    private Object carregarPlugin(Plugin plugin) throws I18NException {

        String BASE_PACKAGE = Constants.INTERDOC_PROPERTY_BASE;

        // Si no existe el plugin, retornamos null
        if (plugin == null) {
            log.info("No existe ningun plugin de este tipo definido en el sistema", new Exception());
            return null;
        }

        // Obtenemos la clase del Plugin
        String className = plugin.getClasse().trim();

        // Obtenemos sus propiedades
        Properties prop = new Properties();

        if (plugin.getPropietats() != null && plugin.getPropietats().trim().length() != 0) {
        	try {
                prop.load(new StringReader(plugin.getPropietats()));
            } catch (IOException e) {
                throw new I18NException("Plugin: Errror obteniendo las propiedades de la entidad");
            }
        }

        // Carregant la classe
        return org.fundaciobit.pluginsib.core.utils.PluginsManager.instancePluginByClassName(className, BASE_PACKAGE, prop);
    }
    
    
    private Properties carregarPropietats(Plugin plugin) throws I18NException {

        // Si no existe el plugin, retornamos null
        if (plugin == null) {
            log.info("No existe ningun plugin de este tipo definido en el sistema", new Exception());
            return null;
        }

        // Obtenemos sus propiedades
        Properties prop = new Properties();

        if (plugin.getPropietats() != null && plugin.getPropietats().trim().length() != 0) {
            try {
                prop.load(new StringReader(plugin.getPropietats()));
            } catch (IOException e) {
                throw new I18NException("Plugin: Error obteniendo las propiedades de la aplicación");
            }
        }

        return prop;
    }
    
}
