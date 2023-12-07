package es.caib.interdoc.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Representació d'una referencia. A nivell de classe definim la seqüència que emprarem, i les claus úniques.
 *
 * @author areus
 */
@Entity
@SequenceGenerator(name = "referencia-sequence", sequenceName = "ITD_REFERENCIA_SEQ", allocationSize = 1)
@Table(name = "ITD_REFERENCIA",
        indexes = {
                @Index(name = "ITD_REFERENCIA_PK_I", columnList = "REFERENCIAID")
        }
)
@NamedQueries({
        @NamedQuery(name = Referencia.GET_ALL, query = "select r from Referencia r"),
        @NamedQuery(name = Referencia.FILTER_BETWEEN_DATES, query = "select r from Referencia r where r.dataCreacio >= :inici and r.dataCreacio <= :fi")
})
public class Referencia extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String GET_ALL = "Referencia.GET_ALL";
    public static final String FILTER_BETWEEN_DATES = "Referencia.FILTER_BETWEEN_DATES";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "referencia-sequence")
    @Column(name = "REFERENCIAID", nullable = false, length = 19)
    private Long id;

    @Column(name = "CSVIDENTIFICADOR", nullable = true, length = 255)
    @Size(max = 255)
    private String csvId;

    @Column(name = "UUID", nullable = true, length = 255)
    @Size(max = 255)
    private String uuId;
    
    @Column(name = "REFERENCIA", nullable = true, length = 255)
    @Size(max = 255)
    private String referencia;

    @Column(name = "DIRECCIO", nullable = false, length = 255)
    @NotEmpty
    @Size(max = 255)
    private String direccio;

    @Column(name = "HASH", nullable = false, length = 255)
    @Size(max = 255)
    private String hash;

    @Column(name = "EMISOR", nullable = false, length = 50)
    @Size(max = 50)
    private String emisor;

    @Column(name = "RECEPTOR", nullable = false, length = 50)
    @Size(max = 50)
    private String receptor;

    @Column(name = "URLVISIBLE", nullable = false, length = 255)
    @Size(max = 255)
    private String urlVisible;

    @Column(name = "FORMATFIRMA")
    @Size(max = 5)
    private String formatFirma;

    @Column(name = "DATACREACIO", nullable = false)
    @NotNull
    @PastOrPresent
    private LocalDate dataCreacio;
    
    @Column(name = "INFOSIGNATURAID", nullable = true, length = 19)
    private Long infoSignaturaId;
    
    @Column(name = "INFOARXIUID", nullable = true, length = 19)
    private Long infoArxiuId;
    
    @Column(name = "FITXERID", nullable = true, length = 19)
    private Long fitxerId;
    
    @Column(name = "ENTITATID", nullable = false, length = 19)
    private Long entitatId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCsvId() {
        return csvId;
    }

    public void setCsvId(String csvId) {
        this.csvId = csvId;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }
    
    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDireccio() {
        return direccio;
    }

    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getUrlVisible() {
        return urlVisible;
    }

    public void setUrlVisible(String urlVisible) {
        this.urlVisible = urlVisible;
    }

    public String getFormatFirma() {
        return formatFirma;
    }

    public void setFormatFirma(String formatFirma) {
        this.formatFirma = formatFirma;
    }

    public LocalDate getDataCreacio() {
        return dataCreacio;
    }

    public void setDataCreacio(LocalDate dataCreacio) {
        this.dataCreacio = dataCreacio;
    }

	public Long getInfoSignaturaId() {
		return infoSignaturaId;
	}

	public void setInfoSignaturaId(Long infoSignaturaId) {
		this.infoSignaturaId = infoSignaturaId;
	}

	public Long getInfoArxiuId() {
		return infoArxiuId;
	}

	public void setInfoArxiuId(Long infoArxiuId) {
		this.infoArxiuId = infoArxiuId;
	}

	public Long getFitxerId() {
		return fitxerId;
	}

	public void setFitxerId(Long fitxerId) {
		this.fitxerId = fitxerId;
	}

	public Long getEntitatId() {
		return entitatId;
	}

	public void setEntitatId(Long entitatId) {
		this.entitatId = entitatId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Referencia that = (Referencia) o;
        return Objects.equals(hash, that.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash);
    }

	@Override
	public String toString() {
		return "Referencia [id=" + id + ", csvId=" + csvId + ", uuId=" + uuId + ", referencia=" + referencia
				+ ", direccio=" + direccio + ", hash=" + hash + ", emisor=" + emisor + ", receptor=" + receptor
				+ ", urlVisible=" + urlVisible + ", formatFirma=" + formatFirma + ", dataCreacio=" + dataCreacio
				+ ", infoSignaturaId=" + infoSignaturaId + ", infoArxiuId=" + infoArxiuId + ", fitxerId=" + fitxerId
				+ ", entitatId=" + entitatId + "]";
	}

}
