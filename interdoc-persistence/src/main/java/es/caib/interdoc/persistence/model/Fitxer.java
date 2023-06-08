package es.caib.interdoc.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Representació d'un fitxer. A nivell de classe definim la seqüència que
 * emprarem, i les claus úniques.
 *
 * @author areus
 */
@Entity
@SequenceGenerator(name = "fitxer-sequence", sequenceName = "ITD_FITXER_SEQ", allocationSize = 1)
@Table(name = "ITD_FITXER", indexes = { @Index(name = "ITD_FITXER_PK_I", columnList = "FITXERID") })
@NamedQueries({ @NamedQuery(name = Fitxer.GET_ALL, query = "select a from Fitxer a") })
public class Fitxer extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final String GET_ALL = "Fitxer.GET_ALL";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fitxer-sequence")
	@Column(name = "FITXERID", nullable = false, length = 19)
	private Long id;

	@Column(name = "NOM", nullable = false, length = 255)
	@NotEmpty
	@Size(max = 255)
	private String nom;

	@Column(name = "DESCRIPCIO", nullable = false, length = 1000)
	@NotEmpty
	@Size(max = 1000)
	private String descripcio;

	@Column(name = "MIME", nullable = false, length = 255)
	@NotEmpty
	@Size(max = 255)
	private String mime;

	@Column(name = "TAMANY", nullable = false)
	@NotNull
	private Long tamany;

	@Column(name = "DATACREACIO", nullable = false)
	@PastOrPresent
	private LocalDate dataCreacio;

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

	public LocalDate getDataCreacio() {
		return dataCreacio;
	}

	public void setDataCreacio(LocalDate dataCreacio) {
		this.dataCreacio = dataCreacio;
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

	public Long getTamany() {
		return tamany;
	}

	public void setTamany(Long tamany) {
		this.tamany = tamany;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fitxer other = (Fitxer) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Fitxer [id=" + id + ", nom=" + nom + ", descripcio=" + descripcio + ", mime=" + mime + ", tamany="
				+ tamany + ", dataCreacio=" + dataCreacio + "]";
	}

}
