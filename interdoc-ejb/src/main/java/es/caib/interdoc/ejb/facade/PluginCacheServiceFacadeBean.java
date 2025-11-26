package es.caib.interdoc.ejb.facade;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Properties;

import javax.annotation.security.PermitAll;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import es.caib.interdoc.commons.i18n.I18NException;
import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.interceptor.ExceptionTranslate;
import es.caib.interdoc.ejb.interceptor.Logged;
import es.caib.interdoc.persistence.model.Plugin;
import es.caib.interdoc.service.facade.PluginCacheServiceFacade;


@Logged
@ExceptionTranslate
@Stateless
@Local(PluginArxiuServiceFacade.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public abstract class PluginCacheServiceFacadeBean<P> extends PluginServiceFacadeBean implements PluginCacheServiceFacade<P>{
    
    @Override
    @PermitAll
    public P getPlugin(Long idEntitat/*, Long tipusPlugin*/) throws Exception {

        try {
            //log.info("***** Accedit a getPlugin()");

            Plugin activePlugin = repository.findActiveByEntitatTipus(idEntitat, getTipusPlugin());

            if (activePlugin != null) {
                long pluginId = activePlugin.getId();
                P p = getPluginFromCache(pluginId);
                if(p == null) {
                    p = (P) carregarPlugin(activePlugin);
                    addPluginToCache(pluginId, p);
                }
                return p;
            }
            
        } catch (I18NException e) {
            // Re-llançar excepcions d'I18N (com l'error de múltiples plugins actius)
            throw e;
        } catch (Exception e) {
            throw new I18NException(e, "error.desconegut", e.getMessage());
        }

        return null;
    }
    
    
    protected abstract Long getTipusPlugin();
    
    
    @SuppressWarnings("unchecked")
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

   @SuppressWarnings("unchecked")
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
