package es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta;

import javax.xml.bind.annotation.XmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceptorBean {
	
	private static Logger log = LoggerFactory.getLogger(ReceptorBean.class.getName());
	
	String organismo = "";

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