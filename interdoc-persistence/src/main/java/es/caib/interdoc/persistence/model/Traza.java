package es.caib.interdoc.persistence.model;

import es.caib.interdoc.service.model.EstatPublicacio;
import jdk.jfr.Name;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

import static javax.persistence.EnumType.ORDINAL;

/**
 * Representació d'una aplicació. A nivell de classe definim la seqüència que emprarem, i les claus úniques.
 *
 * @author areus
 */
@Entity
@SequenceGenerator(name = "traza-sequence", sequenceName = "ITD_TRAZA_SEQ", allocationSize = 1)
@Table(name = "ITD_TRAZA",
        uniqueConstraints = {
                @UniqueConstraint(name = "ITD_TRAZA_NOM_UK", columnNames = "NOM")
        },
        indexes = {
                @Index(name = "ITD_TRAZA_PK_I", columnList = "TRAZAID"),
                @Index(name = "ITD_TRAZA_NOM_UK_I", columnList = "NOM")
        }
)
@NamedQueries({
        @NamedQuery(name = Traza.FIND_BY_NOM,
                query = "select a from Traza a where a.nom = :nom")
})
public class Traza extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_NOM = "Traza.FIND_BY_NOM";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "traza-sequence")
    @Column(name = "TRAZAID", nullable = false, length = 19)
    private Long id;


    /**
     * Nom de la únitat orgànica. Ha de ser una cadena no buida, de màxim 50 caràcters.
     */
    @Column(name = "NOM", nullable = false, length = 50)
    @NotEmpty
    @Size(max = 50)
    private String nom;

    /**
     * Codi de usuari de l'aplicació que vol autenticar-se al sistema per consultar referencies.
     */

    @Column(name = "VALOR", length = 50)
    private String valor;

    /**
     * Clau de l'usuari per a la autenticació
     */

    @Column(name = "REFERENCIAID", nullable = false)
    private Long referenciaId;

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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Long getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(Long referenciaId) {
        this.referenciaId = referenciaId;
    }

    public LocalDate getDataCreacio() {
        return dataCreacio;
    }

    public void setDataCreacio(LocalDate dataCreacio) {
        this.dataCreacio = dataCreacio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Traza traza = (Traza) o;
        return Objects.equals(nom, traza.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }

    @Override
    public String toString() {
        return "Traza{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", valor='" + valor + '\'' +
                ", referenciaId=" + referenciaId +
                ", dataCreacio=" + dataCreacio +
                '}';
    }
}
