package es.caib.interdoc.api.interna.ws.resposta;

import javax.xml.bind.annotation.XmlElement;
import org.apache.log4j.Logger;

public class RestringidoAplicacionesBean {
	
	private static Logger log = Logger.getLogger(RestringidoAplicacionesBean.class.getName());
	
	String idAplicacion = null;

	public void setIdAplicacion(String idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	@XmlElement(name = "IdAplicacion")
	public String getIdAplicacion() {
		return idAplicacion;
	}

	@Override
	public String toString() {
		if (log.isDebugEnabled()) {
			StringBuilder builder = new StringBuilder();
			builder.append("\n idAplicacion=" + idAplicacion);
			return builder.toString();
		} else {
			return "idAplicacion=" + idAplicacion;
		}
	}
}