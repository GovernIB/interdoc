package es.caib.interdoc.plugins.arxiu;

import java.util.Properties;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.service.facade.PluginServiceFacade;

/**
 * 
 * Controlador del plugin d'Arxiu
 * @author jagarcia
 *
 */

public class ArxiuController {

	private static final Logger LOG = LoggerFactory.getLogger(ArxiuController.class);

	@Inject
	private ArxiuPluginImpl plugin;
	
	@EJB(mappedName = PluginServiceFacade.JNDI_NAME)
	private PluginServiceFacade pluginService;
	
	public ArxiuController() {
		LOG.info("Inici del modul Arxiu amb constructor buit");
		// setPlugin(new ArxiuPluginImpl());
	}

	public ArxiuController(Long entitatId) {
		super();
		
		LOG.info("Inici del modul Arxiu per la entitat : " + String.valueOf(entitatId));
		
		try {
			
			if (this.pluginService == null) {	
				LOG.info("pluginService is null");
				this.pluginService = (PluginServiceFacade) (new InitialContext()).lookup(PluginServiceFacade.JNDI_NAME);
			}
				
			if(plugin == null) {
				
				setPlugin((ArxiuPluginImpl) pluginService.getPlugin(entitatId, Constants.PLUGIN_ARXIU));

				Properties props = pluginService.getPropertiesPlugin(entitatId, Constants.PLUGIN_ARXIU);
				
				plugin.carregarProperties(props);	
			}
		
		} catch (Exception e) {
			LOG.error("error al cargar el plugin con pluginsib");
			e.printStackTrace();
		}
	}
	
	public PluginServiceFacade getPluginService() {
		return pluginService;
	}

	public void setPluginService(PluginServiceFacade pluginService) {
		this.pluginService = pluginService;
	}

	public void setPlugin(ArxiuPluginImpl plugin) {
		this.plugin = plugin;
	}

	public ArxiuPluginImpl getPlugin() {
		return this.plugin;
	}
	
	
	
}
