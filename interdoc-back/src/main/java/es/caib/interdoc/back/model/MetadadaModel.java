package es.caib.interdoc.back.model;

import es.caib.interdoc.service.facade.MetadadaServiceFacade;
import es.caib.interdoc.service.model.MetadadaDTO;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class MetadadaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private MetadadaServiceFacade metadadaervice;

    private MetadadaDTO value = new MetadadaDTO();

    public MetadadaDTO getValue() {
        return value;
    }

    public void setValue(MetadadaDTO value) {
        this.value = value;
    }

    public void load() {
        if (value.getId() == null) {
            throw new IllegalArgumentException("id is null");
        }
        value = metadadaervice.findById(value.getId()).orElseThrow();
    }
}
