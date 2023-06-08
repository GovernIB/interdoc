package es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PermisoBean {
	
	private static Logger log = LoggerFactory.getLogger(PermisoBean.class.getName());
	
	String privado = "";
	String publico = "";

	RestringidoBean restringidoBean;

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
		if (restringidoBean == null)
			restringidoBean = new RestringidoBean();
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