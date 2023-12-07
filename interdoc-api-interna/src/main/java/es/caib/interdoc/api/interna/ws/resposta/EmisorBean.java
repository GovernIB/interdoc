package es.caib.interdoc.api.interna.ws.resposta;

import javax.xml.bind.annotation.XmlElement;

import org.apache.log4j.Logger;

public class EmisorBean {
	
	private static Logger log = Logger.getLogger(EmisorBean.class.getName());
	
	String organismo = null;

	public void setOrganismo(String organismo) {
		this.organismo = organismo;
	}

	@XmlElement(name = "Organismo")
	public String getOrganismo() {
		return organismo;
	}

	@Override
	public String toString() {
		if (log.isDebugEnabled()) {
			StringBuilder builder = new StringBuilder();
			builder.append("\n organismo=" + organismo);
			return builder.toString();
		} else {
			return "organismo=" + organismo;
		}
	}
}