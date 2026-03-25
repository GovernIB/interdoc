package es.caib.interdoc.commons.utils;

import es.caib.interdoc.commons.config.PropertyFileConfigSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author: jagarcia
 */

public class Configuracio implements Constants {

    private static final Logger log = LoggerFactory.getLogger(Configuracio.class);

    private static PropertyFileConfigSource propietats = null;
    
    private static final Properties fileProperties = new Properties();

    public Configuracio() {
        propietats = new PropertyFileConfigSource();
    }

    public static String getProperty(String key) {
  
    	if (propietats == null ) {
    		propietats = new PropertyFileConfigSource();
    	}
        return propietats.getValue(key);
    }


    public static boolean isDesenvolupament() {
        return Boolean.parseBoolean(getProperty(INTERDOC_PROPERTY_BASE + "development"));
    }

    public static boolean isCAIB() {
        return Boolean.parseBoolean(getProperty(INTERDOC_PROPERTY_BASE + "iscaib"));
    }

    public static String getDefaultLanguage() {
        String valor = getProperty(INTERDOC_PROPERTY_BASE + "defaultlanguage");
        return (valor != null) ? valor : IDIOMA_CATALA;
    }

    public static String getBaseWsUrl() {
    	return getProperty(INTERDOC_PROPERTY_BASE + "basewsurl");
    }
    
    public static String getFileTempPath() {
    	return getProperty(INTERDOC_PROPERTY_BASE + "filesdirectory");
    }
    
    public static boolean isCsvQueryDocumentServiceTest() {
    	return Boolean.parseBoolean(getProperty(INTERDOC_PROPERTY_BASE + "csvQueryDocumentTest"));
    }
    
    public static String getCsvQueryDocumentWebserviceURL() {
    	return getProperty(INTERDOC_PROPERTY_BASE + "csvquerydocumentws");
    }
    
    public static String getObtenerReferenciaWsdl() {
    	return getProperty(INTERDOC_PROPERTY_BASE + "plugins.arxiu.endpoint");
    }
    
    public static String getObtenerReferenciaUsuari() {
    	return getProperty(INTERDOC_PROPERTY_BASE + "plugins.arxiu.usuari");
    }
    
    public static String getObtenerReferenciaClau() {
    	return getProperty(INTERDOC_PROPERTY_BASE + "plugins.arxiu.clau");
    }

    /**
     * Obté totes les propietats d'un plugin per entitat i tipus de plugin des de BBDD.
     *
     * @param entitatId id de l'entitat
     * @param tipusPlugin tipus de plugin
     * @return propietats del plugin, o buides si no existeixen / hi ha error
     */
    public static Properties getPluginProperties(Long entitatId, Long tipusPlugin) {
        if (entitatId == null || tipusPlugin == null) {
            log.warn("Paràmetres invàlids a getPluginProperties(entitatId={}, tipusPlugin={})",
                    entitatId, tipusPlugin);
            return new Properties();
        }

        try {
            final String pluginServiceJndiName = "java:app/interdoc-ejb/PluginServiceFacadeBean!es.caib.interdoc.service.facade.PluginServiceFacade";
            Object pluginService = (new InitialContext()).lookup(pluginServiceJndiName);

            Method getPropertiesPluginMethod = pluginService.getClass()
                    .getMethod("getPropertiesPlugin", Long.class, Long.class);

            Properties pluginProperties = (Properties) getPropertiesPluginMethod.invoke(pluginService, entitatId, tipusPlugin);
            return (pluginProperties != null) ? pluginProperties : new Properties();
        } catch (Exception e) {
            log.error("Error obtenint propietats del plugin per entitat {} i tipus {}",
                    entitatId, tipusPlugin, e);
            return new Properties();
        }
    }

    /**
     * Obté una propietat d'un plugin per entitat i tipus de plugin des de BBDD.
     *
     * @param entitatId id de l'entitat
     * @param tipusPlugin tipus de plugin
     * @param property nom de la propietat a recuperar
     * @return valor de la propietat o null si no existeix / hi ha error
     */
    public static String getPluginProperty(Long entitatId, Long tipusPlugin, String property) {
        if (entitatId == null || tipusPlugin == null || property == null || property.trim().isEmpty()) {
            log.warn("Paràmetres invàlids a getPluginProperty(entitatId={}, tipusPlugin={}, property={})",
                    entitatId, tipusPlugin, property);
            return null;
        }

        return getPluginProperties(entitatId, tipusPlugin).getProperty(property);
    }
    
    public static Properties getSystemAndFileProperties() {
        return getFilesProperties();
    }
    
    public static Properties getFilesProperties() {
        if (fileProperties.isEmpty()) {
            String propertyFile = System.getProperty(Constants.INTERDOC_PROPERTY_BASE + "properties");
            File file = new File(propertyFile);

            String propertySystemFile = System.getProperty(Constants.INTERDOC_PROPERTY_BASE + "system.properties");
            File systemFile = new File(propertySystemFile);

            try {
                fileProperties.load(new FileInputStream(file));
                fileProperties.load(new FileInputStream(systemFile));
            } catch (IOException e) {
                log.error("No es pot carregar algun dels fitxers de propietats ... ", e);
            }
        }

        return fileProperties;
    }

}
