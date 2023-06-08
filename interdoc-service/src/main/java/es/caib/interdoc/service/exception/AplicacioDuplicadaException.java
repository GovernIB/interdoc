package es.caib.interdoc.service.exception;

import java.util.Locale;

/**
 * Ja existeix una aplicació per la unitat orgànica amb el mateix codiDir3.
 *
 * @author jagarcia
 */
public class AplicacioDuplicadaException extends ServiceException {

    private static final long serialVersionUID = 1L;

    private final String codiDir3;

    public AplicacioDuplicadaException(String codiDir3) {
        this.codiDir3 = codiDir3;
    }

    @Override
    public String getLocalizedMessage(Locale locale) {
        return translate(locale, "error.unitatDuplicada", codiDir3);
    }
}
