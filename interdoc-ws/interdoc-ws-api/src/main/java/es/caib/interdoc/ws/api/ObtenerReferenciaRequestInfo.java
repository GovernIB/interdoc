
package es.caib.interdoc.ws.api;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtenerReferenciaRequestInfo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="obtenerReferenciaRequestInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="aplicacioId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="csv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="document" type="{http://impl.ws.interna.api.interdoc.caib.es/}fitxer" minOccurs="0"/&gt;
 *         &lt;element name="emisor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="entitatId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="interessats" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="metadades" type="{http://impl.ws.interna.api.interdoc.caib.es/}metadada" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="receptor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerReferenciaRequestInfo", propOrder = {
    "aplicacioId",
    "csv",
    "document",
    "firma",
    "emisor",
    "entitatId",
    "interessats",
    "metadades",
    "receptor",
    "uuid",
    "origen",
    "estatElaboracio",
    "tipusDocumental"
})
public class ObtenerReferenciaRequestInfo {

    protected String aplicacioId;
    protected String csv;
    protected Fitxer document;
    @XmlElement(nillable = true)
    protected Firma firma;
    protected String emisor;
    protected String entitatId;
    @XmlElement(nillable = true)
    protected List<String> interessats;
    @XmlElement(nillable = true)
    protected List<Metadada> metadades;
    protected String receptor;
    protected String uuid;
    protected String origen;
    protected String estatElaboracio;
    protected String tipusDocumental;

    /**
     * Obtiene el valor de la propiedad aplicacioId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAplicacioId() {
        return aplicacioId;
    }

    /**
     * Define el valor de la propiedad aplicacioId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAplicacioId(String value) {
        this.aplicacioId = value;
    }

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
     * Obtiene el valor de la propiedad document.
     * 
     * @return
     *     possible object is
     *     {@link Fitxer }
     *     
     */
    public Fitxer getDocument() {
        return document;
    }

    /**
     * Define el valor de la propiedad document.
     * 
     * @param value
     *     allowed object is
     *     {@link Fitxer }
     *     
     */
    public void setDocument(Fitxer value) {
        this.document = value;
    }
    
    /**
     * Obtiene el valor de la propiedad firma.
     * 
     * @return
     *     possible object is
     *     {@link Firma }
     *     
     */
    public Firma getFirma() {
    	return firma;
    }

    /**
     * Define el valor de la propiedad firma.
     * 
     * @param value
     *     allowed object is
     *     {@link Firma }
     *     
     */
    public void setFirma(Firma value) {
    	this.firma = value;
    }
    
    /**
     * Obtiene el valor de la propiedad emisor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmisor() {
        return emisor;
    }

    /**
     * Define el valor de la propiedad emisor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmisor(String value) {
        this.emisor = value;
    }

    /**
     * Obtiene el valor de la propiedad entitatId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntitatId() {
        return entitatId;
    }

    /**
     * Define el valor de la propiedad entitatId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntitatId(String value) {
        this.entitatId = value;
    }

    /**
     * Gets the value of the interessats property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the interessats property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInteressats().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getInteressats() {
        if (interessats == null) {
            interessats = new ArrayList<String>();
        }
        return this.interessats;
    }

    /**
     * Gets the value of the metadades property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the metadades property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMetadades().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Metadada }
     * 
     * 
     */
    public List<Metadada> getMetadades() {
        if (metadades == null) {
            metadades = new ArrayList<Metadada>();
        }
        return this.metadades;
    }

    /**
     * Obtiene el valor de la propiedad receptor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceptor() {
        return receptor;
    }

    /**
     * Define el valor de la propiedad receptor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceptor(String value) {
        this.receptor = value;
    }

    /**
     * Obtiene el valor de la propiedad uuid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Define el valor de la propiedad uuid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getEstatElaboracio() {
		return estatElaboracio;
	}

	public void setEstatElaboracio(String estatElaboracio) {
		this.estatElaboracio = estatElaboracio;
	}

	public String getTipusDocumental() {
		return tipusDocumental;
	}

	public void setTipusDocumental(String tipusDocumental) {
		this.tipusDocumental = tipusDocumental;
	}

	public void setInteressats(List<String> interessats) {
		this.interessats = interessats;
	}

	public void setMetadades(List<Metadada> metadades) {
		this.metadades = metadades;
	}
    
    

}
