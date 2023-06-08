package es.caib.interdoc.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import es.caib.interdoc.service.model.Estat;

import java.time.LocalDate;
import java.util.Objects;



/**
 * Representació d'un plugin. A nivell de classe definim la seqüència que emprarem, i les claus úniques.
 *
 * @author jagarcia
 */
@Entity
@SequenceGenerator(name = "plugin-sequence", sequenceName = "ITD_PLUGIN_SEQ", allocationSize = 1)
@Table(name = "ITD_PLUGIN",
        indexes = {
                @Index(name = "ITD_PLUGIN_PK_I", columnList = "PLUGINID")
        }
)
@NamedQueries({
        @NamedQuery(name = Plugin.GET_ALL, query = "select a from Plugin a")
})
public class Plugin extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String GET_ALL = "Plugin.GET_ALL";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plugin-sequence")
    @Column(name = "PLUGINID", nullable = false, length = 19)
    private Long id;


    /**
     * Nom del plugin. Ha de ser una cadena no buida, de màxim 100 caràcters.
     */
    @Column(name = "NOM", nullable = false, length = 50)
    @NotEmpty
    @Size(max = 100)
    private String nom;

    /**
     * Classe del plugin. Ha de ser una cadena no buida, de màxim 255 caràcters.
     */
    @Column(name = "CLASSE", nullable = false, length = 255)
    @NotEmpty
    @Size(max = 255)
    private String classe;

    /**
     * Camp de propietats
     */

    @Column(name = "PROPIETATS", nullable = false)
    private String propietats;

    /**
     * Dia de creació. Ha de ser el dia d'avui o un dia passat (no pot ser futur).
     * En la serialitzacio/deserialització JSON s'empra el format dd-mm-aaaa.
     */
    @Column(name = "DATACREACIO", nullable = false)
    @NotNull
    @PastOrPresent
    private LocalDate dataCreacio;

  
    @Column(name = "ACTIU", nullable = false)
    @NotNull
    private Estat actiu;

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

	public Estat getActiu() {
		return actiu;
	}

	public void setActiu(Estat actiu) {
		this.actiu = actiu;
	}

	/*
   La implementació de equals i hashCode s'hauria de fer sempre que es pugui amb una clau natural, o en cas que
   no n'hi hagi amb l'id, però comparant-ho només si no és null, i retornant un valor fix al hashCode per evitar
   que canvii després de cridar persist.
   Veure: https://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html
   Apartat: 2.5.7. Implementing equals() and hashCode()
   */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plugin)) return false;
        Plugin that = (Plugin) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        // TODO
        return Objects.hash(id);
    }

	@Override
	public String toString() {
		return "Plugin [id=" + id + ", nom=" + nom + ", classe=" + classe + ", propietats=" + propietats
				+ ", dataCreacio=" + dataCreacio + ", actiu=" + actiu + "]";
	}

    
}
