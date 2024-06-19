package es.caib.interdoc.back.model;

import es.caib.interdoc.service.facade.EntitatServiceFacade;
import es.caib.interdoc.service.facade.PluginServiceFacade;
import es.caib.interdoc.service.model.EntitatDTO;
import es.caib.interdoc.service.model.PluginDTO;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class PluginModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
	private PluginServiceFacade pluginService;
    
    @EJB
    private EntitatServiceFacade entitatService;

    private PluginDTO value = new PluginDTO();
    
    private EntitatDTO entitat = new EntitatDTO();

    public PluginDTO getValue() {
        return value;
    }

    public void setValue(PluginDTO value) {
        this.value = value;
    }

    public void load() {
        if (value.getId() == null) {
            throw new IllegalArgumentException("id is null");
        }
        value = pluginService.findById(value.getId()).orElseThrow();
        entitat = entitatService.findById(value.getEntitatId()).orElseThrow();
    }
}
