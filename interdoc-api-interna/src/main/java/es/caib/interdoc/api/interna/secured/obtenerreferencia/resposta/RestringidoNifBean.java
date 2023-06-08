package es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta;

import javax.xml.bind.annotation.XmlElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestringidoNifBean {
	private static Logger log = LoggerFactory.getLogger(RestringidoNifBean.class.getName());
	
	String nif = "";

	public void setNif(String nif) {
		this.nif = nif;
	}

	@XmlElement(name = "Nif")
	public String getNif() {
		return nif;
	}

	@Override
	public String toString() {
		if (log.isDebugEnabled()) {
			StringBuilder builder = new StringBuilder();
			builder.append("\n nif=" + nif);
			return builder.toString();
		} else {
			return "nif=" + nif;
		}
	}
}