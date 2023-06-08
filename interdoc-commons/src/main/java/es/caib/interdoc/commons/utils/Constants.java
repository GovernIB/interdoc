package es.caib.interdoc.commons.utils;


import java.util.HashMap;
import java.util.Map;

/**
 * Constants emprades dins tota l'aplicació.
 *
 * @author anadal
 * @author areus
 */
public interface Constants {

    String INTERDOC_PROPERTY_BASE = "es.caib.interdoc";

    String INTERDOC_PLUGIN_ARXIU_BASE = "plugin.arxiu";

    /**
     * Constant pel rol d'Administrador
     */
    String ITD_ADMIN = "ITD_ADMIN";

    /**
     * Constant pel rol d'Usuari
     */
    String ITD_USER = "ITD_USER";

    /**
     * Constant pel rol de WS
     */
    String ITD_WS = "ITD_WS";
    
    /*-------------- VARIABLES DE SESSIO --------------*/
    String SESSION_LOGIN_INFO = "loginInfo";

    /* ------------- TIPUS USUARI ----------- */
    Long TIPUS_USUARI_PERSONA = 1L;
    Long TIPUS_USUARI_APLICACIO = 2L;

    Long[] TIPUS_USUARI = {
            TIPUS_USUARI_PERSONA, TIPUS_USUARI_APLICACIO
    };


    /* -------------- IDIOMES --------------*/
    String IDIOMA_CATALA = "ca";
    String IDIOMA_CASTELLA = "es";
    String IDIOMA_ANGLES = "en";

    Long IDIOMA_CATALAN_ID = 1L;
    String IDIOMA_CATALAN_CODIGO = "ca";

    Long IDIOMA_CASTELLANO_ID = 2L;
    String IDIOMA_CASTELLANO_CODIGO = "es";

    Long IDIOMA_GALLEGO_ID = 3L;
    String IDIOMA_GALLEGO_CODIGO = "gl";

    Long IDIOMA_EUSKERA_ID = 4L;
    String IDIOMA_EUSKERA_CODIGO = "eu";

    Long IDIOMA_INGLES_ID = 5L;
    String IDIOMA_INGLES_CODIGO = "en";

    Long[] IDIOMAS_UI = {
            IDIOMA_CATALAN_ID, IDIOMA_CASTELLANO_ID
    };

    Long[] IDIOMAS_INTERDOC = {
            IDIOMA_CATALAN_ID, IDIOMA_CASTELLANO_ID,
            IDIOMA_GALLEGO_ID, IDIOMA_EUSKERA_ID,
            IDIOMA_INGLES_ID
    };

    Long[] IDIOMAS_INTERDOC_ES = {
            IDIOMA_CASTELLANO_ID, IDIOMA_CATALAN_ID,
            IDIOMA_GALLEGO_ID, IDIOMA_EUSKERA_ID,
            IDIOMA_INGLES_ID
    };

    Map<Long, String> CODIGO_BY_IDIOMA_ID = new HashMap<>() {{
        put(IDIOMA_CATALAN_ID, IDIOMA_CATALAN_CODIGO);
        put(IDIOMA_CASTELLANO_ID, IDIOMA_CASTELLANO_CODIGO);
        put(IDIOMA_GALLEGO_ID, IDIOMA_GALLEGO_CODIGO);
        put(IDIOMA_EUSKERA_ID, IDIOMA_EUSKERA_CODIGO);
        put(IDIOMA_INGLES_ID, IDIOMA_INGLES_CODIGO);
    }};

    Map<String, Long> IDIOMA_ID_BY_CODIGO = invert(CODIGO_BY_IDIOMA_ID);
    
    String PUBLICO = "PUBLICO";
    String PRIVADO = "PRIVADO";
    String RESTRINGIDO = "RESTRINGIDO";


    public static <V, K> Map<V, K> invert(Map<K, V> map) {

        Map<V, K> inv = new HashMap<V, K>();

        for (Map.Entry<K, V> entry : map.entrySet())
            inv.put(entry.getValue(), entry.getKey());

        return inv;
    }


}
