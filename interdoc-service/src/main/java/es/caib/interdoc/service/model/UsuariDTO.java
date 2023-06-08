package es.caib.interdoc.service.model;

import es.caib.interdoc.commons.utils.Constants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.*;
import java.util.List;

/**
 * Dades referents a un usuari.
 *
 * @author jagarcia
 */
@Schema(name = "Usuari")
public class UsuariDTO {

    private Long id;

    @NotEmpty
    @Size(max = 100)
    private String nom;

    @NotEmpty
    @Size(max = 100)
    private String llinatge1;

    @NotEmpty
    @Size(max = 100)
    private String llinatge2;

    @NotEmpty
    @Size(max = 50)
    private String document;

    @NotEmpty
    @Size(max = 255)
    private String email;

    @NotEmpty
    @Size(max = 255)
    private String identificador;

    @NotEmpty
    @Size(max = 50)
    private Long tipusUsuario;

    private Boolean itd_admin = false;

    private Boolean itd_usuari = false;

    @NotEmpty
    private Long idioma;

    public UsuariDTO() {
    }

    public UsuariDTO(String identificador) {
        this.identificador = identificador;
    }

    public UsuariDTO(Long id, String nom, String llinatge1, String llinatge2, String document, String email, String identificador, Long tipusUsuario, Boolean itd_admin, Boolean itd_usuari, Long idioma) {
        this.id = id;
        this.nom = nom;
        this.llinatge1 = llinatge1;
        this.llinatge2 = llinatge2;
        this.document = document;
        this.email = email;
        this.identificador = identificador;
        this.tipusUsuario = tipusUsuario;
        this.itd_admin = itd_admin;
        this.itd_usuari = itd_usuari;
        this.idioma = idioma;
    }


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

    public String getLlinatge1() {
        return llinatge1;
    }

    public void setLlinatge1(String llinatge1) {
        this.llinatge1 = llinatge1;
    }

    public String getLlinatge2() {
        return llinatge2;
    }

    public void setLlinatge2(String llinatge2) {
        this.llinatge2 = llinatge2;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Long getTipusUsuario() {
        return tipusUsuario;
    }

    public void setTipusUsuario(Long tipusUsuario) {
        this.tipusUsuario = tipusUsuario;
    }

    public Boolean getItd_admin() {
        return itd_admin;
    }

    public void setItd_admin(Boolean itd_admin) {
        this.itd_admin = itd_admin;
    }

    public Boolean getItd_usuari() {
        return itd_usuari;
    }

    public void setItd_usuari(Boolean itd_usuari) {
        this.itd_usuari = itd_usuari;
    }

    public Long getIdioma() {
        return idioma;
    }

    public void setIdioma(Long idioma) {
        this.idioma = idioma;
    }

    public String getNomComplet() {

        String nomComplet = getNom();
        if (getLlinatge1() != null) {
            nomComplet = nomComplet + " " + getLlinatge1();
        }
        if (getLlinatge2() != null) {
            nomComplet = nomComplet + " " + getLlinatge2();
        }

        return nomComplet;
    }

    public String getNomIdentificador() {

        return getNomComplet() + " (" + getIdentificador() + ")";
    }

    public void setRoles(List<RolDTO> roles) {
        if(roles != null){
            setItd_admin(roles.contains(new RolDTO(Constants.ITD_ADMIN)));
            setItd_usuari(roles.contains(new RolDTO(Constants.ITD_USER)));
        }else{
            setItd_admin(false);
            setItd_usuari(false);
        }
    }

    @Override
    public String toString() {
        return "UsuariDTO{" +
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


}
