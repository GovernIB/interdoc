package es.caib.interdoc.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Dades que es guarden quan es realitza a un accés a una referencia.
 * 
 * @author jagarcia
 */

@Schema(name = "Acces")
public class AccesDTO {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String identificacio;

    @NotEmpty
    @Size(max = 50)
    private String tipusIdentificacio;

    @NotEmpty
    @Size(max = 20)
    private String ip;

    private long referenciaId;

    @NotNull
    @PastOrPresent
    private LocalDate dataCreacio;

    public AccesDTO() {
    }

    public AccesDTO(Long id, String identificacio, String tipusIdentificacio, String ip, long referenciaId, LocalDate dataCreacio) {
        this.id = id;
        this.identificacio = identificacio;
        this.tipusIdentificacio = tipusIdentificacio;
        this.ip = ip;
        this.referenciaId = referenciaId;
        this.dataCreacio = dataCreacio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificacio() {
        return identificacio;
    }

    public void setIdentificacio(String identificacio) {
        this.identificacio = identificacio;
    }

    public String getTipusIdentificacio() {
        return tipusIdentificacio;
    }

    public void setTipusIdentificacio(String tipusIdentificacio) {
        this.tipusIdentificacio = tipusIdentificacio;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(long referenciaId) {
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
        return "AccesDTO{" +
                "id=" + id +
                ", identificacio='" + identificacio + '\'' +
                ", tipusIdentificacio='" + tipusIdentificacio + '\'' +
                ", ip='" + ip + '\'' +
                ", referenciaId=" + referenciaId +
                ", dataCreacio=" + dataCreacio +
                '}';
    }
}
