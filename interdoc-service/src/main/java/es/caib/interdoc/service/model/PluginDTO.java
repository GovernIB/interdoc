package es.caib.interdoc.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Dades referents a un plugin
 *
 * @author jagarcia
 */
@Schema(name = "Plugin")
public class PluginDTO {

    private Long id;

    @NotEmpty
    @Size(max = 50)
    private String nom;

    @NotEmpty
    @Size(max = 255)
    private String classe;

    @NotEmpty
    private String propietats;

    @NotNull
    @PastOrPresent
    private LocalDate dataCreacio;
    
    @NotNull
    private Long entitatId;

    @NotNull
    private Estat actiu;

    public PluginDTO() {
    }
        
	public PluginDTO(Long id, @NotEmpty @Size(max = 50) String nom, @NotEmpty @Size(max = 255) String classe,
			@NotEmpty String propietats, @NotNull @PastOrPresent LocalDate dataCreacio, @NotNull Long entitatId, 
			@NotNull Estat actiu) {
		super();
		this.id = id;
		this.nom = nom;
		this.classe = classe;
		this.propietats = propietats;
		this.dataCreacio = dataCreacio;
		this.entitatId = entitatId;
		this.actiu = actiu;
	}
    
    public PluginDTO(@NotEmpty @Size(max = 50) String nom, @NotEmpty @Size(max = 255) String classe,
			@NotEmpty String propietats, @NotNull @PastOrPresent LocalDate dataCreacio, @NotNull Long entitatId, 
			@NotNull Estat actiu) {
		super();
		this.nom = nom;
		this.classe = classe;
		this.propietats = propietats;
		this.dataCreacio = dataCreacio;
		this.entitatId = entitatId;
		this.actiu = actiu;
	}
    
    public PluginDTO(@NotEmpty @Size(max = 50) String nom, @NotEmpty @Size(max = 255) String classe,
			@NotEmpty String propietats, @NotNull @PastOrPresent LocalDate dataCreacio, @NotNull Estat actiu) {
		super();
		this.nom = nom;
		this.classe = classe;
		this.propietats = propietats;
		this.dataCreacio = dataCreacio;
		this.actiu = actiu;
	}

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

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getPropietats() {
		return propietats;
	}

	public void setPropietats(String propietats) {
		this.propietats = propietats;
	}

	public LocalDate getDataCreacio() {
		return dataCreacio;
	}

	public void setDataCreacio(LocalDate dataCreacio) {
		this.dataCreacio = dataCreacio;
	}
	
	public Long getEntitatId() {
		return entitatId;
	}
	
	public void setEntitatId(Long entitatId) {
		this.entitatId = entitatId;
	}

	public Estat getActiu() {
		return actiu;
	}

	public void setActiu(Estat actiu) {
		this.actiu = actiu;
	}

	@Override
	public String toString() {
		return "PluginDTO [id=" + id + ", nom=" + nom + ", classe=" + classe + ", propietats=" + propietats
				+ ", dataCreacio=" + dataCreacio + ", actiu=" + actiu + "]";
	}
  
}
