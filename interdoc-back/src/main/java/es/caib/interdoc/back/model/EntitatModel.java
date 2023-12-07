package es.caib.interdoc.back.model;

import es.caib.interdoc.service.facade.EntitatServiceFacade;
import es.caib.interdoc.service.model.EntitatDTO;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class EntitatModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private EntitatServiceFacade entitatService;

    private EntitatDTO value = new EntitatDTO();

    public EntitatDTO getValue() {
        return value;
    }

    public void setValue(EntitatDTO value) {
        this.value = value;
    }

    public void load() {
        if (value.getId() == null) {
            throw new IllegalArgumentException("id is null");
        }
        value = entitatService.findById(value.getId()).orElseThrow();
    }
}
