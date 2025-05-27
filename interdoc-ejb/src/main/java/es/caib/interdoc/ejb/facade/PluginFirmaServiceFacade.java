package es.caib.interdoc.ejb.facade;

import javax.ejb.Local;

import es.caib.interdoc.plugins.apifirmasimple.InterdocFirmaPlugin;
import es.caib.interdoc.service.facade.PluginCacheServiceFacade;

@Local
public interface PluginFirmaServiceFacade extends PluginCacheServiceFacade<InterdocFirmaPlugin>{

    public static final String JNDI_NAME = "java:app/interdoc-ejb/PluginFirmaServiceFacadeBean!es.caib.interdoc.ejb.facade.PluginFirmaServiceFacade";
    
}
