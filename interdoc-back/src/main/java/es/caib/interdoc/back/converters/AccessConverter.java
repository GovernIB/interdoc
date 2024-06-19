package es.caib.interdoc.back.converters;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.service.facade.AccesServiceFacade;
import es.caib.interdoc.service.model.AccesDTO;

@Named
@RequestScoped
public class AccessConverter implements Converter {
	
	private static final Logger LOG = LoggerFactory.getLogger(AccessConverter.class);

	@Inject
	AccesServiceFacade service;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		AccesDTO acces = new AccesDTO();
		
		try {
			if(!value.equals("null")) {
				Optional<List<AccesDTO>> accesos = service.findByRefenciaId(Long.parseLong(value));
				if (!accesos.isEmpty()) {
					return accesos.get();
				}
			}
		} catch (Exception e) {
			LOG.debug("ERROR Converter:getAsObject => " + e.getMessage());
		}
		
		return acces;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		String r = "";
		
		try {
			if (value instanceof AccesDTO) {
				AccesDTO acces = (AccesDTO) value;
				r = String.valueOf(acces.getReferenciaId());
			}else if (value instanceof String) {
	               r = (String) value;
	            }
		} catch(Exception e) {
			LOG.debug("ERROR Converter:getAsString => " + e.getMessage());
		}
		
		return r;
	}
	
	

}
