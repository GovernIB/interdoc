package es.caib.interdoc.ws.model;

import java.util.List;

import es.caib.interdoc.ws.utils.Fitxer;
import es.caib.interdoc.ws.utils.Metadada;

public class ObtenerReferenciaRequestInfo {

	private String csv;
	private String uuid;
	private Fitxer document;
	private List<Metadada> metadades;
	private String aplicacioId;

	private String emisor;
	private String receptor;
	private List<String> interessats;

	public String getCsv() {
		return csv;
	}

	public void setCsv(String csv) {
		this.csv = csv;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Fitxer getDocument() {
		return document;
	}

	public void setDocument(Fitxer document) {
		this.document = document;
	}

	public List<Metadada> getMetadades() {
		return metadades;
	}

	public void setMetadades(List<Metadada> metadades) {
		this.metadades = metadades;
	}

	public String getAplicacioId() {
		return aplicacioId;
	}

	public void setAplicacioId(String aplicacioId) {
		this.aplicacioId = aplicacioId;
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

	public List<String> getInteressats() {
		return interessats;
	}

	public void setInteressats(List<String> interessats) {
		this.interessats = interessats;
	}

	public ObtenerReferenciaRequestInfo() {
		super();
	}

	public ObtenerReferenciaRequestInfo(String csv, String uuid, Fitxer document, List<Metadada> metadades,
			String aplicacioId, String emisor, String receptor, List<String> interessats) {
		super();
		this.csv = csv;
		this.uuid = uuid;
		this.document = document;
		this.metadades = metadades;
		this.aplicacioId = aplicacioId;
		this.emisor = emisor;
		this.receptor = receptor;
		this.interessats = interessats;
	}

	@Override
	public String toString() {
		return "ObtenerReferenciaRequestInfo [csv=" + csv + ", uuid=" + uuid + ", document=" + document + ", metadades="
				+ metadades + ", aplicacioId=" + aplicacioId + ", emisor=" + emisor + ", receptor=" + receptor
				+ ", interessats=" + interessats + "]";
	}

}
