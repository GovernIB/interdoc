package es.caib.interdoc.plugins.arxiu;

public class SignaturaArxiu {

	private String formatFirma;
	private String perfilFirma;
	private Fitxer firma;
	public String getFormatFirma() {
		return formatFirma;
	}
	public void setFormatFirma(String formatFirma) {
		this.formatFirma = formatFirma;
	}
	public String getPerfilFirma() {
		return perfilFirma;
	}
	public void setPerfilFirma(String perfilFirma) {
		this.perfilFirma = perfilFirma;
	}
	public Fitxer getFirma() {
		return firma;
	}
	public void setFirma(Fitxer firma) {
		this.firma = firma;
	}
	public SignaturaArxiu(String formatFirma, String perfilFirma, Fitxer firma) {
		super();
		this.formatFirma = formatFirma;
		this.perfilFirma = perfilFirma;
		this.firma = firma;
	}
	@Override
	public String toString() {
		return "SignaturaArxiu [formatFirma=" + formatFirma + ", perfilFirma=" + perfilFirma + ", firma=" + firma + "]";
	}
	public SignaturaArxiu() {
		super();
	}
	
}
