package es.caib.interdoc.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Dades referents a un usuari.
 *
 * @author jagarcia
 */

public class RolDTO {

    private Long id;

    @NotEmpty
    @Size(max = 100)
    private String nom;

    @NotEmpty
    @Size(max = 255)
    private String descripcio;

    public RolDTO() {
    }

    public RolDTO(String nom) {
        this.nom = nom;
    }

    public RolDTO(String nom, String descripcio) {
        this.nom = nom;
        this.descripcio = descripcio;
    }

    public RolDTO(Long id, String nom, String descripcio) {
        this.id = id;
        this.nom = nom;
        this.descripcio = descripcio;
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

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    @Override
    public String toString() {
        return "RolDTO{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", descripcio='" + descripcio + '\'' +
                '}';
    }
}
