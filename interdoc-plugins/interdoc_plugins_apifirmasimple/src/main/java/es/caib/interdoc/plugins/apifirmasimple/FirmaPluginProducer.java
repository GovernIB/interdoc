package es.caib.interdoc.plugins.apifirmasimple;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.fundaciobit.apisib.apifirmasimple.v1.ApiFirmaEnServidorSimple;
import org.fundaciobit.apisib.apifirmasimple.v1.jersey.ApiFirmaEnServidorSimpleJersey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirmaPluginProducer {
	
	private static final Logger LOG = LoggerFactory.getLogger(FirmaPluginProducer.class);
	
	/**
     * Instancia el plugin de firma. El marcam com a @ApplicationScoped per garantir que només s'en
     * instancia un.
     */
    @Produces
    @ApplicationScoped
    public ApiFirmaEnServidorSimple getFirmaPlugin() {
    	
    	LOG.info("Instanciant plugin firma simple en servidor ...");
    	
    	Config config = ConfigProvider.getConfig();
    	
    	final String url = config.getValue("es.caib.interdoc.plugin.firma.endpoint", String.class);
    	final String usuario = config.getValue("es.caib.interdoc.plugin.firma.usuari", String.class);
    	final String contrasena = config.getValue("es.caib.interdoc.plugin.firma.password", String.class);
    	
    	return new ApiFirmaEnServidorSimpleJersey(url, usuario, contrasena);
    	
    }
    
    
    public FirmaPluginProducer() {
    	
    }

}
