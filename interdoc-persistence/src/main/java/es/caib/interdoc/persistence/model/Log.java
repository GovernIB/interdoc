package es.caib.interdoc.persistence.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

/**
 * Registre de log. A nivell de classe definim la seqüència que emprarem, i les claus úniques.
 *
 * @author jagarcia
 */


@Entity
@SequenceGenerator(name = "log-sequence", sequenceName = "ITD_LOG_SEQ", allocationSize = 1)
@Table(name = "ITD_LOG",
indexes = {
        @Index(name = "ITD_LOG_PK_I", columnList = "LOGID")
}
)
@NamedQueries({
	@NamedQuery(name = Log.GET_ALL, query = "select l from Log l")
})
public class Log extends BaseEntity  {
	
	private static final long serialVersionUID = 1L;
	
	public static final String GET_ALL = "Log.GET_ALL";
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log-sequence")
    @Column(name = "LOGID", nullable = false, length = 19)
    private Long id;
	
	@Column(name = "DESCRIPCIO", nullable = true)
    private String descripcio;
	
	@Column(name = "PETICIO", nullable = true, length = 255)
    @Size(max = 255)
    private String peticio;
	
	@Column(name = "EXCEPCIO", nullable = true)
    private String excepcio;

	/**
     * Dia de creació. Ha de ser el dia d'avui o un dia passat (no pot ser futur).
     * En la serialitzacio/deserialització JSON s'empra el format dd-mm-aaaa.
     */
    @Column(name = "DATACREACIO", nullable = false)
    @NotNull
    @PastOrPresent
    private LocalDate dataCreacio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	public String getPeticio() {
		return peticio;
	}

	public void setPeticio(String peticio) {
		this.peticio = peticio;
	}

	public String getExcepcio() {
		return excepcio;
	}

	public void setExcepcio(String excepcio) {
		this.excepcio = excepcio;
	}

	public LocalDate getDataCreacio() {
		return dataCreacio;
	}

	public void setDataCreacio(LocalDate dataCreacio) {
		this.dataCreacio = dataCreacio;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", descripcio=" + descripcio + ", peticio=" + peticio + ", excepcio=" + excepcio
				+ ", dataCreacio=" + dataCreacio + "]";
	}

	public Log() {
		super();
	}
	
	public Log(String descripcio, @Size(max = 255) String peticio, String excepcio) {
		super();
		this.descripcio = descripcio;
		this.peticio = peticio;
		this.excepcio = excepcio;
	}
    
    
    
}
