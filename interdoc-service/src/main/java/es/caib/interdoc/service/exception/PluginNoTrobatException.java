package es.caib.interdoc.service.exception;

import java.util.Locale;

/**
 * Ja existeix una aplicació per la unitat orgànica amb el mateix codiDir3.
 *
 * @author jagarcia
 */
public class PluginNoTrobatException extends ServiceException {

    private static final long serialVersionUID = 1L;


    public PluginNoTrobatException() {
    }

    @Override
    public String getLocalizedMessage(Locale locale) {
        return translate(locale, "error.recursNoTrobat");
    }
}
