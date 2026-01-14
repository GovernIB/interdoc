package es.caib.interdoc.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Dades referents a un resultat obtingut despres de cridar al servei de ObtenerReferencia. 
 * Es guarda un string amb el XML resultant
 *
 * @author jagarcia
 */
@Schema(name = "ReferenciaXML")
public class ReferenciaXMLDTO {

    private Long id;

    @NotEmpty
    private String resultat;

    @NotNull
    private Long referenciaId;

    @NotNull
    @PastOrPresent
    private LocalDate dataCreacio;


    public ReferenciaXMLDTO() {
    }

    public ReferenciaXMLDTO(Long id, String resultat, Long referenciaId, LocalDate dataCreacio) {
        this.id = id;
        this.resultat = resultat;
        this.referenciaId = referenciaId;
        this.dataCreacio = dataCreacio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
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
        return "ReferenciaXMLDTO{" +
                "id=" + id +
                ", resultat='" + resultat + '\'' +
                ", referenciaId=" + referenciaId +
                ", dataCreacio=" + dataCreacio +
                '}';
    }
}
