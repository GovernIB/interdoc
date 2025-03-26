package es.caib.interdoc.back.converters;

import es.caib.interdoc.service.model.Estat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Per transformar paràmetres que han de retornar enumeracions del tipus {@link Estat}.
 * Normalment JSF ja fa la conversió que toca quan el camp mapejat es de tipus enumeració.
 * Però si mapejam a un Map (com en el cas dels filtres d'una cerca), cal que apliquem expressament
 * aquest conversor si volem rebre un enum enlloc d'un string.
 *
 * @author areus
 */
@FacesConverter(value = "estatConverter", forClass= Estat.class)
public class EstatConverter implements Converter<Estat> {
	
	@Override
	public Estat getAsObject(FacesContext context, UIComponent component, String value) {
	   	 if (value == null || value.isEmpty()) {
	            return Estat.INACTIU;
	        }
	   	
	   	 return Estat.valueOf(value.toUpperCase());
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Estat value) {
		 return value != null ? value.name() : "";
	}
}
