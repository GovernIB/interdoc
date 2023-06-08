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
@SequenceGenerator(name = "aplicacio-sequence", sequenceName = "ITD_APLICACIO_SEQ", allocationSize = 1)
@Table(name = "ITD_APLICACIO",
        uniqueConstraints = {
                @UniqueConstraint(name = "ITD_APLICACIO_USUARI_UK", columnNames = "USUARI"),
                @UniqueConstraint(name = "ITD_UNITAT_CODIDIR3_UK", columnNames = "CODIDIR3")
        },
        indexes = {
                @Index(name = "ITD_APLICACIO_PK_I", columnList = "APLICACIOID"),
                @Index(name = "ITD_APLICACIO_USUARI_UK_I", columnList = "USUARI"),
                @Index(name = "ITD_UNITAT_CODIDIR3_UK_I", columnList = "CODIDIR3")
        }
)
@NamedQueries({
        @NamedQuery(name = Aplicacio.FIND_BY_CODIDIR3,
                query = "select a from Aplicacio a where a.codiDir3 = :codiDir3"),
        @NamedQuery(name = Aplicacio.GET_ALL, query = "select a from Aplicacio a")
})
public class Aplicacio extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_CODIDIR3 = "Aplicacio.FIND_BY_CODIDIR3";
    public static final String GET_ALL = "Aplicacio.GET_ALL";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aplicacio-sequence")
    @Column(name = "APLICACIOID", nullable = false, length = 19)
    private Long id;

    /**
     * Codi DIR3 que identifica la aplicació. És únic, i per tant una clau natural.
     * Una vegada creat, no s'actualitza.
     * Ha de seguir el patró d'una lletra (A, E, I, J, L, U) seguida de 8 dígits.
     */
    @Column(name = "CODIDIR3", nullable = false, updatable = false, length = 9)
    @NotNull
    @Pattern(regexp = "[AEIJLU][0-9]{8}", message = "{codidir3.Pattern.message}")
    private String codiDir3;

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

    @Column(name = "USUARI", nullable = false, length = 50)
    @NotNull
    private String usuari;

    /**
     * Clau de l'usuari per a la autenticació
     */

    @Column(name = "CLAU", nullable = false, length = 100)
    private String clau;

    /**
     * Dia de creació. Ha de ser el dia d'avui o un dia passat (no pot ser futur).
     * En la serialitzacio/deserialització JSON s'empra el format dd-mm-aaaa.
     */
    @Column(name = "DATACREACIO", nullable = false)
    @NotNull
    @PastOrPresent
    private LocalDate dataCreacio;

    @Enumerated(ORDINAL)
    @Column(name = "ESTAT", nullable = false)
    @NotNull
    private EstatPublicacio estat;

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

    public String getUsuari() {
        return usuari;
    }

    public void setUsuari(String usuari) {
        this.usuari = usuari;
    }

    public String getClau() {
        return clau;
    }

    public void setClau(String clau) {
        this.clau = clau;
    }

    public LocalDate getDataCreacio() {
        return dataCreacio;
    }

    public void setDataCreacio(LocalDate dataCreacio) {
        this.dataCreacio = dataCreacio;
    }

    public EstatPublicacio getEstat() {
        return estat;
    }

    public void setEstat(EstatPublicacio estat) {
        this.estat = estat;
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
        if (!(o instanceof Aplicacio)) return false;
        Aplicacio that = (Aplicacio) o;
        return Objects.equals(codiDir3, that.codiDir3) && Objects.equals(usuari, that.usuari);
    }

    @Override
    public int hashCode() {
        // TODO
        return Objects.hash(codiDir3);
    }

    @Override
    public String toString() {
        return "Aplicacio{" +
                "id=" + id +
                ", codiDir3='" + codiDir3 + '\'' +
                ", nom='" + nom + '\'' +
                ", usuari='" + usuari + '\'' +
                ", clau='" + clau + '\'' +
                ", dataCreacio=" + dataCreacio +
                ", estat=" + estat +
                '}';
    }
}
