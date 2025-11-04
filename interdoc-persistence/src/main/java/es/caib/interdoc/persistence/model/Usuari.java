package es.caib.interdoc.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Representació d'un usuari. A nivell de classe definim la seqüència que emprarem, i les claus úniques.
 *
 * @author areus
 */
@Entity
@SequenceGenerator(name = "usuari-sequence", sequenceName = "ITD_USUARI_SEQ", allocationSize = 1)
@Table(name = "ITD_USUARI", indexes = { @Index(name = "ITD_USUARI_PK_I", columnList = "USUARIID") })
@NamedQueries({ @NamedQuery(name = Usuari.GET_ALL, query = "select a from Usuari a") })
public class Usuari extends BaseEntity {

    private static final long serialVersionUID = 24L;

    public static final String GET_ALL = "Usuari.GET_ALL";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuari-sequence")
    @Column(name = "USUARIID", nullable = false, length = 19)
    private Long usuariId;

    @Column(name = "USERNAME", nullable = false, length = 255)
    @NotNull
    private String username;

    @Column(name = "IDIOMAID", nullable = false, length = 5)
    @NotNull
    @Size(max = 5)
    private String idiomaId;
    
    @Column(name = "DARRERAENTITAT", nullable = true)
    private Long darreraEntitat;

    @Column(name = "NOM", nullable = false)
    @NotNull
    private String nom;

    @Column(name = "LLINATGE1", nullable = false)
    @NotNull
    private String llinatge1;

    @Column(name = "LLINATGE2", nullable = true)
    private String llinatge2;

    @Column(name = "EMAIL", nullable = true)
    private String email;

    @Column(name = "NIF", nullable = true)
    private String nif;

    public Long getUsuariId() {
        return usuariId;
    }

    public void setUsuariId(Long usuariId) {
        this.usuariId = usuariId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdiomaId() {
        return idiomaId;
    }

    public void setIdiomaId(String idiomaId) {
        this.idiomaId = idiomaId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLlinatge1() {
        return llinatge1;
    }

    public void setLlinatge1(String llinatge1) {
        this.llinatge1 = llinatge1;
    }
    
    public String getLlinatge2() {
        return this.llinatge2;
    }
    
    public void setLlinatge2(String llinatge2) {
        this.llinatge2 = llinatge2;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email=email;
    }
    
    public String getNif() {
        return nif;
    }
    
    public void setNif(String nif) {
        this.nif=nif;
    }
    
    public Long getDarreraEntitat() {
        return darreraEntitat;
    }
    
    public void setDarreraEntitat(Long darreraEntitat) {
        this.darreraEntitat = darreraEntitat;
    }
    
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Usuari))
            return false;
        Usuari that = (Usuari) o;
        return Objects.equals(nif, that.nif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nif);
    }

    @Override
    public String toString() {
        return "Usuari [usuariId=" + usuariId + ", idiomaId=" + idiomaId + ", nom=" + nom + ", llinatge1=" + llinatge1 +", llinatge2=" + llinatge2 +", email=" + email
                + ", nif=" + nif + "darreraEntitat ="+darreraEntitat+ "]";
    }

}
