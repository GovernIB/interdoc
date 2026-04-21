package es.caib.interdoc.ejb;

import javax.ejb.Local;

import es.caib.interdoc.commons.i18n.I18NException;
import es.caib.interdoc.plugins.firma.api.InterdocFirmaPlugin;
import es.caib.interdoc.service.model.FitxerDTO;
import es.caib.interdoc.service.model.InfoSignaturaDTO;

@Local
public interface PluginFirmaLogicaService extends AbstractPluginLogicaService<InterdocFirmaPlugin> {


    public static final String JNDI_NAME = "java:app/interdoc-ejb/PluginFirmaLogicaEJB!es.caib.interdoc.ejb.PluginFirmaLogicaService";


    public InterdocFirmaPlugin getInstanceOfPlugin(Long entitatId) throws I18NException;

    public InfoSignaturaDTO firmarDocument(Long entitatId, FitxerDTO fitxer, String languageUI) throws Exception;


}
