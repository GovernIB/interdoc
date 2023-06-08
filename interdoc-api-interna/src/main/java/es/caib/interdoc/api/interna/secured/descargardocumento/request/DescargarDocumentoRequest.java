package es.caib.interdoc.api.interna.secured.descargardocumento.request;

public class DescargarDocumentoRequest {

	private String csv;
	private String dir3;
	private String recuperacionOriginal;
	private String documentoEni;
	private String idEni;
	private String nif;
	private String tipoIdentificacion;
	private String ip;
	
	public String getCsv() {
		return csv;
	}
	public void setCsv(String csv) {
		this.csv = csv;
	}
	public String getDir3() {
		return dir3;
	}
	public void setDir3(String dir3) {
		this.dir3 = dir3;
	}
	public String getRecuperacionOriginal() {
		return recuperacionOriginal;
	}
	public void setRecuperacionOriginal(String recuperacionOriginal) {
		this.recuperacionOriginal = recuperacionOriginal;
	}
	public String getDocumentoEni() {
		return documentoEni;
	}
	public void setDocumentoEni(String documentoEni) {
		this.documentoEni = documentoEni;
	}
	public String getIdEni() {
		return idEni;
	}
	public void setIdEni(String idEni) {
		this.idEni = idEni;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Override
	public String toString() {
		return "DescargarDocumentoRequest [csv=" + csv + ", dir3=" + dir3 + ", recuperacionOriginal="
				+ recuperacionOriginal + ", documentoEni=" + documentoEni + ", idEni=" + idEni + ", nif=" + nif
				+ ", tipoIdentificacion=" + tipoIdentificacion + ", ip=" + ip + "]";
	}
	public DescargarDocumentoRequest(String csv, String dir3, String recuperacionOriginal, String documentoEni,
			String idEni, String nif, String tipoIdentificacion, String ip) {
		super();
		this.csv = csv;
		this.dir3 = dir3;
		this.recuperacionOriginal = recuperacionOriginal;
		this.documentoEni = documentoEni;
		this.idEni = idEni;
		this.nif = nif;
		this.tipoIdentificacion = tipoIdentificacion;
		this.ip = ip;
	}
	public DescargarDocumentoRequest() {
		super();
	}
	
	
	
}
