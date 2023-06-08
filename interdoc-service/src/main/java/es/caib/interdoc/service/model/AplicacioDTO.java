package es.caib.interdoc.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Dades referents a una aplicació.
 *
 * @author jagarcia
 */
@Schema(name = "Aplicacio")
public class AplicacioDTO {

    private Long id;

    @NotNull
    @Pattern(regexp = "[AEIJLU][0-9]{8}", message = "{codidir3.Pattern.message}")
    private String codiDir3;

    @NotEmpty
    @Size(max = 50)
    private String nom;

    @NotEmpty
    @Size(max = 50)
    private String usuari;

    @NotEmpty
    @Size(max = 50)
    private String clau;

    @NotNull
    @PastOrPresent
    private LocalDate dataCreacio;

    @NotNull
    private EstatPublicacio estat;


    public AplicacioDTO() {
    }

    public AplicacioDTO(Long id, String codiDir3, String nom, String usuari, LocalDate dataCreacio, EstatPublicacio estat) {
        super();
        this.id = id;
        this.codiDir3 = codiDir3;
        this.nom = nom;
        this.usuari = usuari;
        this.dataCreacio = dataCreacio;
        this.estat = estat;
    }


    public AplicacioDTO(Long id, String codiDir3, String nom, String usuari, String clau, LocalDate dataCreacio, EstatPublicacio estat) {
        super();
        this.id = id;
        this.codiDir3 = codiDir3;
        this.nom = nom;
        this.usuari = usuari;
        this.clau = clau;
        this.dataCreacio = dataCreacio;
        this.estat = estat;
    }

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

    @Override
    public String toString() {
        return "AplicacioDTO{" +
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
