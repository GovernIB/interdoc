
package es.caib.interdoc.ws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CSVQueryDocumentResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CSVQueryDocumentResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="documentResponse" type="{urn:es:gob:aapp:csvbroker:webservices:querydocument:model:v1.0}documentResponse" minOccurs="0"/&gt;
 *         &lt;element name="waitResponse" type="{urn:es:gob:aapp:csvbroker:webservices:querydocument:model:v1.0}waitResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CSVQueryDocumentResponse", propOrder = {
    "code",
    "description",
    "documentResponse",
    "waitResponse"
})
public class CSVQueryDocumentResponse {

    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected String description;
    protected DocumentResponse documentResponse;
    protected WaitResponse waitResponse;

    /**
     * Obtiene el valor de la propiedad code.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Define el valor de la propiedad code.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Obtiene el valor de la propiedad description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define el valor de la propiedad description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Obtiene el valor de la propiedad documentResponse.
     * 
     * @return
     *     possible object is
     *     {@link DocumentResponse }
     *     
     */
    public DocumentResponse getDocumentResponse() {
        return documentResponse;
    }

    /**
     * Define el valor de la propiedad documentResponse.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentResponse }
     *     
     */
    public void setDocumentResponse(DocumentResponse value) {
        this.documentResponse = value;
    }

    /**
     * Obtiene el valor de la propiedad waitResponse.
     * 
     * @return
     *     possible object is
     *     {@link WaitResponse }
     *     
     */
    public WaitResponse getWaitResponse() {
        return waitResponse;
    }

    /**
     * Define el valor de la propiedad waitResponse.
     * 
     * @param value
     *     allowed object is
     *     {@link WaitResponse }
     *     
     */
    public void setWaitResponse(WaitResponse value) {
        this.waitResponse = value;
    }

}
