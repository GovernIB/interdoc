package es.caib.interdoc.api.interna.ws.model;

import java.util.List;

import es.caib.interdoc.api.interna.ws.utils.Fitxer;
import es.caib.interdoc.api.interna.ws.utils.Firma;
import es.caib.interdoc.api.interna.ws.utils.Metadada;

/**
 * 
 * Informació necessaria per realitzar la operació d'obtenir una referencia
 * 
 * @author jagarcia
 *
 */

public class ObtenerReferenciaRequestInfo {

	private String csv;
	private String uuid;
	private Fitxer document;
	private Firma firma;
	private List<Metadada> metadades;
	private String aplicacioId;
	private String entitatId;

	private String emisor;
	private String receptor;
	private String origen;
	private String estatElaboracio;
	private String tipusDocumental;
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

	public Firma getFirma() {
		return firma;
	}

	public void setFirma(Firma firma) {
		this.firma = firma;
	}
	
	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getEstatElaboracio() {
		return estatElaboracio;
	}

	public void setEstatElaboracio(String estatElaboracio) {
		this.estatElaboracio = estatElaboracio;
	}

	public String getTipusDocumental() {
		return tipusDocumental;
	}

	public void setTipusDocumental(String tipusDocumental) {
		this.tipusDocumental = tipusDocumental;
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

	public String getEntitatId() {
		return entitatId;
	}

	public void setEntitatId(String entitatId) {
		this.entitatId = entitatId;
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

	public ObtenerReferenciaRequestInfo(String csv, String uuid, Fitxer document, Firma firma, List<Metadada> metadades,
			String aplicacioId, String emisor, String receptor, List<String> interessats) {
		super();
		this.csv = csv;
		this.uuid = uuid;
		this.document = document;
		this.firma = firma;
		this.metadades = metadades;
		this.aplicacioId = aplicacioId;
		this.emisor = emisor;
		this.receptor = receptor;
		this.interessats = interessats;
	}

	public ObtenerReferenciaRequestInfo(String csv, String uuid, Fitxer document, Firma firma, List<Metadada> metadades,
			String aplicacioId, String entitatId, String emisor, String receptor, List<String> interessats) {
		super();
		this.csv = csv;
		this.uuid = uuid;
		this.document = document;
		this.firma = firma;
		this.metadades = metadades;
		this.aplicacioId = aplicacioId;
		this.entitatId = entitatId;
		this.emisor = emisor;
		this.receptor = receptor;
		this.interessats = interessats;
	}

	public ObtenerReferenciaRequestInfo(String csv, String uuid, Fitxer document, Firma firma, List<Metadada> metadades,
			String aplicacioId, String entitatId, String emisor, String receptor, String origen, String estatElaboracio,
			String tipusDocumental, List<String> interessats) {
		super();
		this.csv = csv;
		this.uuid = uuid;
		this.document = document;
		this.firma = firma;
		this.metadades = metadades;
		this.aplicacioId = aplicacioId;
		this.entitatId = entitatId;
		this.emisor = emisor;
		this.receptor = receptor;
		this.origen = origen;
		this.estatElaboracio = estatElaboracio;
		this.tipusDocumental = tipusDocumental;
		this.interessats = interessats;
	}

	@Override
	public String toString() {
		return "ObtenerReferenciaRequestInfo [csv=" + csv + ", uuid=" + uuid + ", document=" + document + ", firma="
				+ firma + ", metadades=" + metadades + ", aplicacioId=" + aplicacioId + ", entitatId=" + entitatId
				+ ", emisor=" + emisor + ", receptor=" + receptor + ", origen=" + origen + ", estatElaboracio="
				+ estatElaboracio + ", tipusDocumental=" + tipusDocumental + ", interessats=" + interessats + "]";
	}

}
