package es.caib.interdoc.plugins.arxiu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Controlador del plugin d'Arxiu
 * @author jagarcia
 *
 */

public class ArxiuController {

	private static final Logger LOG = LoggerFactory.getLogger(ArxiuController.class);
/*
	@Inject
	private ArxiuPluginImpl plugin;
	
	@EJB(mappedName = PluginArxiuServiceFacade.JNDI_NAME)
	private PluginArxiuServiceFacade pluginService;*/
	
	public ArxiuController() {
		LOG.info("Inici del modul Arxiu amb constructor buit");
		// setPlugin(new ArxiuPluginImpl());
	}

	//public ArxiuController(Long entitatId) {
	    
		/*super();
		
		LOG.info("Inici del modul Arxiu per la entitat : " + String.valueOf(entitatId));
		
		try {
			
			if (this.pluginService == null) {	
				this.pluginService = (PluginArxiuServiceFacade) (new InitialContext()).lookup(PluginArxiuServiceFacade.JNDI_NAME);
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
	}*/
	/*
	public PluginServiceFacade getPluginService() {
		return pluginService;
	}

	public void setPluginService(PluginArxiuServiceFacade pluginService) {
		this.pluginService = pluginService;
	}

	public void setPlugin(ArxiuPluginImpl plugin) {
		this.plugin = plugin;
	}

	public ArxiuPluginImpl getPlugin() {
		return this.plugin;
	}*/
	
	//}
	
}
