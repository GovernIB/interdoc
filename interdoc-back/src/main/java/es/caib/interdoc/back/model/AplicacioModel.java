package es.caib.interdoc.back.model;

import es.caib.interdoc.service.facade.AplicacioServiceFacade;
import es.caib.interdoc.service.model.AplicacioDTO;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class AplicacioModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private AplicacioServiceFacade aplicacioService;

    private AplicacioDTO value = new AplicacioDTO();

    public AplicacioDTO getValue() {
        return value;
    }

    public void setValue(AplicacioDTO value) {
        this.value = value;
    }

    public void load() {
        if (value.getId() == null) {
            throw new IllegalArgumentException("id is null");
        }
        value = aplicacioService.findById(value.getId()).orElseThrow();
    }
}
