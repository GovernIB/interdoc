package es.caib.interdoc.back.model;

import es.caib.interdoc.service.facade.ReferenciaServiceFacade;
import es.caib.interdoc.service.model.ReferenciaDTO;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ReferenciaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ReferenciaServiceFacade referenciaService;

    private ReferenciaDTO value = new ReferenciaDTO();

    public ReferenciaDTO getValue() {
        return value;
    }

    public void setValue(ReferenciaDTO value) {
        this.value = value;
    }

    public void load() {
        if (value.getId() == null) {
            throw new IllegalArgumentException("id is null");
        }
        value = referenciaService.findById(value.getId()).orElseThrow();
    }
}
