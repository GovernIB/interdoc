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
@SequenceGenerator(name = "referenciaxml-sequence", sequenceName = "ITD_REFXML_SEQ", allocationSize = 1)
@Table(name = "ITD_REFERENCIAXML",
        indexes = {
                @Index(name = "ITD_REFERENCIAXML_PK_I", columnList = "REFERENCIAXMLID")
        }
)
@NamedQueries({
        @NamedQuery(name = ReferenciaXML.GET_ALL, query = "select x from ReferenciaXML x")
})
public class ReferenciaXML extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String GET_ALL = "ReferenciaXML.GET_ALL";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "referenciaxml-sequence")
    @Column(name = "REFERENCIAXMLID", nullable = false, length = 19)
    private Long id;


    @Column(name = "RESULTAT")
    private String resultat;

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

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
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
        ReferenciaXML that = (ReferenciaXML) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ReferenciaXML{" +
                "id=" + id +
                ", resultat='" + resultat + '\'' +
                ", dataCreacio=" + dataCreacio +
                ", referenciaId=" + referenciaId +
                '}';
    }
}
