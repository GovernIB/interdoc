package es.caib.interdoc.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Dades referents a un usuari.
 *
 * @author fbosch
 */
@Schema(name = "Usuari")
public class UsuariDTO {

    @NotNull
    private Long usuariId;

    @NotEmpty
    @Size(max = 255)
    private String username;

    @NotEmpty
    @Size(max = 5)
    private String idiomaId;

    private Long darreraEntitat;
    
    @Size(max = 255)
    private String darreraEntitatNom;

    @NotEmpty
    @Size(max = 255)
    private String nom;

    @NotEmpty
    @Size(max = 255)
    private String llinatge1;

    @Size(max = 255)
    private String llinatge2;

    @NotEmpty
    @Size(max = 255)
    private String email;

    @NotEmpty
    @Size(max = 255)
    private String nif;

    public Long getUsuariId() {
        return usuariId;
    }

    public String getUsername() {
        return username;
    }

    public String getIdiomaId() {
        return idiomaId;
    }

    public Long getDarreraEntitat() {
        return darreraEntitat;
    }
    
    public String getDarreraEntitatNom() {
        return darreraEntitatNom;
    }

    public String getNom() {
        return nom;
    }

    public String getLlinatge1() {
        return llinatge1;
    }

    public String getLlinatge2() {
        return llinatge2;
    }

    public String getEmail() {
        return email;
    }

    public String getNif() {
        return nif;
    }

    public void setUsuariId(Long usuariId) {
        this.usuariId = usuariId;
    }

    public void setUsername(@NotEmpty @Size(max = 255) String username) {
        this.username = username;
    }

    public void setIdiomaId(@NotEmpty @Size(max = 5) String idiomaId) {
        this.idiomaId = idiomaId;
    }

    public void setDarreraEntitat(Long darreraEntitat) {
        this.darreraEntitat = darreraEntitat;
    }
    
    public void setDarreraEntitatNom(String darreraEntitatNom) {
        this.darreraEntitatNom = darreraEntitatNom;
    }

    public void setNom(@NotEmpty @Size(max = 255) String nom) {
        this.nom = nom;
    }

    public void setLlinatge1(@NotEmpty @Size(max = 255) String llinatge1) {
        this.llinatge1 = llinatge1;
    }

    public void setLlinatge2(@NotEmpty @Size(max = 255) String llinatge2) {
        this.llinatge2 = llinatge2;
    }

    public void setEmail(@NotEmpty @Size(max = 255) String email) {
        this.email = email;
    }

    public void setNif(@NotEmpty @Size(max = 255) String nif) {
        this.nif = nif;
    }

    public UsuariDTO() {
    }

    public UsuariDTO(Long usuariId,
            String username,
            String nom,
            String llinatge1,
            String llinatge2,
            String email,
            String nif) {
        super();
        this.usuariId = usuariId;
        this.username = username;
        this.nom = nom;
        this.llinatge1 = llinatge1;
        this.llinatge2 = llinatge2;
        this.email = email;
        this.nif = nif;
    }

    public UsuariDTO(@NotNull Long usuariId, @NotEmpty @Size(max = 255) String username,
            Long darreraEntitat,
            @NotEmpty @Size(max = 5) String idiomaId,
            @NotEmpty @Size(max = 255) String nom,
            @NotEmpty @Size(max = 255) String llinatge1,
            String llinatge2,
            @Size(max = 255) String email,
            @NotEmpty @Size(max = 255) String nif) {
        super();
        this.usuariId = usuariId;
        this.username = username;
        this.darreraEntitat = darreraEntitat;
        this.idiomaId = idiomaId;
        this.nom = nom;
        this.llinatge1 = llinatge1;
        this.llinatge2 = llinatge2;
        this.email = email;
        this.nif = nif;
    }

    public UsuariDTO(long usuariId,
            long darreraEntitat,
            String username,
            String nom,
            String llinatge1,
            String llinatge2,
            String email,
            String nif) {
        this.usuariId = usuariId;
        this.darreraEntitat = darreraEntitat;
        this.username = username;
        this.nom = nom;
        this.llinatge1 = llinatge1;
        this.llinatge2 = llinatge2;
        this.email = email;
        this.nif = nif;
    }

    /**
     * Constructor requerit per les consultes HQL amb projecció.
     * Ordre dels paràmetres: usuariId, username, darreraEntitat, nom, llinatge1, llinatge2, email, nif
     */
    public UsuariDTO(Long usuariId,
            String username,
            Long darreraEntitat,
            String nom,
            String llinatge1,
            String llinatge2,
            String email,
            String nif) {
        this.usuariId = usuariId;
        this.username = username;
        this.darreraEntitat = darreraEntitat;
        this.nom = nom;
        this.llinatge1 = llinatge1;
        this.llinatge2 = llinatge2;
        this.email = email;
        this.nif = nif;
    }

}
