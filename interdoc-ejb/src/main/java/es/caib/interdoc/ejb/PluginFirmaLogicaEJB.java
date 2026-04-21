package es.caib.interdoc.ejb;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;


import es.caib.interdoc.commons.i18n.I18NException;
import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.plugins.firma.api.InterdocFirmaPlugin;
import es.caib.interdoc.service.model.FitxerDTO;
import es.caib.interdoc.service.model.InfoSignaturaDTO;
import es.caib.interdoc.service.model.PluginDTO;

@Stateless(name = "PluginFirmaLogicaEJB")
public class PluginFirmaLogicaEJB extends AbstractPluginLogicaEJB<InterdocFirmaPlugin> implements PluginFirmaLogicaService {


    @Override
    @RolesAllowed({Constants.ITD_USER, Constants.ITD_ADMIN, Constants.ITD_WS})
    public Long getType() {
        return Constants.PLUGIN_FIRMA;
    }

    @Override
    @RolesAllowed({Constants.ITD_USER, Constants.ITD_ADMIN, Constants.ITD_WS})
    protected String getName() {
        return "Firma";
    }

    @Override
    @RolesAllowed({Constants.ITD_USER, Constants.ITD_ADMIN, Constants.ITD_WS})
    public InterdocFirmaPlugin getInstanceOfPlugin(Long entitatId) throws I18NException{
        try {
                PluginDTO pluginDTO = null;
                InterdocFirmaPlugin interdocFirmaPlugin = null;
				pluginDTO = this.getPluginByEntity(entitatId);
				
				if (pluginDTO == null) {
					throw new I18NException("No s'ha trobat cap plugin de Firma actiu per l'entitat amb ID " + entitatId);
				}

				interdocFirmaPlugin = this.getInstanceByPluginID(pluginDTO.getId());
                
				log.info("Plugin de firma carregat correctament per l'entitatId " + entitatId + ": " + interdocFirmaPlugin.getClass().getName());
				
                return interdocFirmaPlugin;
            }catch (I18NException e) {
                throw e;
			} catch (Exception e) {
                String msg = "ERROR: No s'ha pogut inicialitzar el Plugin de Firma per l'entitat (ID: " + entitatId + "): "+ e.getMessage();
                log.error(msg, e);
                throw new I18NException(e,msg);
			}
    }

    @Override
    @RolesAllowed({Constants.ITD_USER, Constants.ITD_ADMIN, Constants.ITD_WS})
    public InfoSignaturaDTO firmarDocument(Long entitatId, FitxerDTO fitxer, String languageUI) throws Exception{

        InterdocFirmaPlugin plugin = this.getInstanceOfPlugin(entitatId);
        return plugin.firmarDocument(fitxer, languageUI);
       // return plugin.firmarDocument(fitxer);

    }



}
