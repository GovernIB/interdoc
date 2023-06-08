package es.caib.interdoc.persistence.model;

import es.caib.interdoc.service.model.Estat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Representació d'una entitat. A nivell de classe definim la seqüència que emprarem, i les claus úniques.
 *
 * @author areus
 */
@Entity
@SequenceGenerator(name = "entitat-sequence", sequenceName = "ITD_ENTITAT_SEQ", allocationSize = 1)
@Table(name = "ITD_ENTITAT",
        indexes = {
                @Index(name = "ITD_ENTITAT_PK_I", columnList = "ENTITATID")
        }
)
@NamedQueries({
        @NamedQuery(name = Entitat.GET_ALL, query = "select a from Entitat a")
})
public class Entitat extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String GET_ALL = "Entitat.GET_ALL";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entitat-sequence")
    @Column(name = "ENTITATID", nullable = false, length = 19)
    private Long id;

    
    @Column(name = "CODIDIR3", nullable = true, length = 9)
    private String codiDir3;

    @Column(name = "NOM", nullable = false, length = 255)
    @NotEmpty
    @Size(max = 255)
    private String nom;

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

    public String getCodiDir3() {
        return codiDir3;
    }

    public void setCodiDir3(String codiDir3) {
        this.codiDir3 = codiDir3;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entitat)) return false;
        Entitat that = (Entitat) o;
        return Objects.equals(codiDir3, that.codiDir3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiDir3);
    }

	@Override
	public String toString() {
		return "Entitat [id=" + id + ", codiDir3=" + codiDir3 + ", nom=" + nom + ", dataCreacio=" + dataCreacio
				+ ", actiu=" + actiu + "]";
	}

}
