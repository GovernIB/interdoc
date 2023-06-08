package es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name = "ReferenciaDocumento")

public class ReferenciaDocumentoBean {

	private static Logger log = LoggerFactory.getLogger(ReferenciaDocumentoBean.class.getName());
	
	String direccion = "";
	String hash = "";
	String uRLVisible = "";
	String formatoFirma = "";
	EmisorBean emisorBean;
	IdentificadorBean identificadorBean;
	MetadatosBean metadatosBean;
	PermisoBean permisoBean;
	ReceptorBean receptorBean;
	TrazabilidadBean trazabilidadBean;

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
		if (emisorBean == null)
			emisorBean = new EmisorBean();
		return emisorBean;
	}

	public void setEmisorBean(EmisorBean emisorBean) {
		this.emisorBean = emisorBean;
	}

	@XmlElement(name = "Identificador")
	public IdentificadorBean getIdentificadorBean() {
		if (identificadorBean == null)
			identificadorBean = new IdentificadorBean();
		return identificadorBean;
	}

	public void setIdentificadorBean(IdentificadorBean identificadorBean) {
		this.identificadorBean = identificadorBean;
	}

	@XmlElement(name = "Metadatos")
	public MetadatosBean getMetadatosBean() {
		if (metadatosBean == null)
			metadatosBean = new MetadatosBean();
		return metadatosBean;
	}

	public void setMetadatosBean(MetadatosBean metadatosBean) {
		this.metadatosBean = metadatosBean;
	}

	@XmlElement(name = "Permiso")
	public PermisoBean getPermisoBean() {
		if (permisoBean == null)
			permisoBean = new PermisoBean();
		return permisoBean;
	}

	public void setPermisoBean(PermisoBean permisoBean) {
		this.permisoBean = permisoBean;
	}

	@XmlElement(name = "Receptor")
	public ReceptorBean getReceptorBean() {
		if (receptorBean == null)
			receptorBean = new ReceptorBean();
		return receptorBean;
	}

	public void setReceptorBean(ReceptorBean receptorBean) {
		this.receptorBean = receptorBean;
	}

	@XmlElement(name = "eEMGDE.Trazabilidad")
	public TrazabilidadBean getTrazabilidadBean() {
		if (trazabilidadBean == null)
			trazabilidadBean = new TrazabilidadBean();
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