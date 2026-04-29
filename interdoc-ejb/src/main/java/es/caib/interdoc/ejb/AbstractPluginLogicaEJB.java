package es.caib.interdoc.ejb;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;


import org.fundaciobit.pluginsib.core.v3.utils.PluginsManager;

import es.caib.interdoc.commons.i18n.I18NException;
import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.ejb.facade.PluginServiceFacadeBean;
import es.caib.interdoc.persistence.model.Log;
import es.caib.interdoc.service.model.Estat;
import es.caib.interdoc.service.model.PluginDTO;

import org.fundaciobit.pluginsib.core.v3.IPluginIB;


/**
 *
 * @author anadal
 *
 */
public abstract class AbstractPluginLogicaEJB<I extends IPluginIB> extends PluginServiceFacadeBean
        implements AbstractPluginLogicaService<I> {

    public abstract Long getType();

    protected abstract String getName();

    @Override
    public List<PluginDTO> getAllPlugins() throws I18NException {
        List<PluginDTO> allPlugins = this.getByTipus(getType());
        List<PluginDTO> activePlugins = new ArrayList<PluginDTO>();
        for(PluginDTO p : allPlugins) {
            if(p.getActiu().equals(Estat.ACTIU)) {
           	  activePlugins.add(p);
            }
        }
        return activePlugins;
    }

    @Override
    public PluginDTO getPluginByEntity(Long entityId) throws I18NException {
        log.info("- getPluginByEntity(): EntityId = " + entityId);
        List<PluginDTO> allPlugins = getAllPlugins();
        if(allPlugins.isEmpty()) {
            log.info("- No plugins found for type " + getType());
            return null;
        }else{
            log.info("- Plugins found for type " + getType() + ":");
            for(PluginDTO p : allPlugins) {
                log.info("  - Plugin ID: " + p.getId() + ", Name: " + p.getNom() + ", Entity ID: " + p.getEntitatId());
            }
        }
        for(PluginDTO p : allPlugins) {
            if(p.getEntitatId() != null && p.getEntitatId().equals(entityId)){
           	  return p;
            }
        }
        return null;
        
    }

    

    @Override
    public I getInstanceByPluginID(long pluginID) throws I18NException {

        IPluginIB pluginInstance = null;
        
        Optional<PluginDTO> pluginOpt = (Optional<PluginDTO>) this.findById(pluginID);

        if (pluginOpt == null || !pluginOpt.isPresent()) {
            return null;
        }

        PluginDTO plugin = pluginOpt.get();
        Properties prop = new Properties();
        if (plugin.getPropietats() != null && plugin.getPropietats().trim().length() != 0) {
            try {

                // Exemple:
                // [=SP["es.caib.digitalib.plugins.signatureserver.afirmaserver.authorization.password"]]

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("SP", Configuracio.getSystemAndFileProperties());

                String plantilla = plugin.getPropietats();
                /*String generat = TemplateEngine.processExpressionLanguageSquareBrackets(plantilla, map,
                        new Locale("ca"));*/

                final String generat = plantilla;
                // log.error("PROPIETATS DESPRES DE generat:\n" + generat + "\n");

                prop.load(new StringReader(generat));
                

            } catch (Exception e) {
                throw new I18NException(e,"Error desconegut processant propietats del plugin " + pluginID + ": " + e.getMessage());
                
            }
        }
        
        log.info("\n - Instantiating plugin with class name: ]" + plugin.getClasse()+"[ \n");
        
        pluginInstance = (IPluginIB) PluginsManager.instancePluginByClassName(plugin.getClasse(),
                Constants.INTERDOC_PROPERTY_BASE, prop);

        if (pluginInstance == null) {
            throw new I18NException("error.plugin.donotinstantiate", getName() + " (" + plugin.getClasse() + ")");
        }
        return (I) pluginInstance;
    }
}
