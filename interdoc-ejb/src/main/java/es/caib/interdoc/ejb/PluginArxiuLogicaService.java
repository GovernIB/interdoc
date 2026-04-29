package es.caib.interdoc.ejb;

import java.util.Locale;

import javax.ejb.Local;

import es.caib.interdoc.commons.i18n.I18NException;
import es.caib.interdoc.plugins.arxiu.ArxiuPluginImpl;
import es.caib.pluginsib.arxiu.api.IArxiuPlugin;

/**
 * 
 * @author anadal
 *
 */
@Local
public interface PluginArxiuLogicaService extends AbstractPluginLogicaService<IArxiuPlugin>  {

        
    public static final String JNDI_NAME = "java:app/interdoc-ejb/PluginArxiuLogicaEJB!es.caib.interdoc.ejb.PluginArxiuLogicaService";

    
    public ArxiuPluginImpl getInstanceOfPlugin(Long entitatId) throws I18NException;

    /**
     * 
     * @param peticio
     * @param fitxer
     * @param locale
     * @return
     */
    //public InfoArxiuJPA custodiaAmbApiArxiu(Peticio peticio, InfoSignatura infoSignatura);


    /**
     * 
     * @param peticio
     * @param plugin
     * @param expedientId
     * @return
     */
    //public boolean tancarExpedient(Peticio peticio, IArxiuPlugin plugin, String expedientID);

}
