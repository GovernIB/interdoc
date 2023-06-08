package es.caib.interdoc.ws.resposta;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;

import org.apache.log4j.Logger;

public class PermisoBean {
	
	private static Logger log = Logger.getLogger(PermisoBean.class.getName());
	
	String privado = null;
	String publico = null;

	RestringidoBean restringidoBean = null;

	public void setPrivado(String privado) {
		this.privado = privado;
	}

	@XmlElement(name = "Privado")
	public String getPrivado() {
		return privado;
	}

	public void setPublico(String publico) {
		this.publico = publico;
	}

	@XmlElement(name = "Publico")
	public String getPublico() {
		return publico;
	}

	@XmlElement(name = "Restringido")
	public RestringidoBean getRestringidoBean() {
		return restringidoBean;
	}

	public void setRestringidoBean(RestringidoBean restringidoBean) {
		this.restringidoBean = restringidoBean;
	}

	@Override
	public String toString() {
		if (log.isDebugEnabled()) {
			StringBuilder builder = new StringBuilder();
			builder.append("\n privado=" + privado);
			builder.append("\n publico=" + publico);
			builder.append("\n restringidoBean=" + restringidoBean);
			return builder.toString();
		} else {
			return "privado=" + privado;
		}
	}
}