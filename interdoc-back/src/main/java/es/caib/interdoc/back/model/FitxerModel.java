package es.caib.interdoc.back.model;

import es.caib.interdoc.service.facade.FitxerServiceFacade;
import es.caib.interdoc.service.model.FitxerDTO;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class FitxerModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private FitxerServiceFacade fitxerService;

    private FitxerDTO value = new FitxerDTO();

    public FitxerDTO getValue() {
        return value;
    }

    public void setValue(FitxerDTO value) {
        this.value = value;
    }

    public void load() {
        if (value.getId() == null) {
            throw new IllegalArgumentException("id is null");
        }
        value = fitxerService.findById(value.getId()).orElseThrow();
    }
}
