package es.caib.interdoc.api.interna.secured.descargardocumento.resposta;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Clase Java para ContenidoMtomInfo complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
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
@XmlType(
		name = "ContenidoMtomInfo", 
		propOrder = { "contenido", "tipoMIME" }
)
public class ContenidoMtomInfo {
	
	@XmlElement(required = true)
    @XmlMimeType("application/octet-stream")
    protected DataHandler contenido;
    protected String tipoMIME;
    
    public DataHandler getContenido() {
        return contenido;
    }
    
    public void setContenido(DataHandler value) {
        this.contenido = value;
    }
    
    public String getTipoMIME() {
        return tipoMIME;
    }
    
    public void setTipoMIME(String value) {
        this.tipoMIME = value;
    }

}
