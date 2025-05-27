package es.caib.interdoc.ejb.facade;

import javax.ejb.Stateless;
import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.plugins.arxiu.InterdocArxiuPlugin;


//@Logged
//@ExceptionTranslate
@Stateless
//@Local(PluginArxiuServiceFacade.class)
//@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PluginArxiuServiceFacadeBean extends PluginCacheServiceFacadeBean<InterdocArxiuPlugin> implements PluginArxiuServiceFacade{

    @Override
    protected Long getTipusPlugin() {
        return Constants.PLUGIN_ARXIU;
    }
    
}
