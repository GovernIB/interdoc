package es.caib.interdoc.ws.resposta;

import javax.xml.bind.annotation.XmlElement;
import org.apache.log4j.Logger;

public class ReceptorBean {
	
	private static Logger log = Logger.getLogger(ReceptorBean.class.getName());
	
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