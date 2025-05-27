package es.caib.interdoc.ejb.facade;

import javax.ejb.Local;

import es.caib.interdoc.plugins.arxiu.InterdocArxiuPlugin;
import es.caib.interdoc.service.facade.PluginCacheServiceFacade;

@Local
public interface PluginArxiuServiceFacade extends PluginCacheServiceFacade<InterdocArxiuPlugin>{

    public static final String JNDI_NAME = "java:app/interdoc-ejb/PluginArxiuServiceFacadeBean!es.caib.interdoc.ejb.facade.PluginArxiuServiceFacade";
    
}
