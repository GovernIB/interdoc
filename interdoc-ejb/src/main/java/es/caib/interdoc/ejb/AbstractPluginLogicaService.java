package es.caib.interdoc.ejb;

import java.util.List;



import es.caib.interdoc.commons.i18n.I18NException;
import es.caib.interdoc.persistence.model.Plugin;
import es.caib.interdoc.service.facade.PluginServiceFacade;
import es.caib.interdoc.service.model.PluginDTO;


/**
 * 
 * @author anadal
 *
 */
public interface AbstractPluginLogicaService<I> extends PluginServiceFacade {

  public List<PluginDTO> getAllPlugins() throws I18NException;

  public I getInstanceByPluginID(long pluginID) throws I18NException;

  public Long getType();

  public PluginDTO getPluginByEntity(Long entityId) throws I18NException;

}
