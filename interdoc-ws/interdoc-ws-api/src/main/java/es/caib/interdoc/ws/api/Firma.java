package es.caib.interdoc.ws.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="firma", propOrder= {
		"format",
		"perfil",
		"fitxer"
})
public class Firma {
	
	protected String format;
	protected String perfil;
	@XmlElement(nillable = true)
	protected Fitxer fitxer;
	
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public Fitxer getFitxer() {
		return fitxer;
	}
	public void setFitxer(Fitxer fitxer) {
		this.fitxer = fitxer;
	}
	
}
