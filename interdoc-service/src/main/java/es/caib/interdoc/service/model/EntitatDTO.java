package es.caib.interdoc.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Dades referents a una entitat.
 *
 * @author jagarcia
 */
@Schema(name = "Entitat")
public class EntitatDTO {

	private Long id;

	@NotEmpty
	@Size(max = 50)
	private String nom;

	@NotNull
	private String codiDir3;

	@NotNull
	@PastOrPresent
	private LocalDate dataCreacio;

	@NotNull
	private Estat actiu;

	public Long getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public String getCodiDir3() {
		return codiDir3;
	}

	public LocalDate getDataCreacio() {
		return dataCreacio;
	}

	public Estat getActiu() {
		return actiu;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setCodiDir3(String codiDir3) {
		this.codiDir3 = codiDir3;
	}

	public void setDataCreacio(LocalDate dataCreacio) {
		this.dataCreacio = dataCreacio;
	}

	public void setActiu(Estat actiu) {
		this.actiu = actiu;
	}

	@Override
	public String toString() {
		return "EntitatDTO [id=" + id + ", nom=" + nom + ", codiDir3=" + codiDir3 + ", dataCreacio=" + dataCreacio
				+ ", actiu=" + actiu + "]";
	}

	public EntitatDTO() {
	}
	
	public EntitatDTO(Long id, @NotEmpty @Size(max = 50) String nom, @NotNull String codiDir3) {
		super();
		this.id = id;
		this.nom = nom;
		this.codiDir3 = codiDir3;
	}
	

	public EntitatDTO(Long id, @NotEmpty @Size(max = 50) String nom, @NotNull String codiDir3, LocalDate dataCreacio,
			@NotNull Estat actiu) {
		super();
		this.id = id;
		this.nom = nom;
		this.codiDir3 = codiDir3;
		this.dataCreacio = dataCreacio;
		this.actiu = actiu;
	}

	public EntitatDTO(Long id, @NotEmpty @Size(max = 50) String nom, @NotNull String codiDir3, @NotNull Estat actiu) {
		super();
		this.id = id;
		this.nom = nom;
		this.codiDir3 = codiDir3;
		this.actiu = actiu;
	}

}
