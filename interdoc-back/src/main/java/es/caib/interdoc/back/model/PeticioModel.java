package es.caib.interdoc.back.model;

import es.caib.interdoc.service.facade.PeticioATercerServiceFacade;
import es.caib.interdoc.service.model.AplicacioDTO;
import es.caib.interdoc.service.model.PeticioATercerDTO;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class PeticioModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private PeticioATercerServiceFacade peticioService;

    private PeticioATercerDTO value = new PeticioATercerDTO();

    public PeticioATercerDTO getValue() {
        return value;
    }

    public void setValue(PeticioATercerDTO value) {
        this.value = value;
    }

    public void load() {
        if (value.getId() == null) {
            throw new IllegalArgumentException("id is null");
        }
        value = peticioService.findById(value.getId()).orElseThrow();
    }
}
