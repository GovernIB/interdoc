package es.caib.interdoc.api.externa.all.dades;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Estructura de dades per mostrar la informació de les referencies de INTERDOC
 * pel servei de reutilització de dades
 * 
 * @author jagarcia
 *
 */

@Schema(name = "Referencia")
public class ReferenciaStat {
	
	private String referenciaId;
	private String isCSV;
	private String isUUID;
	private String emisor;
	private String receptor;
	private String dataCreacio;
	private String entitat;
	private String formatfirma;
	private int numAccesos;
	
	public String getReferenciaId() {
		return referenciaId;
	}

	public void setReferenciaId(String referenciaId) {
		this.referenciaId = referenciaId;
	}

	public String getIsCSV() {
		return isCSV;
	}

	public void setIsCSV(String isCSV) {
		this.isCSV = isCSV;
	}

	public String getIsUUID() {
		return isUUID;
	}

	public void setIsUUID(String isUUID) {
		this.isUUID = isUUID;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public String getReceptor() {
		return receptor;
	}

	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}

	public String getDataCreacio() {
		return dataCreacio;
	}

	public void setDataCreacio(String dataCreacio) {
		this.dataCreacio = dataCreacio;
	}

	public String getEntitat() {
		return entitat;
	}

	public void setEntitat(String entitat) {
		this.entitat = entitat;
	}

	public String getFormatfirma() {
		return formatfirma;
	}

	public void setFormatfirma(String formatfirma) {
		this.formatfirma = formatfirma;
	}

	public int getNumAccesos() {
		return numAccesos;
	}

	public void setNumAccesos(int numAccesos) {
		this.numAccesos = numAccesos;
	}

	public ReferenciaStat() {
		super();
	}
	
	public ReferenciaStat(String referenciaId, String isCSV, String isUUID, String emisor, String receptor,
			String dataCreacio, String entitat, String formatfirma, String tipusfirma, int numAccesos) {
		super();
		this.referenciaId = referenciaId;
		this.isCSV = isCSV;
		this.isUUID = isUUID;
		this.emisor = emisor;
		this.receptor = receptor;
		this.dataCreacio = dataCreacio;
		this.entitat = entitat;
		this.formatfirma = formatfirma;
		this.numAccesos = numAccesos;
	}

	@Override
	public String toString() {
		return "ReferenciaStat [referenciaId=" + referenciaId + ", isCSV=" + isCSV + ", isUUID=" + isUUID + ", emisor="
				+ emisor + ", receptor=" + receptor + ", dataCreacio=" + dataCreacio + ", entitat=" + entitat
				+ ", formatfirma=" + formatfirma + ", numAccesos=" + numAccesos + "]";
	}
	
	

}
