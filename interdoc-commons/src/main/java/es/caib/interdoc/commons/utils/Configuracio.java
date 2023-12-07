package es.caib.interdoc.commons.utils;

import es.caib.interdoc.commons.config.PropertyFileConfigSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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
    	return getProperty(INTERDOC_PROPERTY_BASE + "obtenerreferencia.wsdl");
    }
    
    public static String getObtenerReferenciaUsuari() {
    	return getProperty(INTERDOC_PROPERTY_BASE + "obtenerreferencia.usuari");
    }
    
    public static String getObtenerReferenciaClau() {
    	return getProperty(INTERDOC_PROPERTY_BASE + "obtenerreferencia.clau");
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
