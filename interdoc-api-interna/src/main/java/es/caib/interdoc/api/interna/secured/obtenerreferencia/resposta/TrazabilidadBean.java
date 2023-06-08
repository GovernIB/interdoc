package es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta;

import javax.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrazabilidadBean {
	
	private static Logger log = LoggerFactory.getLogger(TrazabilidadBean.class.getName());

	List<TrazaBean> trazaBeanList;

	public void setTrazaBeanList(List<TrazaBean> trazaBeanList) {
		this.trazaBeanList = trazaBeanList;
	}

	@XmlElement(name = "Traza")
	public List<TrazaBean> getTrazaBeanList() {
		if (trazaBeanList == null)
			trazaBeanList = new ArrayList<TrazaBean>();
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