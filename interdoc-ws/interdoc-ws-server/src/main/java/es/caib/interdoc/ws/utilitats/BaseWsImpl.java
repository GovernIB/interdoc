package es.caib.interdoc.ws.utilitats;

import javax.inject.Inject;
import javax.jws.WebMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.commons.utils.Version;

/**
 * Classe base dels diferents serveis web que ofereix mètodes comuns.
 *
 * @author anadal
 */
public class BaseWsImpl {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    private Version version;

    /**
     * Retorna la versió actual de l'aplicació.
     *
     * @return versió actual de l'aplicació.
     */
    @WebMethod
    public String getVersion() {
        return version.getVersion();
    }
}
