package es.caib.interdoc.api.interna.ws.resposta.mtom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para csvQueryDocumentWSSResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="csvQueryDocumentWSSResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="queryDocumentResponse" type="{urn:es:gob:aapp:csvbroker:webservices:querydocument:model:v1.0}CSVQueryDocumentResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "csvQueryDocumentWSSResponse", namespace = "urn:es:gob:aapp:csvbroker:webservices:querydocument:v1.0", propOrder = {
    "queryDocumentResponse"
})
public class CsvQueryDocumentWSSResponse {

    protected CSVQueryDocumentResponse queryDocumentResponse;

    /**
     * Obtiene el valor de la propiedad queryDocumentResponse.
     * 
     * @return
     *     possible object is
     *     {@link CSVQueryDocumentResponse }
     *     
     */
    public CSVQueryDocumentResponse getQueryDocumentResponse() {
        return queryDocumentResponse;
    }

    /**
     * Define el valor de la propiedad queryDocumentResponse.
     * 
     * @param value
     *     allowed object is
     *     {@link CSVQueryDocumentResponse }
     *     
     */
    public void setQueryDocumentResponse(CSVQueryDocumentResponse value) {
        this.queryDocumentResponse = value;
    }

}
