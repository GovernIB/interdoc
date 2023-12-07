package es.caib.interdoc.api.interna.ws.resposta;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class TrazabilidadBean {
	
	private static Logger log = Logger.getLogger(TrazabilidadBean.class.getName());

	List<TrazaBean> trazaBeanList = null;

	public void setTrazaBeanList(List<TrazaBean> trazaBeanList) {
		this.trazaBeanList = trazaBeanList;
	}

	@XmlElement(name = "Traza")
	public List<TrazaBean> getTrazaBeanList() {;
		return trazaBeanList;
	}

	@Override
	public String toString() {
		if (log.isDebugEnabled()) {
			StringBuilder builder = new StringBuilder();
			builder.append("\n trazaBeanList=" + trazaBeanList);
			return builder.toString();
		} else {
			return "trazaBeanList=" + trazaBeanList;
		}
	}
}