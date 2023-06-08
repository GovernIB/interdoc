package es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta;

import javax.xml.bind.annotation.XmlElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrazaBean {
	
	private static Logger log = LoggerFactory.getLogger(TrazaBean.class.getName());
	
	String nombre = "";
	String valor = "";
	
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