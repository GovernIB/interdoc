package es.caib.interdoc.service.facade;



/**
 * Servei per els casos d'ús de mateniment d'una Aplicació.
 *
 * @author jagarcia
 */
public interface PluginCacheServiceFacade<P> extends PluginServiceFacade {
	
	//public static final String JNDI_NAME = "java:app/interdoc-ejb/PluginServiceFacadeBean!es.caib.interdoc.service.facade.PluginServiceFacade";

	/**
     * Retorna un plugin determinat
     *
     * @param idEntidad
     * @param tipusPlugin
     * @return
     * @throws Exception
     */
    P getPlugin(Long idEntitat/*, Long tipusPlugin*/) throws Exception;
        
    
}
