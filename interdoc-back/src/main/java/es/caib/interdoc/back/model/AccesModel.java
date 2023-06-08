package es.caib.interdoc.back.model;

import es.caib.interdoc.service.facade.AccesServiceFacade;
import es.caib.interdoc.service.model.AccesDTO;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class AccesModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private AccesServiceFacade accesService;

    private AccesDTO value = new AccesDTO();

    public AccesDTO getValue() {
        return value;
    }

    public void setValue(AccesDTO value) {
        this.value = value;
    }

    public void load() {
        if (value.getId() == null) {
            throw new IllegalArgumentException("id is null");
        }
        value = accesService.findById(value.getId()).orElseThrow();
    }
}
