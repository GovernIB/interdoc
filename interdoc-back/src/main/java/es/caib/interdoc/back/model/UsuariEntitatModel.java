package es.caib.interdoc.back.model;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import es.caib.interdoc.service.facade.UsuariEntitatServiceFacade;
import es.caib.interdoc.service.model.UsuariEntitatDTO;

import java.io.Serializable;

@Named
@ViewScoped
public class UsuariEntitatModel implements Serializable {

    private static final long serialVersionUID = 98L;


    @EJB
    private UsuariEntitatServiceFacade usuariEntitatService;

    private UsuariEntitatDTO value = new UsuariEntitatDTO();

    public UsuariEntitatDTO getValue() {
        return value;
    }

    public void setValue(UsuariEntitatDTO value) {
        this.value = value;
    }

    public void load() {
        if (value.getUsuariId() == null) {
            throw new IllegalArgumentException("UsuariId is null");
        }
        value = usuariEntitatService.findById(value.getUsuariId()).orElseThrow();
    }
    
    @PostConstruct
    public void postConstruct() {
        value.setActiu(true);

    }

    
}
