package es.caib.interdoc.api.interna.ws.resposta.mtom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CSVQueryDocumentMtomSecurityResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CSVQueryDocumentMtomSecurityResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="documentoMtomResponse" type="{urn:es:gob:aapp:csvstorage:webservices:documentmtom:model:v1.0}documentoMtomResponse" minOccurs="0"/&gt;
 *         &lt;element name="waitResponse" type="{urn:es:gob:aapp:csvbroker:webservices:querydocument:model:v1.0}waitResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "queryDocumentSecurityResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CSVQueryDocumentMtomSecurityResponse",
	namespace = "urn:es:gob:aapp:csvbroker:webservices:querydocument:model:v1.0",
propOrder = {
    "code",
    "description",
    "documentoMtomResponse",
    "waitResponse"
})
public class CSVQueryDocumentMtomSecurityResponse {

    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected String description;
    protected DocumentoMtomResponse documentoMtomResponse;
    protected es.caib.interdoc.api.interna.ws.resposta.mtom.WaitResponse waitResponse;

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
     * Obtiene el valor de la propiedad documentoMtomResponse.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoMtomResponse }
     *     
     */
    public DocumentoMtomResponse getDocumentoMtomResponse() {
        return documentoMtomResponse;
    }

    /**
     * Define el valor de la propiedad documentoMtomResponse.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoMtomResponse }
     *     
     */
    public void setDocumentoMtomResponse(DocumentoMtomResponse value) {
        this.documentoMtomResponse = value;
    }

    /**
     * Obtiene el valor de la propiedad waitResponse.
     * 
     * @return
     *     possible object is
     *     {@link WaitResponse }
     *     
     */
    public es.caib.interdoc.api.interna.ws.resposta.mtom.WaitResponse getWaitResponse() {
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
    public void setWaitResponse(es.caib.interdoc.api.interna.ws.resposta.mtom.WaitResponse value) {
        this.waitResponse = value;
    }

}
