package es.caib.interdoc.api.interna.ws.utils;

public class Fitxer {
	
	protected byte[] data;
    protected String descripcio;
    protected String mime;
    protected String nom;
    protected long tamany = -1;
    
    public Fitxer() {
    	super();
    }
    
	public Fitxer(byte[] data, String descripcio, String encryptedFileID, long fitxerID, String mime, String nom,
			long tamany) {
		super();
		this.data = data;
		this.descripcio = descripcio;
		this.mime = mime;
		this.nom = nom;
		this.tamany = tamany;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public String getDescripcio() {
		return descripcio;
	}
	
	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}
	
	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public long getTamany() {
		return tamany;
	}
	
	public void setTamany(long tamany) {
		this.tamany = tamany;
	}
    
    
}
