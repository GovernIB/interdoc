package es.caib.interdoc.api.interna.secured.descargardocumento.resposta;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



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
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CSVQueryDocumentMtomSecurityResponse", propOrder = {
    "code",
    "description",
    "documentoMtomResponse",
    "waitResponse"
})

public class CSVQueryDocumentMtomSecurityResponse {
	
	private static Logger log = LoggerFactory.getLogger(CSVQueryDocumentMtomSecurityResponse.class.getName());
	
	 	@XmlElement(required = true)
	    protected String code;
	    @XmlElement(required = true)
	    protected String description;
	    protected DocumentoMtomResponse documentoMtomResponse;
	    protected WaitResponse waitResponse;

	    public String getCode() {
	        return code;
	    }

	    public void setCode(String value) {
	        this.code = value;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String value) {
	        this.description = value;
	    }

	    public DocumentoMtomResponse getDocumentoMtomResponse() {
	        return documentoMtomResponse;
	    }

	    public void setDocumentoMtomResponse(DocumentoMtomResponse value) {
	        this.documentoMtomResponse = value;
	    }

	    public WaitResponse getWaitResponse() {
	        return waitResponse;
	    }

	    public void setWaitResponse(WaitResponse value) {
	        this.waitResponse = value;
	    }

}
