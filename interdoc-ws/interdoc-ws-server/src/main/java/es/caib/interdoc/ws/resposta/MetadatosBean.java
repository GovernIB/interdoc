package es.caib.interdoc.ws.resposta;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class MetadatosBean {
	
	private static Logger log = Logger.getLogger(MetadatosBean.class.getName());

	List<MetadatoBean> metadatoBeanList = null;

	public void setMetadatoBeanList(List<MetadatoBean> metadatoBeanList) {
		this.metadatoBeanList = metadatoBeanList;
	}

	@XmlElement(name = "Metadato")
	public List<MetadatoBean> getMetadatoBeanList() {
		return metadatoBeanList;
	}

	@Override
	public String toString() {
		if (log.isDebugEnabled()) {
			StringBuilder builder = new StringBuilder();
			builder.append("\n metadatoBeanList=" + metadatoBeanList);
			return builder.toString();
		} else {
			return "metadatoBeanList=" + metadatoBeanList;
		}
	}
}