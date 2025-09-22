package es.caib.interdoc.back.model;

import es.caib.interdoc.service.facade.UsuariServiceFacade;
import es.caib.interdoc.service.model.UsuariDTO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class UsuariModel implements Serializable {

    private static final long serialVersionUID = 98L;


    @EJB
    private UsuariServiceFacade usuariService;

    private UsuariDTO value = new UsuariDTO();

    public UsuariDTO getValue() {
        return value;
    }

    public void setValue(UsuariDTO value) {
        this.value = value;
    }

    public void load() {
        if (value.getUsuariId() == null) {
            throw new IllegalArgumentException("UsuariId is null");
        }
        value = usuariService.findById(value.getUsuariId()).orElseThrow();
    }
    
    @PostConstruct
    public void postConstruct() {
    	if (value.getUsuariId() == null){
       	}
    }
    
}
