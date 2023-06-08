package es.caib.interdoc.commons.utils;

import es.caib.interdoc.commons.config.PropertyFileConfigSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author: jagarcia
 */

public class Configuracio implements Constants {

    private static final Logger log = LoggerFactory.getLogger(Configuracio.class);

    private static PropertyFileConfigSource propietats;


    public Configuracio() {
        this.propietats = new PropertyFileConfigSource();
    }


    public static String getProperty(String key) {
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

}
