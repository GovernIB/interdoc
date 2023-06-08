package es.caib.interdoc.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Representació d'una aplicació. A nivell de classe definim la seqüència que emprarem, i les claus úniques.
 *
 * @author areus
 */
@Entity
@SequenceGenerator(name = "acces-sequence", sequenceName = "ITD_ACCES_SEQ", allocationSize = 1)
@Table(name = "ITD_ACCES",
        indexes = {
                @Index(name = "ITD_ACCES_PK_I", columnList = "ACCESID")
        }
)
@NamedQueries({
        @NamedQuery(name = Acces.GET_ALL, query = "select a from Acces a")
})
public class Acces extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String GET_ALL = "Acces.GET_ALL";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acces-sequence")
    @Column(name = "ACCESID", nullable = false, length = 19)
    private Long id;


    @Column(name = "IDENTIFICACIO", nullable = false, length = 50)
    @NotEmpty
    @Size(max = 50)
    private String identificacio;


    @Column(name = "TIPUSIDENTIFICACIO", nullable = false, length = 50)
    @NotNull
    private String tipusIdentificacio;


    @Column(name = "IP", nullable = false, length = 20)
    @NotNull
    private String ip;

    /**
     * Dia de creació. Ha de ser el dia d'avui o un dia passat (no pot ser futur).
     * En la serialitzacio/deserialització JSON s'empra el format dd-mm-aaaa.
     */
    @Column(name = "DATACREACIO", nullable = false)
    @NotNull
    @PastOrPresent
    private LocalDate dataCreacio;


    @Column(name="REFERENCIAID", nullable = false)
    @NotNull
    private long referenciaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificacio() {
        return identificacio;
    }

    public void setIdentificacio(String identificacio) {
        this.identificacio = identificacio;
    }

    public String getTipusIdentificacio() {
        return tipusIdentificacio;
    }

    public void setTipusIdentificacio(String tipusIdentificacio) {
        this.tipusIdentificacio = tipusIdentificacio;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDate getDataCreacio() {
        return dataCreacio;
    }

    public void setDataCreacio(LocalDate dataCreacio) {
        this.dataCreacio = dataCreacio;
    }

    public long getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(long referenciaId) {
        this.referenciaId = referenciaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Acces acces = (Acces) o;
        return Objects.equals(id, acces.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Acces{" +
                "id=" + id +
                ", identificacio='" + identificacio + '\'' +
                ", tipusIdentificacio='" + tipusIdentificacio + '\'' +
                ", ip='" + ip + '\'' +
                ", dataCreacio=" + dataCreacio +
                ", referenciaId=" + referenciaId +
                '}';
    }
}
