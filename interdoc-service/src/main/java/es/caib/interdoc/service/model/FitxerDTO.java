package es.caib.interdoc.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Dades referents a un fitxer.
 *
 * @author jagarcia
 */
@Schema(name = "Fitxer")
public class FitxerDTO {

    private Long id;

    @NotEmpty
    @Size(max = 255)
    private String nom;
    
    @Size(max = 1000)
    private String descripcio;
    
    @NotEmpty
    @Size(max = 255)
    private String ruta;
    
    private byte[] data;
    
    @NotEmpty
    @Size(max = 255)
    private String mime;

    @NotNull
    @PastOrPresent
    private LocalDate dataCreacio;

    private Long tamany;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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
	
	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public LocalDate getDataCreacio() {
		return dataCreacio;
	}

	public void setDataCreacio(LocalDate dataCreacio) {
		this.dataCreacio = dataCreacio;
	}

	public Long getTamany() {
		return tamany;
	}

	public void setTamany(Long tamany) {
		this.tamany = tamany;
	}
	
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public FitxerDTO() {
		
	}

	public FitxerDTO(Long id, @NotEmpty @Size(max = 255) String nom, @Size(max = 1000) String descripcio,
			@NotEmpty @Size(max = 255) String mime, Long tamany, @NotEmpty @Size(max = 255) String ruta) {
		super();
		this.id = id;
		this.nom = nom;
		this.ruta = ruta;
		this.descripcio = descripcio;
		this.mime = mime;
		this.tamany = tamany;
	}

	@Override
	public String toString() {
		return "FitxerDTO [id=" + id + ", nom=" + nom + ", descripcio=" + descripcio + ", mime=" + mime + ", ruta=" + ruta
				+ ", dataCreacio=" + dataCreacio + ", tamany=" + tamany + "]";
	}

	
	

}
