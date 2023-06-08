
package es.caib.interdoc.ws.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para creaReferencia complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="creaReferencia"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="obtenerReferenciaRequest" type="{http://impl.ws.interdoc.caib.es/}obtenerReferenciaRequestInfo" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "creaReferencia", propOrder = {
    "obtenerReferenciaRequest"
})
public class CreaReferencia {

    protected ObtenerReferenciaRequestInfo obtenerReferenciaRequest;

    /**
     * Obtiene el valor de la propiedad obtenerReferenciaRequest.
     * 
     * @return
     *     possible object is
     *     {@link ObtenerReferenciaRequestInfo }
     *     
     */
    public ObtenerReferenciaRequestInfo getObtenerReferenciaRequest() {
        return obtenerReferenciaRequest;
    }

    /**
     * Define el valor de la propiedad obtenerReferenciaRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link ObtenerReferenciaRequestInfo }
     *     
     */
    public void setObtenerReferenciaRequest(ObtenerReferenciaRequestInfo value) {
        this.obtenerReferenciaRequest = value;
    }

}
