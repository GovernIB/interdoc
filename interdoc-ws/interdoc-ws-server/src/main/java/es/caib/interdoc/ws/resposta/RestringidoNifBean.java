package es.caib.interdoc.ws.resposta;

import javax.xml.bind.annotation.XmlElement;
import org.apache.log4j.Logger;

public class RestringidoNifBean {
	private static Logger log = Logger.getLogger(RestringidoNifBean.class.getName());
	
	String nif = null;

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