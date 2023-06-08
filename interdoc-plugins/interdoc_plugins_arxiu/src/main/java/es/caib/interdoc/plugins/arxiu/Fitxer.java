package es.caib.interdoc.plugins.arxiu;

public class Fitxer {

	private byte[] contingut;
	private long tamany = -1;
	private String tipusMime;
	private String arxiuNom;
	private Format format;
	private Extensio extensio;
	
	public byte[] getContingut() {
		return contingut;
	}
	public void setContingut(byte[] contingut) {
		this.contingut = contingut;
	}
	public long getTamany() {
		return tamany;
	}
	public void setTamany(long tamany) {
		this.tamany = tamany;
	}
	public String getTipusMime() {
		return tipusMime;
	}
	public void setTipusMime(String tipusMime) {
		this.tipusMime = tipusMime;
	}
	public String getArxiuNom() {
		return arxiuNom;
	}
	public void setArxiuNom(String arxiuNom) {
		this.arxiuNom = arxiuNom;
	}
	public Format getFormat() {
		return format;
	}
	public void setFormat(Format format) {
		this.format = format;
	}
	public Extensio getExtensio() {
		return extensio;
	}
	public void setExtensio(Extensio extensio) {
		this.extensio = extensio;
	}
	public Fitxer(byte[] contingut, long tamany, String tipusMime, String arxiuNom, Format format,
			Extensio extensio) {
		super();
		this.contingut = contingut;
		this.tamany = tamany;
		this.tipusMime = tipusMime;
		this.arxiuNom = arxiuNom;
		this.format = format;
		this.extensio = extensio;
	}
	public Fitxer() {
		super();
	}
	
	
}
