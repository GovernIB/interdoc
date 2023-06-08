package es.caib.interdoc.commons.rest;

/**
 * Constants emprades a l'API REST
 *
 * @author areus
 */
public interface RestConstants {

    /**
     * Propietat del request per emmagatzemar el locale actual de la petició.
     */
    String REQUEST_LOCALE = "es.caib.interdoc.commons.rest.requestLocale";

    /**
     * Paràmetre del context definit al web.xml amb la llista de locales soportats.
     */
    String SUPPORTED_LOCALES = "es.caib.interdoc.commons.rest.supportedLocales";
}
