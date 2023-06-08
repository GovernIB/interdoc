package es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestringidoBean {
	
	private static Logger log = LoggerFactory.getLogger(RestringidoBean.class.getName());
	
	String empleadoPublico = "";
	String textContent = "";
	RestringidoAplicacionesBean restringidoAplicacionesBean;
	List<RestringidoNifBean> restringidoNifBeanList;
	RestringidoPorIdentificacionBean restringidoPorIdentificacionBean;

	public void setEmpleadoPublico(String empleadoPublico) {
		this.empleadoPublico = empleadoPublico;
	}

	@XmlElement(name = "EmpleadoPublico")
	public String getEmpleadoPublico() {
		return empleadoPublico;
	}

	@XmlElement(name = "RestringidoAplicaciones")
	public RestringidoAplicacionesBean getRestringidoAplicacionesBean() {
		if (restringidoAplicacionesBean == null)
			restringidoAplicacionesBean = new RestringidoAplicacionesBean();
		return restringidoAplicacionesBean;
	}

	public void setRestringidoAplicacionesBean(RestringidoAplicacionesBean restringidoAplicacionesBean) {
		this.restringidoAplicacionesBean = restringidoAplicacionesBean;
	}

	public void setRestringidoNifBeanList(List<RestringidoNifBean> restringidoNifBeanList) {
		this.restringidoNifBeanList = restringidoNifBeanList;
	}

	@XmlElement(name = "RestringidoNif")
	public List<RestringidoNifBean> getRestringidoNifBeanList() {
		if (restringidoNifBeanList == null)
			restringidoNifBeanList = new ArrayList<RestringidoNifBean>();
		return restringidoNifBeanList;
	}

	@XmlElement(name = "RestringidoPorIdentificacion")
	public RestringidoPorIdentificacionBean getRestringidoPorIdentificacionBean() {
		if (restringidoPorIdentificacionBean == null)
			restringidoPorIdentificacionBean = new RestringidoPorIdentificacionBean();
		return restringidoPorIdentificacionBean;
	}

	public void setRestringidoPorIdentificacionBean(RestringidoPorIdentificacionBean restringidoPorIdentificacionBean) {
		this.restringidoPorIdentificacionBean = restringidoPorIdentificacionBean;
	}

	@Override
	public String toString() {
		if (log.isDebugEnabled()) {
			StringBuilder builder = new StringBuilder();
			builder.append("\n restringidoNifBeanList=" + restringidoNifBeanList);
			builder.append("\n restringidoAplicacionesBean=" + restringidoAplicacionesBean);
			builder.append("\n restringidoPorIdentificacionBean=" + restringidoPorIdentificacionBean);
			builder.append("\n empleadoPublico=" + empleadoPublico);
			return builder.toString();
		} else {
			return "restringidoNifBeanList=" + restringidoNifBeanList;
		}
	}
}