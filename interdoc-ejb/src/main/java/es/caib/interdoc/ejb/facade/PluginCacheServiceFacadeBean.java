package es.caib.interdoc.ejb.facade;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.security.PermitAll;
import es.caib.interdoc.commons.i18n.I18NException;
import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.persistence.model.Plugin;
import es.caib.interdoc.service.facade.PluginCacheServiceFacade;


/*@Logged
@ExceptionTranslate
@Stateless
@Local(PluginArxiuFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)*/
public abstract class PluginCacheServiceFacadeBean<P> extends PluginServiceFacadeBean implements PluginCacheServiceFacade<P>{
    
    @Override
    @PermitAll
    public P getPlugin(Long idEntitat/*, Long tipusPlugin*/) throws Exception {

        try {
            //log.info("***** Accedit a getPlugin()");

            List<Plugin> plugins = repository.findByEntitatTipus(idEntitat, getTipusPlugin());

            if (plugins.size() > 0) {
                long pluginId = plugins.get(0).getId();
                P p = getPluginFromCache(plugins.get(0).getId());
                if(p==null) {
                    p = (P) carregarPlugin(plugins.get(0));
                    addPluginToCache(pluginId, p);
                    return carregarPlugin(plugins.get(0));
                }
                return p;
            }
            
        } catch (Exception e) {
            throw new I18NException(e, "error.desconegut", e.getMessage());
        }

        return null;
    }
    
    
    protected abstract Long getTipusPlugin();
    
    
    private P carregarPlugin(Plugin plugin) throws Exception {
        
        String BASE_PACKAGE = Constants.INTERDOC_PROPERTY_BASE;
        
       // Si no existe el plugin, retornamos null
        if (plugin == null) {
            return null;
        }
        
        String className = plugin.getClasse().trim();
        
        Properties prop = new Properties();
        
        if (plugin.getPropietats() != null && plugin.getPropietats().trim().length() > 0) {
            prop.load(new StringReader(plugin.getPropietats()));
        }
        
        return (P) org.fundaciobit.pluginsib.core.v3.utils.PluginsManager.instancePluginByClassName(className, BASE_PACKAGE, prop);
   }
    
    
    public void addPluginToCache(Long pluginID, P pluginInstance) {
        log.info("CACHE: addPluginToCache() Id="+pluginID);
        synchronized (pluginsCache) {
          pluginsCache.put(pluginID, pluginInstance);  
        }
    }

   public P getPluginFromCache(Long pluginID) {
       log.info("CACHE: getPluginFromCache() Id="+pluginID);
        synchronized (pluginsCache) {
          return  (P) pluginsCache.get(pluginID);  
        }
    }
   
    
    public void clearCache() {
      log.info("CACHE: clearCache()");
      synchronized (pluginsCache) {
        pluginsCache.clear();
      }
    }
    
}
