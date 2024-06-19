package es.caib.interdoc.plugins.arxiu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
	private String numeroRegistre;

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
	
	public DocumentInfo(String nom, List<String> organs, List<String> interessats, Map<String, Object> metadades,
			String origen, String estatElaboracio, String tipusDocumental, Fitxer fitxer, InfoSignaturaDTO signatura,
			SignaturaArxiu firma, String numeroRegistre) {
		super();
		this.nom = nom;
		this.organs = organs;
		this.interessats = interessats;
		this.metadades = metadades;
		this.origen = origen;
		this.estatElaboracio = estatElaboracio;
		this.tipusDocumental = tipusDocumental;
		this.fitxer = fitxer;
		this.signatura = signatura;
		this.firma = firma;
		this.numeroRegistre = numeroRegistre;
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
	
	public String getNumeroRegistre() {
		return numeroRegistre;
	}
	
	public void setNumeroRegistre(String numeroRegistre) {
		this.numeroRegistre = numeroRegistre;
	}
	

	@Override
	public String toString() {
		
		 String objeto = "nom=" + nom;
		 
		if (organs != null) {
			objeto += ", organs=" + Arrays.toString(organs.toArray());
		}
		
		if (interessats != null) {
			objeto += ", interessats=" + Arrays.toString(interessats.toArray());
		}
		
		if (metadades != null) {
			objeto += ", metadades=[";
			for (Map.Entry<String, Object> entry : metadades.entrySet()) {
				objeto += "(" + entry.getKey() + "=" + entry.getValue() + "),";
			}
			objeto += "]";
		}
		
		if (origen != null) {
			objeto += ", origen=" + origen;
		}
		
		if (fitxer != null) {
			objeto += ", fitxer=[" + fitxer + "]";
		}
		
		if (signatura != null) {
			objeto += ", signatura=[" + signatura + "]";
		}
		
		if (firma != null) {
			objeto += ", firma=[" + firma + "]";
		}
		
		if (estatElaboracio != null) {
			objeto += ", estatElaboracio=" + estatElaboracio;
		}
		
		if (tipusDocumental != null) {
			objeto += ", tipusDocumental=" + tipusDocumental;
		}
		
		if (numeroRegistre != null) {
			objeto += ", numeroRegistre=" + numeroRegistre;
		}
		
		return "DocumentInfo [" + objeto + "]";
		
	}
	
	
}
