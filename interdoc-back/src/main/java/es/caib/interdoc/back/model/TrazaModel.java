package es.caib.interdoc.back.model;

import es.caib.interdoc.service.facade.TrazaServiceFacade;
import es.caib.interdoc.service.model.TrazaDTO;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class TrazaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private TrazaServiceFacade trazaService;

    private TrazaDTO value = new TrazaDTO();

    public TrazaDTO getValue() {
        return value;
    }

    public void setValue(TrazaDTO value) {
        this.value = value;
    }

    public void load() {
        if (value.getId() == null) {
            throw new IllegalArgumentException("id is null");
        }
        value = trazaService.findById(value.getId()).orElseThrow();
    }
}
