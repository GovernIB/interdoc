package es.caib.interdoc.api.interna.secured.descargardocumento.request;

public class DocumentoRequest {

	private String nombre;
	private String documento;
	private String mime;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public DocumentoRequest(String nombre, String documento, String mime) {
		super();
		this.nombre = nombre;
		this.documento = documento;
		this.mime = mime;
	}

	public DocumentoRequest() {
		super();
	}

}
