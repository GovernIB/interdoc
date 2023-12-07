package es.caib.interdoc.api.interna.ws.resposta;

import javax.xml.bind.annotation.XmlElement;
import org.apache.log4j.Logger;

public class TrazaBean {
	
	private static Logger log = Logger.getLogger(TrazaBean.class.getName());
	
	String nombre = null;
	String valor = null;
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@XmlElement(name = "Nombre")
	public String getNombre() {
		return nombre;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@XmlElement(name = "Valor")
	public String getValor() {
		return valor;
	}

	@Override
	public String toString() {
		if (log.isDebugEnabled()) {
			StringBuilder builder = new StringBuilder();
			builder.append("\n nombre=" + nombre);
			builder.append("\n valor=" + valor);
			return builder.toString();
		} else {
			return "nombre=" + nombre;
		}
	}
}