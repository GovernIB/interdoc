package es.caib.interdoc.ws.resposta.mtom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para documentoMtomResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="documentoMtomResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{urn:es:gob:aapp:csvstorage:webservices:document:model:v1.0}response"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="contenido" type="{urn:es:gob:aapp:csvstorage:webservices:documentmtom:model:v1.0}ContenidoMtomInfo" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "documentoMtomResponse", namespace = "urn:es:gob:aapp:csvstorage:webservices:documentmtom:model:v1.0", propOrder = {
    "contenido"
})
public class DocumentoMtomResponse
    extends Response
{

    protected ContenidoMtomInfo contenido;

    /**
     * Obtiene el valor de la propiedad contenido.
     * 
     * @return
     *     possible object is
     *     {@link ContenidoMtomInfo }
     *     
     */
    public ContenidoMtomInfo getContenido() {
        return contenido;
    }

    /**
     * Define el valor de la propiedad contenido.
     * 
     * @param value
     *     allowed object is
     *     {@link ContenidoMtomInfo }
     *     
     */
    public void setContenido(ContenidoMtomInfo value) {
        this.contenido = value;
    }

}
