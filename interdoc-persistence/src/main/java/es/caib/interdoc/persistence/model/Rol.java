package es.caib.interdoc.persistence.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ITD_ROL")
@SequenceGenerator(name = "rol-sequence", sequenceName = "ITD_ROL_SEQ", allocationSize = 1)
public class Rol implements Serializable {

    private Long id;
    private String nom;
    private String descripcio;

    public Rol() {
    }

    public Rol(Long id) {
        this.id = id;
    }

    public Rol(String nom) {
        this.nom = nom;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rol-sequence")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NOM", nullable = false, unique = true)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Column(name = "DESCRIPCIO", nullable = false)
    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", descripcio='" + descripcio + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rol rol = (Rol) o;
        return Objects.equals(nom, rol.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, descripcio);
    }
}
