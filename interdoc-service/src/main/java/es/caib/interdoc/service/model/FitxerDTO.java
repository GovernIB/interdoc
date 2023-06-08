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
	
	public FitxerDTO() {
		
	}

	public FitxerDTO(Long id, @NotEmpty @Size(max = 255) String nom, @Size(max = 1000) String descripcio,
			@NotEmpty @Size(max = 255) String mime, Long tamany) {
		super();
		this.id = id;
		this.nom = nom;
		this.descripcio = descripcio;
		this.mime = mime;
		this.tamany = tamany;
	}

	@Override
	public String toString() {
		return "FitxerDTO [id=" + id + ", nom=" + nom + ", descripcio=" + descripcio + ", mime=" + mime
				+ ", dataCreacio=" + dataCreacio + ", tamany=" + tamany + "]";
	}

	
	

}
