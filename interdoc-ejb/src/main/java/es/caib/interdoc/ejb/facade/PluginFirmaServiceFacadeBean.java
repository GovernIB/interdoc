package es.caib.interdoc.ejb.facade;

import javax.ejb.Stateless;
import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.plugins.apifirmasimple.InterdocFirmaPlugin;



//@Logged
//@ExceptionTranslate
@Stateless
//@Local(PluginFirmaServiceFacade.class)
//@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PluginFirmaServiceFacadeBean extends PluginCacheServiceFacadeBean<InterdocFirmaPlugin> implements PluginFirmaServiceFacade{
    
    @Override
    protected Long getTipusPlugin() {
        return Constants.PLUGIN_FIRMA;
    }
}
