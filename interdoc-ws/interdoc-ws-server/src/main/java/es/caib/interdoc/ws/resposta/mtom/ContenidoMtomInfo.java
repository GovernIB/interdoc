package es.caib.interdoc.ws.resposta.mtom;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ContenidoMtomInfo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ContenidoMtomInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="contenido" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="tipoMIME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContenidoMtomInfo", namespace = "urn:es:gob:aapp:csvstorage:webservices:documentmtom:model:v1.0", propOrder = {
    "contenido",
    "tipoMIME"
})
public class ContenidoMtomInfo {

    @XmlElement(required = true)
    @XmlMimeType("application/octet-stream")
    protected DataHandler contenido;
    protected String tipoMIME;

    /**
     * Obtiene el valor de la propiedad contenido.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    public DataHandler getContenido() {
        return contenido;
    }

    /**
     * Define el valor de la propiedad contenido.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    public void setContenido(DataHandler value) {
        this.contenido = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoMIME.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoMIME() {
        return tipoMIME;
    }

    /**
     * Define el valor de la propiedad tipoMIME.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoMIME(String value) {
        this.tipoMIME = value;
    }

}
