package es.caib.interdoc.ejb.repository;


import es.caib.interdoc.commons.i18n.I18NException;
import es.caib.interdoc.persistence.model.Plugin;
import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.PluginAtribut;
import es.caib.interdoc.service.model.PluginDTO;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Interfície de les operacions bàsiques sobre plugins.
 *
 * @author jagarcia
 */
public interface PluginRepository extends CrudRepository<Plugin, Long> {


    List<PluginDTO> findPagedByFilterAndOrder(int firstResult, int maxResult,
                                                 Map<PluginAtribut, Object> filter,
                                                 List<Ordre<PluginAtribut>> ordenacio);

    List<Plugin> getAll();

    long countByFilter(Map<PluginAtribut, Object> filter);
    
    
    Object getPlugin(Long id) throws I18NException;
    
    Properties getPropertiesPlugin(Long id) throws I18NException;
    
    boolean existPlugin(Long id) throws I18NException;
    
    List<Object> getPlugins(Long tipoPlugin) throws I18NException;

}
