package es.caib.interdoc.back.model;

import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.service.facade.MetadadaServiceFacade;
import es.caib.interdoc.service.model.MetadadaDTO;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Named
@ViewScoped
public class MetadadaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private MetadadaServiceFacade metadadaService;
    
    private String referenciaId;

    private MetadadaDTO value = new MetadadaDTO();
    
    private List<MetadadaDTO> lista = new ArrayList<MetadadaDTO>();

    public MetadadaDTO getValue() {
        return value;
    }

    public void setValue(MetadadaDTO value) {
        this.value = value;
    }

    public String getReferenciaId() {
		return referenciaId;
	}

	public void setReferenciaId(String referenciaId) {
		this.referenciaId = referenciaId;
	}

	public List<MetadadaDTO> getLista() {
		return lista;
	}

	public void setLista(List<MetadadaDTO> lista) {
		this.lista = lista;
	}

	public void load() {
        if (value.getId() == null) {
            throw new IllegalArgumentException("id is null");
        }
        value = metadadaService.findById(value.getId()).orElseThrow();
    }
    
    public void loadByReferenciaId() {

		if (Utils.isEmpty(referenciaId)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "ReferenciaId és obligatori"));
			throw new IllegalArgumentException("referenciaId is null or empty");
		}

		Optional<List<MetadadaDTO>> itemsByReferenciaId = metadadaService
				.findByReferenciaId(Long.parseLong(getReferenciaId()));
		
		if (!itemsByReferenciaId.isEmpty())
			setLista(itemsByReferenciaId.get());
	}
}
