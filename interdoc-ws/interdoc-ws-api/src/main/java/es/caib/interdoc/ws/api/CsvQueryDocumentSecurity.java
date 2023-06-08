
package es.caib.interdoc.ws.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para csvQueryDocumentSecurity complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="csvQueryDocumentSecurity"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="csvQueryDocumentSecurityRequest" type="{http://impl.ws.interdoc.caib.es/}CSVQueryDocumentSecurityRequest" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "csvQueryDocumentSecurity", propOrder = {
    "csvQueryDocumentSecurityRequest"
})
public class CsvQueryDocumentSecurity {

    protected CSVQueryDocumentSecurityRequest csvQueryDocumentSecurityRequest;

    /**
     * Obtiene el valor de la propiedad csvQueryDocumentSecurityRequest.
     * 
     * @return
     *     possible object is
     *     {@link CSVQueryDocumentSecurityRequest }
     *     
     */
    public CSVQueryDocumentSecurityRequest getCsvQueryDocumentSecurityRequest() {
        return csvQueryDocumentSecurityRequest;
    }

    /**
     * Define el valor de la propiedad csvQueryDocumentSecurityRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link CSVQueryDocumentSecurityRequest }
     *     
     */
    public void setCsvQueryDocumentSecurityRequest(CSVQueryDocumentSecurityRequest value) {
        this.csvQueryDocumentSecurityRequest = value;
    }

}
