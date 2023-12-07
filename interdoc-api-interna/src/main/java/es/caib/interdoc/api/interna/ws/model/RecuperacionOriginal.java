package es.caib.interdoc.api.interna.ws.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Clase Java para recuperacionOriginal.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="recuperacionOriginal"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="S"/&gt;
 *     &lt;enumeration value="N"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "recuperacionOriginal")
@XmlEnum
public enum RecuperacionOriginal {
	S,
    N;

    public String value() {
        return name();
    }

    public static RecuperacionOriginal fromValue(String v) {
        return valueOf(v);
    }
	
}
