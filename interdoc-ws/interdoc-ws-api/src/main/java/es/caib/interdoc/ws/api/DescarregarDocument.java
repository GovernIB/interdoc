
package es.caib.interdoc.ws.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para descarregarDocument complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="descarregarDocument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="referencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="usuari" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="clau" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "descarregarDocument", propOrder = {
    "referencia",
    "usuari",
    "clau"
})
public class DescarregarDocument {

    protected String referencia;
    protected String usuari;
    protected String clau;

    /**
     * Obtiene el valor de la propiedad referencia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * Define el valor de la propiedad referencia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencia(String value) {
        this.referencia = value;
    }

    /**
     * Obtiene el valor de la propiedad usuari.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsuari() {
        return usuari;
    }

    /**
     * Define el valor de la propiedad usuari.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuari(String value) {
        this.usuari = value;
    }

    /**
     * Obtiene el valor de la propiedad clau.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClau() {
        return clau;
    }

    /**
     * Define el valor de la propiedad clau.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClau(String value) {
        this.clau = value;
    }

}
