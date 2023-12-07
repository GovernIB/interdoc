package es.caib.interdoc.api.interna.ws.resposta;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

@XmlRootElement(name = "Error")
public class ErrorResponse {

	private static Logger log = Logger.getLogger(ErrorResponse.class.getName());
	
	String codigo = null;
	String descripcion = null;
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ErrorResponse() {
		super();
	}

	public ErrorResponse(String codigo, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("\n codigo=" + codigo);
			builder.append("\n descripcion=" + descripcion);
			return builder.toString();
	}
}