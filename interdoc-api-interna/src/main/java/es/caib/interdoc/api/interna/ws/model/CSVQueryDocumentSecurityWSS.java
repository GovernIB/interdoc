package es.caib.interdoc.api.interna.ws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Clase Java para csvQueryDocumentSecurityWSS complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="csvQueryDocumentSecurityWSS"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="queryDocumentSecurityRequest" type="{urn:es:gob:aapp:csvbroker:webservices:querydocument:model:v1.0}ueryDocumentSecurityRequest" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "csvQueryDocumentSecurityWSS", propOrder = {
    "queryDocumentSecurityRequest"
})
public class CSVQueryDocumentSecurityWSS {
	
	protected UeryDocumentSecurityRequest queryDocumentSecurityRequest;
	
	/**
     * Obtiene el valor de la propiedad queryDocumentSecurityRequest.
     * 
     * @return
     *     possible object is
     *     {@link UeryDocumentSecurityRequest }
     *     
     */
    public UeryDocumentSecurityRequest getQueryDocumentSecurityRequest() {
        return queryDocumentSecurityRequest;
    }

    /**
     * Define el valor de la propiedad queryDocumentSecurityRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link UeryDocumentSecurityRequest }
     *     
     */
    public void setQueryDocumentSecurityRequest(UeryDocumentSecurityRequest value) {
        this.queryDocumentSecurityRequest = value;
    }

}
