package es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadatosBean {
	
	private static Logger log = LoggerFactory.getLogger(MetadatosBean.class.getName());

	List<MetadatoBean> metadatoBeanList;

	public void setMetadatoBeanList(List<MetadatoBean> metadatoBeanList) {
		this.metadatoBeanList = metadatoBeanList;
	}

	@XmlElement(name = "Metadato")
	public List<MetadatoBean> getMetadatoBeanList() {
		if (metadatoBeanList == null)
			metadatoBeanList = new ArrayList<MetadatoBean>();
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