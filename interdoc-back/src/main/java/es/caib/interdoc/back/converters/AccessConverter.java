package es.caib.interdoc.back.converters;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import es.caib.interdoc.service.facade.AccesServiceFacade;
import es.caib.interdoc.service.model.AccesDTO;

@Named
@RequestScoped
public class AccessConverter implements Converter {

	@Inject
	AccesServiceFacade service;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		AccesDTO acces = new AccesDTO();
		
		try {
			if(!value.equals("null")) {
				Optional<List<AccesDTO>> accesos = service.findByRefenciaId(Long.parseLong(value));
				if (!accesos.isEmpty()) {
					System.out.println("getAsObject => " + acces.toString());
					return accesos.get();
				}
			}
		} catch (Exception e) {
			System.out.println("ERROR Converter:getAsObject");
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
				System.out.println("getAsString => " + r);
			}else if (value instanceof String) {
	               r = (String) value;
	            }
		} catch(Exception e) {
			System.out.println("ERROR Converter:getAsString");
		}
		
		return r;
	}
	
	

}
