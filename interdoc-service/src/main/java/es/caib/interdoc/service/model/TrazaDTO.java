package es.caib.interdoc.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Dades referents a una aplicació.
 *
 * @author jagarcia
 */
@Schema(name = "Traza")
public class TrazaDTO {

    private Long id;

    @NotEmpty
    @Size(max = 255)
    private String nom;

    @NotEmpty
    @Size(max = 255)
    private String valor;

    @NotEmpty
    private Long referenciaId;

    @NotNull
    @PastOrPresent
    private LocalDate dataCreacio;

    public TrazaDTO() {
    }

    public TrazaDTO(Long id, String nom, String valor, Long referenciaId, LocalDate dataCreacio) {
        this.id = id;
        this.nom = nom;
        this.valor = valor;
        this.referenciaId = referenciaId;
        this.dataCreacio = dataCreacio;
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
    public String toString() {
        return "TrazaDTO{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", valor='" + valor + '\'' +
                ", referenciaId=" + referenciaId +
                ", dataCreacio=" + dataCreacio +
                '}';
    }
}
