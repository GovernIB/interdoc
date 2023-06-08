
package es.caib.interdoc.ws.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para metadada complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="metadada"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="clau" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tipus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="valor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "metadada", propOrder = {
    "clau",
    "tipus",
    "valor"
})
public class Metadada {

    protected String clau;
    protected String tipus;
    protected String valor;

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

    /**
     * Obtiene el valor de la propiedad tipus.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipus() {
        return tipus;
    }

    /**
     * Define el valor de la propiedad tipus.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipus(String value) {
        this.tipus = value;
    }

    /**
     * Obtiene el valor de la propiedad valor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValor() {
        return valor;
    }

    /**
     * Define el valor de la propiedad valor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValor(String value) {
        this.valor = value;
    }

}
