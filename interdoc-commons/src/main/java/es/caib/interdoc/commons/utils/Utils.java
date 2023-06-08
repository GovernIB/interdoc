package es.caib.interdoc.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    /**
     * Comprueba si una cadena está vacia ("") o es null.
     * @param cadena
     * @return
     */
    public static boolean isEmpty(final String cadena) {
        return cadena == null || cadena.length() == 0
                || cadena.equals("null"); //esta condicion es para controlar los null de oracle
    }

    /**
     * Comprueba si una cadena no está vacia ("") o es null.
     * @param cadena
     * @return
     */
    public static boolean isNotEmpty(final String cadena) {
        return !isEmpty(cadena);
    }


    protected String fullDateToDate(String fullDate) {

        if (fullDate==null) return null;

        String strDate = fullDate;
        SimpleDateFormat sdfFullDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date;
        try {
            date = sdfFullDate.parse(fullDate);
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
            strDate = sdfDate.format(date);
        } catch (ParseException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return strDate;

    }

}
