
package es.caib.interdoc.ws.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para csvQueryDocument complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="csvQueryDocument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="csvQueryDocumentRequest" type="{http://impl.ws.interdoc.caib.es/}CSVQueryDocumentRequest" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "csvQueryDocument", propOrder = {
    "csvQueryDocumentRequest"
})
public class CsvQueryDocument {

    protected CSVQueryDocumentRequest csvQueryDocumentRequest;

    /**
     * Obtiene el valor de la propiedad csvQueryDocumentRequest.
     * 
     * @return
     *     possible object is
     *     {@link CSVQueryDocumentRequest }
     *     
     */
    public CSVQueryDocumentRequest getCsvQueryDocumentRequest() {
        return csvQueryDocumentRequest;
    }

    /**
     * Define el valor de la propiedad csvQueryDocumentRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link CSVQueryDocumentRequest }
     *     
     */
    public void setCsvQueryDocumentRequest(CSVQueryDocumentRequest value) {
        this.csvQueryDocumentRequest = value;
    }

}
