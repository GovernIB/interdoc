package es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta;

import javax.xml.bind.annotation.XmlElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestringidoPorIdentificacionBean {
	
	private static Logger log = LoggerFactory.getLogger(RestringidoPorIdentificacionBean.class.getName());
	
	String identificacion = "";
	String textContent = "";

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	@XmlElement(name = "Identificacion")
	public String getIdentificacion() {
		return identificacion;
	}

	@Override
	public String toString() {
		if (log.isDebugEnabled()) {
			StringBuilder builder = new StringBuilder();
			builder.append("\n identificacion=" + identificacion);
			return builder.toString();
		} else {
			return "identificacion=" + identificacion;
		}
	}
}