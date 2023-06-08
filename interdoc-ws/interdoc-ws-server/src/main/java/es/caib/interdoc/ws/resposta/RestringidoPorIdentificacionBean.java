package es.caib.interdoc.ws.resposta;

import javax.xml.bind.annotation.XmlElement;
import org.apache.log4j.Logger;

public class RestringidoPorIdentificacionBean {
	
	private static Logger log = Logger.getLogger(RestringidoPorIdentificacionBean.class.getName());
	
	String identificacion = null;
	String textContent = null;

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