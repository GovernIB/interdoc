package es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta;

import javax.xml.bind.annotation.XmlElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestringidoAplicacionesBean {
	
	private static Logger log = LoggerFactory.getLogger(RestringidoAplicacionesBean.class.getName());
	
	String idAplicacion = "";

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