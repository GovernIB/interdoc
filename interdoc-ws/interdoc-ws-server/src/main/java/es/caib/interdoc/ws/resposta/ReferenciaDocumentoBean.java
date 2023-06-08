package es.caib.interdoc.ws.resposta;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.log4j.Logger;

@XmlRootElement(name = "ReferenciaDocumento")
@XmlType(
		propOrder = { "identificadorBean", "permisoBean", "formatoFirma", "hash", "direccion", "URLVisible", "emisorBean", "receptorBean", "metadatosBean", "trazabilidadBean" }
		)
public class ReferenciaDocumentoBean {

	private static Logger log = Logger.getLogger(ReferenciaDocumentoBean.class.getName());
	
	String direccion = null;
	String hash = null;
	String uRLVisible = null;
	String formatoFirma = null;
	EmisorBean emisorBean = null;
	IdentificadorBean identificadorBean = null;
	MetadatosBean metadatosBean = null;
	PermisoBean permisoBean = null;
	ReceptorBean receptorBean = null;
	TrazabilidadBean trazabilidadBean = null;

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@XmlElement(name = "Direccion")
	public String getDireccion() {
		return direccion;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@XmlElement(name = "Hash")
	public String getHash() {
		return hash;
	}

	public void setURLVisible(String uRLVisible) {
		this.uRLVisible = uRLVisible;
	}

	@XmlElement(name = "URLVisible")
	public String getURLVisible() {
		return uRLVisible;
	}

	public void setFormatoFirma(String formatoFirma) {
		this.formatoFirma = formatoFirma;
	}

	@XmlElement(name = "eEMGDE.Firma.TipoFirma.FormatoFirma")
	public String getFormatoFirma() {
		return formatoFirma;
	}

	@XmlElement(name = "Emisor")
	public EmisorBean getEmisorBean() {
		return emisorBean;
	}

	public void setEmisorBean(EmisorBean emisorBean) {
		this.emisorBean = emisorBean;
	}

	@XmlElement(name = "Identificador")
	public IdentificadorBean getIdentificadorBean() {
		return identificadorBean;
	}

	public void setIdentificadorBean(IdentificadorBean identificadorBean) {
		this.identificadorBean = identificadorBean;
	}

	@XmlElement(name = "Metadatos")
	public MetadatosBean getMetadatosBean() {
		return metadatosBean;
	}

	public void setMetadatosBean(MetadatosBean metadatosBean) {
		this.metadatosBean = metadatosBean;
	}

	@XmlElement(name = "Permiso")
	public PermisoBean getPermisoBean() {
		return permisoBean;
	}

	public void setPermisoBean(PermisoBean permisoBean) {
		this.permisoBean = permisoBean;
	}

	@XmlElement(name = "Receptor")
	public ReceptorBean getReceptorBean() {
		return receptorBean;
	}

	public void setReceptorBean(ReceptorBean receptorBean) {
		this.receptorBean = receptorBean;
	}

	@XmlElement(name = "eEMGDE.Trazabilidad")
	public TrazabilidadBean getTrazabilidadBean() {
		return trazabilidadBean;
	}

	public void setTrazabilidadBean(TrazabilidadBean trazabilidadBean) {
		this.trazabilidadBean = trazabilidadBean;
	}

	@Override
	public String toString() {
		if (log.isDebugEnabled()) {
			StringBuilder builder = new StringBuilder();
			builder.append("\n emisorBean=" + emisorBean);
			builder.append("\n uRLVisible=" + uRLVisible);
			builder.append("\n metadatosBean=" + metadatosBean);
			builder.append("\n direccion=" + direccion);
			builder.append("\n identificadorBean=" + identificadorBean);
			builder.append("\n hash=" + hash);
			builder.append("\n formatoFirma=" + formatoFirma);
			builder.append("\n permisoBean=" + permisoBean);
			builder.append("\n receptorBean=" + receptorBean);
			builder.append("\n trazabilidadBean=" + trazabilidadBean);
			return builder.toString();
		} else {
			return "emisorBean=" + emisorBean;
		}
	}
}