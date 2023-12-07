package es.caib.interdoc.plugins.apifirmasimple;

import java.util.Properties;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.service.exception.PluginNoTrobatException;
import es.caib.interdoc.service.facade.PluginServiceFacade;

public class FirmaSimpleController {

	private final Logger LOG = LoggerFactory.getLogger(FirmaSimpleController.class);

	@EJB(mappedName = PluginServiceFacade.JNDI_NAME)
	private PluginServiceFacade pluginService;

	@Inject
	private FirmaPluginImpl plugin;

	public FirmaSimpleController() {
		LOG.info("Inici del mòdul Firma : constructor buit");
		// setPlugin(new FirmaPluginImpl());
	}

	public FirmaSimpleController(Long entitatId) throws Exception, PluginNoTrobatException {
		super();
		LOG.info("Inici del mòdul Firma per la entitat => " + String.valueOf(entitatId));

		if (this.pluginService == null)
			this.pluginService = (PluginServiceFacade) (new InitialContext()).lookup(PluginServiceFacade.JNDI_NAME);

		if (plugin == null) {

			FirmaPluginImpl firmaPluginImpl = (FirmaPluginImpl) pluginService.getPlugin(entitatId,
					Constants.PLUGIN_FIRMA);

			if (firmaPluginImpl == null)
				throw new PluginNoTrobatException();

			setPlugin(firmaPluginImpl);

			Properties props = pluginService.getPropertiesPlugin(entitatId, Constants.PLUGIN_FIRMA);

			plugin.carregarProperties(props);

		}

		/*
		 * if (entitatId < 1) { // Carregam el plugin amb el Producer
		 * FirmaPluginProducer producer = new FirmaPluginProducer(); plugin =
		 * producer.getFirmaPlugin(); setPlugin(plugin);
		 * 
		 * carregarPropertiesFile(); }
		 */

	}

	public FirmaPluginImpl getPlugin() {
		return this.plugin;
	}

	public void setPlugin(FirmaPluginImpl plugin) {
		this.plugin = plugin;
	}

	public PluginServiceFacade getPluginService() {
		return pluginService;
	}

	public void setPluginService(PluginServiceFacade pluginService) {
		this.pluginService = pluginService;
	}
}
