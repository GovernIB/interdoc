package es.caib.interdoc.api.interna.ws.utils;

public class Firma {
	
	protected String format;
	protected String perfil;
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
	
	public Firma() {
		super();
	}

	public Firma(String format, String perfil, Fitxer fitxer) {
		super();
		this.format = format;
		this.perfil = perfil;
		this.fitxer = fitxer;
	}

	@Override
	public String toString() {
		return "Firma [format=" + format + ", perfil=" + perfil + ", fitxer=" + fitxer + "]";
	}
	
}
