package es.caib.interdoc.back.converters;

import es.caib.interdoc.service.model.Estat;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

/**
 * Per transformar paràmetres que han de retornar enumeracions del tipus {@link EstatPublicacio}.
 * Normalment JSF ja fa la conversió que toca quan el camp mapejat es de tipus enumeració.
 * Però si mapejam a un Map (com en el cas dels filtres d'una cerca), cal que apliquem expressament
 * aquest conversor si volem rebre un enum enlloc d'un string.
 *
 * @author areus
 */
@FacesConverter(value = "estatConverter")
public class EstatConverter extends EnumConverter {

    public EstatConverter() {
        super(Estat.class);
    }
}
