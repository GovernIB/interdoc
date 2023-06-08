package es.caib.interdoc.persistence.model;

import es.caib.interdoc.commons.utils.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ITD_USUARI")
@SequenceGenerator(name = "usuari-sequence", sequenceName = "ITD_USU_SEQ", allocationSize = 1)
@NamedQueries({
        @NamedQuery(name = Usuari.GET_ALL, query = "select a from Usuari a")
})
public class Usuari implements Serializable {

    public static final String GET_ALL = "Usuari.GET_ALL";

    private Long id;
    private String nom;
    private String llinatge1;
    private String llinatge2;
    private String document;
    private String email;
    private String identificador;
    private Long tipusUsuario;
    private Boolean itd_admin = false;
    private Boolean itd_usuari = false;
    private Long idioma;

    public Usuari() {
        super();
    }

    public Usuari(Long id) {
        this.id = id;
    }

    public Usuari(Long id, String identificador) {
        this.id = id;
        this.identificador = identificador;
    }

    public Usuari(String nom, String llinatge1, String llinatge2, String document, String email, String identificador, Long tipusUsuario) {
        this.nom = nom;
        this.llinatge1 = llinatge1;
        this.llinatge2 = llinatge2;
        this.document = document;
        this.email = email;
        this.identificador = identificador;
        this.tipusUsuario = tipusUsuario;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuari-sequence")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NOM", nullable = false)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Column(name = "LLINATGE1")
    public String getLlinatge1() {
        return llinatge1;
    }

    public void setLlinatge1(String llinatge1) {
        this.llinatge1 = llinatge1;
    }

    @Column(name = "LLINATGE2")
    public String getLlinatge2() {
        return llinatge2;
    }

    public void setLlinatge2(String llinatge2) {
        this.llinatge2 = llinatge2;
    }

    @Column(name = "DOCUMENT")
    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    @Column(name = "EMAIL", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "IDENTIFICADOR", nullable = false, unique = true)
    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    @Column(name = "TIPUS_USUARI")
    public Long getTipusUsuario() {
        return tipusUsuario;
    }

    public void setTipusUsuario(Long tipusUsuario) {
        this.tipusUsuario = tipusUsuario;
    }

    @Column(name = "ITD_ADMIN", nullable = false)
    public Boolean getItd_admin() {
        return itd_admin;
    }

    public void setItd_admin(Boolean itd_admin) {
        this.itd_admin = itd_admin;
    }

    @Column(name = "ITD_USER", nullable = false)
    public Boolean getItd_usuari() {
        return itd_usuari;
    }

    public void setItd_usuari(Boolean itd_usuari) {
        this.itd_usuari = itd_usuari;
    }
    @Column(name = "IDIOMA")
    public Long getIdioma() {
        return idioma;
    }

    public void setIdioma(Long idioma) {
        this.idioma = idioma;
    }

    @Override
    public String toString() {
        return "Usuari{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", llinatge1='" + llinatge1 + '\'' +
                ", llinatge2='" + llinatge2 + '\'' +
                ", document='" + document + '\'' +
                ", email='" + email + '\'' +
                ", identificador='" + identificador + '\'' +
                ", tipusUsuario=" + tipusUsuario +
                ", itd_admin=" + itd_admin +
                ", itd_usuari=" + itd_usuari +
                ", idioma=" + idioma +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuari usuari = (Usuari) o;
        return Objects.equals(id, usuari.id) ;
    }

}
