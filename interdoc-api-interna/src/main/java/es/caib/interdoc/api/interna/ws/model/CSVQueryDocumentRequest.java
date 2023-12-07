package es.caib.interdoc.api.interna.ws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Clase Java para CSVQueryDocumentRequest complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CSVQueryDocumentRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="csv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dir3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="recuperacion_original" type="{urn:es:gob:aapp:csvbroker:webservices:querydocument:model:v1.0}recuperacionOriginal" minOccurs="0"/&gt;
 *         &lt;element name="documento_eni" type="{urn:es:gob:aapp:csvbroker:webservices:querydocument:model:v1.0}documentoEni" minOccurs="0"/&gt;
 *         &lt;element name="idEni" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CSVQueryDocumentRequest", propOrder = {
    "csv",
    "dir3",
    "recuperacionOriginal",
    "documentoEni",
    "idEni"
})
@XmlSeeAlso({
    CSVQueryDocumentSecurityRequest.class
})
public class CSVQueryDocumentRequest {

	protected String csv;
    protected String dir3;
    @XmlElement(name = "recuperacion_original")
    @XmlSchemaType(name = "string")
    protected RecuperacionOriginal recuperacionOriginal;
    @XmlElement(name = "documento_eni")
    @XmlSchemaType(name = "string")
    protected DocumentoEni documentoEni;
    protected String idEni;

    /**
     * Obtiene el valor de la propiedad csv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCsv() {
        return csv;
    }

    /**
     * Define el valor de la propiedad csv.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCsv(String value) {
        this.csv = value;
    }

    /**
     * Obtiene el valor de la propiedad dir3.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDir3() {
        return dir3;
    }

    /**
     * Define el valor de la propiedad dir3.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDir3(String value) {
        this.dir3 = value;
    }

    /**
     * Obtiene el valor de la propiedad recuperacionOriginal.
     * 
     * @return
     *     possible object is
     *     {@link RecuperacionOriginal }
     *     
     */
    public RecuperacionOriginal getRecuperacionOriginal() {
        return recuperacionOriginal;
    }

    /**
     * Define el valor de la propiedad recuperacionOriginal.
     * 
     * @param value
     *     allowed object is
     *     {@link RecuperacionOriginal }
     *     
     */
    public void setRecuperacionOriginal(RecuperacionOriginal value) {
        this.recuperacionOriginal = value;
    }

    /**
     * Obtiene el valor de la propiedad documentoEni.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoEni }
     *     
     */
    public DocumentoEni getDocumentoEni() {
        return documentoEni;
    }

    /**
     * Define el valor de la propiedad documentoEni.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoEni }
     *     
     */
    public void setDocumentoEni(DocumentoEni value) {
        this.documentoEni = value;
    }

    /**
     * Obtiene el valor de la propiedad idEni.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdEni() {
        return idEni;
    }

    /**
     * Define el valor de la propiedad idEni.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdEni(String value) {
        this.idEni = value;
    }
	
}
