package es.caib.interdoc.plugins.arxiu;

import es.caib.plugins.arxiu.api.IArxiuPlugin;
import es.caib.plugins.arxiu.caib.ArxiuPluginCaib;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.Properties;

public class ArxiuPluginProducer {

    private static final Logger LOG = LoggerFactory.getLogger(ArxiuPluginProducer.class);

    /**
     * Instancia el plugin d'arxiu. El marcam com a @ApplicationScoped per garantir que només s'en
     * instancia un.
     */
    @Produces
    @ApplicationScoped
    public IArxiuPlugin getArxiuPlugin() {
        LOG.info("Instanciant plugin arxiu...");
        
        Config config = ConfigProvider.getConfig();
        
        // per instanciar el plugin necessitam adaptar les propietats
        Properties properties = new Properties();
        properties.setProperty("plugin.arxiu.caib.base.url", config.getValue("es.caib.interdoc.plugin.arxiu.endpoint", String.class));
        properties.setProperty("plugin.arxiu.caib.aplicacio.codi", config.getValue("es.caib.interdoc.plugin.arxiu.aplicacioCodi", String.class));
        properties.setProperty("plugin.arxiu.caib.usuari", config.getValue("es.caib.interdoc.plugin.arxiu.usuari", String.class));
        properties.setProperty("plugin.arxiu.caib.contrasenya", config.getValue("es.caib.interdoc.plugin.arxiu.contrasenya", String.class));
        
        LOG.info("----------- PROPERTIES ARXIU -----------------");
        LOG.info("URL " + properties.getProperty("plugin.arxiu.caib.base.url"));
        LOG.info("CODI " + properties.getProperty("plugin.arxiu.caib.aplicacio.codi"));
        LOG.info("USUARI " + properties.getProperty("plugin.arxiu.caib.usuari"));
        LOG.info("---------  FI PROPERTIES ARXIU ------------------");

        IArxiuPlugin plugin = new ArxiuPluginCaib("", properties);
        LOG.info("Plugin instanciat");
        return plugin;
    }
    
    ArxiuPluginProducer(){
    	
    }

}