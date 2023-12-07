package es.caib.interdoc.back.model;

import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.service.facade.AccesServiceFacade;
import es.caib.interdoc.service.model.AccesDTO;

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
public class AccesModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private AccesServiceFacade accesService;

	private String referenciaId;

	private AccesDTO value = new AccesDTO();

	private List<AccesDTO> lista = new ArrayList<AccesDTO>();

	public AccesDTO getValue() {
		return value;
	}

	public void setValue(AccesDTO value) {
		this.value = value;
	}

	public List<AccesDTO> getLista() {
		return lista;
	}

	public void setLista(List<AccesDTO> lista) {
		this.lista = lista;
	}

	public void setReferenciaId(String referenciaId) {
		this.referenciaId = referenciaId;
	}

	public String getReferenciaId() {
		return this.referenciaId;
	}

	public void load() {
		if (value.getId() == null) {
			throw new IllegalArgumentException("id is null");
		}
		value = accesService.findById(value.getId()).orElseThrow();
	}

	public void loadByReferenciaId() {

		if (Utils.isEmpty(referenciaId)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "ReferenciaId és obligatori"));
			throw new IllegalArgumentException("referenciaId is null or empty");
		}

		Optional<List<AccesDTO>> accesosByReferenciaId = accesService
				.findByRefenciaId(Long.parseLong(getReferenciaId()));
		
		if (!accesosByReferenciaId.isEmpty())
			setLista(accesosByReferenciaId.get());
	}

}
