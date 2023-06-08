package es.caib.interdoc.api.interna.secured.descargardocumento.resposta;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Clase Java para response complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "response", propOrder = {
    "codigo",
    "descripcion"
})

public class Response {
	
	 	protected String codigo;
	    protected String descripcion;

	    public String getCodigo() {
	        return codigo;
	    }

	    public void setCodigo(String value) {
	        this.codigo = value;
	    }

	    public String getDescripcion() {
	        return descripcion;
	    }

	    public void setDescripcion(String value) {
	        this.descripcion = value;
	    }

		@Override
		public String toString() {
			return "Response [codigo=" + codigo + ", descripcion=" + descripcion + "]";
		}

		public Response() {
			super();
		}

		public Response(String codigo, String descripcion) {
			super();
			this.codigo = codigo;
			this.descripcion = descripcion;
		}

}
