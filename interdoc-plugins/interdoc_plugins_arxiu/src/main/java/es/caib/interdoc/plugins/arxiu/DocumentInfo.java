package es.caib.interdoc.plugins.arxiu;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class DocumentInfo implements Serializable {
	
	private String nom; 
	private List<String> organs;
	private List<String> interessats;
	private Map<String,Object> metadades; 
	private Origen origen; 
	private Fitxer fitxer;

	public DocumentInfo() {
		super();
	}

	public DocumentInfo(String nom, List<String> organs, List<String> interessats,
			Map<String,Object> metadades, Origen origen, Fitxer fitxer) {
		super();
		this.nom = nom;
		this.organs = organs;
		this.interessats = interessats;
		this.metadades = metadades;
		this.origen = origen;
		this.fitxer = fitxer;
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

	public Origen getOrigen() {
		return origen;
	}

	public void setOrigen(Origen origen) {
		this.origen = origen;
	}

	public Fitxer getFitxer() {
		return fitxer;
	}

	public void setFitxer(Fitxer fitxer) {
		this.fitxer = fitxer;
	}

	@Override
	public String toString() {
		return "DocumentInfo [nom=" + nom + ", organs=" + organs + ", interessats=" + interessats
				+ ", metadades=" + metadades + ", origen=" + origen + ", fitxer=" + fitxer + "]";
	}
	
	
}
