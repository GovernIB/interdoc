package es.caib.interdoc.ws.resposta.mtom;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para tipoIdentificacion.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoIdentificacion"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="CLAVE_PERM"/&gt;
 *     &lt;enumeration value="PIN24"/&gt;
 *     &lt;enumeration value="DNIE"/&gt;
 *     &lt;enumeration value="PF_2CA"/&gt;
 *     &lt;enumeration value="PJ_2CA"/&gt;
 *     &lt;enumeration value="COMPONENTESSL"/&gt;
 *     &lt;enumeration value="SEDE_ELECTRONICA"/&gt;
 *     &lt;enumeration value="SELLO_ORGANO"/&gt;
 *     &lt;enumeration value="EMPLEADO_PUBLICO"/&gt;
 *     &lt;enumeration value="ENTIDAD_NO_PERSONA_JURIDICA"/&gt;
 *     &lt;enumeration value="EMPLEADO_PUBLICO_PSEUD"/&gt;
 *     &lt;enumeration value="CUALIFICADO_SELLO_ENTIDAD"/&gt;
 *     &lt;enumeration value="CUALIFICADO_AUTENTICACION"/&gt;
 *     &lt;enumeration value="CUALIFICADO_SELLO_TIEMPO"/&gt;
 *     &lt;enumeration value="REPRESENTACION_PJ"/&gt;
 *     &lt;enumeration value="REPRESENTACION_ENTIDAD_SIN_PF"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "tipoIdentificacion")
@XmlEnum
public enum TipoIdentificacion {

    CLAVE_PERM("CLAVE_PERM"),
    @XmlEnumValue("PIN24")
    PIN_24("PIN24"),
    DNIE("DNIE"),
    @XmlEnumValue("PF_2CA")
    PF_2_CA("PF_2CA"),
    @XmlEnumValue("PJ_2CA")
    PJ_2_CA("PJ_2CA"),
    COMPONENTESSL("COMPONENTESSL"),
    SEDE_ELECTRONICA("SEDE_ELECTRONICA"),
    SELLO_ORGANO("SELLO_ORGANO"),
    EMPLEADO_PUBLICO("EMPLEADO_PUBLICO"),
    ENTIDAD_NO_PERSONA_JURIDICA("ENTIDAD_NO_PERSONA_JURIDICA"),
    EMPLEADO_PUBLICO_PSEUD("EMPLEADO_PUBLICO_PSEUD"),
    CUALIFICADO_SELLO_ENTIDAD("CUALIFICADO_SELLO_ENTIDAD"),
    CUALIFICADO_AUTENTICACION("CUALIFICADO_AUTENTICACION"),
    CUALIFICADO_SELLO_TIEMPO("CUALIFICADO_SELLO_TIEMPO"),
    REPRESENTACION_PJ("REPRESENTACION_PJ"),
    REPRESENTACION_ENTIDAD_SIN_PF("REPRESENTACION_ENTIDAD_SIN_PF");
    private final String value;

    TipoIdentificacion(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoIdentificacion fromValue(String v) {
        for (TipoIdentificacion c: TipoIdentificacion.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
