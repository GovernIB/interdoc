package es.caib.interdoc.plugins.arxiu;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import es.caib.interdoc.service.model.InfoSignaturaDTO;


public class DocumentInfo implements Serializable {
	
	private static final long serialVersionUID = -4826465796539140766L;
	
	private String nom; 
	private List<String> organs;
	private List<String> interessats;
	private Map<String,Object> metadades; 
	private String origen;
	private String estatElaboracio;
	private String tipusDocumental;
	private Fitxer fitxer;
	private InfoSignaturaDTO signatura;
	private SignaturaArxiu firma;

	public DocumentInfo() {
		super();
	}

	public DocumentInfo(String nom, List<String> organs, List<String> interessats,
			Map<String,Object> metadades, String origen, Fitxer fitxer) {
		super();
		this.nom = nom;
		this.organs = organs;
		this.interessats = interessats;
		this.metadades = metadades;
		this.origen = origen;
		this.fitxer = fitxer;
	}

	public DocumentInfo(String nom, List<String> organs, List<String> interessats, Map<String, Object> metadades,
			String origen, Fitxer fitxer, InfoSignaturaDTO signatura, SignaturaArxiu firma) {
		super();
		this.nom = nom;
		this.organs = organs;
		this.interessats = interessats;
		this.metadades = metadades;
		this.origen = origen;
		this.fitxer = fitxer;
		this.signatura = signatura;
		this.firma = firma;
	}

	public SignaturaArxiu getFirma() {
		return firma;
	}

	public void setFirma(SignaturaArxiu firma) {
		this.firma = firma;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<String> getOrgans() {
		return organs;
	}

	public void setOrgans(List<String> organs) {
		this.organs = organs;
	}

	public List<String> getInteressats() {
		return interessats;
	}

	public void setInteressats(List<String> interessats) {
		this.interessats = interessats;
	}

	public Map<String,Object> getMetadades() {
		return metadades;
	}

	public void setMetadades(Map<String,Object> metadades) {
		this.metadades = metadades;
	}

	public InfoSignaturaDTO getSignatura() {
		return signatura;
	}

	public void setSignatura(InfoSignaturaDTO signatura) {
		this.signatura = signatura;
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

	public Fitxer getFitxer() {
		return fitxer;
	}

	public void setFitxer(Fitxer fitxer) {
		this.fitxer = fitxer;
	}

	@Override
	public String toString() {
		return "DocumentInfo [nom=" + nom + ", organs=" + organs + ", interessats=" + interessats + ", metadades="
				+ metadades + ", origen=" + origen + ", fitxer=" + fitxer + ", signatura=" + signatura + ", firma="
				+ firma + "]";
	}
	
	
}
