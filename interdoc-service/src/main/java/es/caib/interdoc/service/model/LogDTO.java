package es.caib.interdoc.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Dades referents a un accés.
 *
 * @author jagarcia
 */
@Schema(name = "Log")
public class LogDTO {

    private Long id;

    @NotNull
    private String descripcio;

    @NotNull
    private String peticio;
    
    @NotNull
    private String excepcio;

    @NotNull
    @PastOrPresent
    private LocalDate dataCreacio;

    public LogDTO() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	public String getPeticio() {
		return peticio;
	}

	public void setPeticio(String peticio) {
		this.peticio = peticio;
	}

	public String getExcepcio() {
		return excepcio;
	}

	public void setExcepcio(String excepcio) {
		this.excepcio = excepcio;
	}

	public LocalDate getDataCreacio() {
		return dataCreacio;
	}

	public void setDataCreacio(LocalDate dataCreacio) {
		this.dataCreacio = dataCreacio;
	}

	@Override
	public String toString() {
		return "LogDTO [id=" + id + ", descripcio=" + descripcio + ", peticio=" + peticio + ", excepcio=" + excepcio
				+ ", dataCreacio=" + dataCreacio + "]";
	}

	public LogDTO(Long id, @NotNull String descripcio, @NotNull String peticio, @NotNull String excepcio,
			@NotNull @PastOrPresent LocalDate dataCreacio) {
		super();
		this.id = id;
		this.descripcio = descripcio;
		this.peticio = peticio;
		this.excepcio = excepcio;
		this.dataCreacio = dataCreacio;
	}

    
    
}
