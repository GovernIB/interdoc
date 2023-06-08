package es.caib.interdoc.ws.resposta;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class RestringidoBean {
	
	private static Logger log = Logger.getLogger(RestringidoBean.class.getName());
	
	String empleadoPublico = null;
	String textContent = null;
	RestringidoAplicacionesBean restringidoAplicacionesBean =null;
	List<RestringidoNifBean> restringidoNifBeanList = null;
	RestringidoPorIdentificacionBean restringidoPorIdentificacionBean = null;

	public void setEmpleadoPublico(String empleadoPublico) {
		this.empleadoPublico = empleadoPublico;
	}

	@XmlElement(name = "EmpleadoPublico")
	public String getEmpleadoPublico() {
		return empleadoPublico;
	}

	@XmlElement(name = "RestringidoAplicaciones")
	public RestringidoAplicacionesBean getRestringidoAplicacionesBean() {
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
		return restringidoNifBeanList;
	}

	@XmlElement(name = "RestringidoPorIdentificacion")
	public RestringidoPorIdentificacionBean getRestringidoPorIdentificacionBean() {
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