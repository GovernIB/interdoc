package es.caib.interdoc.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Dades referents a una referencia.
 *
 * @author jagarcia
 */
@Schema(name = "Referencia")
public class ReferenciaDTO {

	private Long id;

	private String csvId;

	private String uuId;

	@NotNull
	@Size(max = 255)
	private String referencia;

	@NotNull
	@Size(max = 255)
	private String direccio;

	@Size(max = 255)
	private String hash;

	@NotEmpty
	@Size(max = 50)
	private String emisor;

	@NotEmpty
	@Size(max = 50)
	private String receptor;

	@NotEmpty
	@Size(max = 255)
	private String urlVisible;

	private String formatFirma;

	@NotNull
	@PastOrPresent
	private LocalDate dataCreacio;

	private Long infoSignaturaId;

	private Long infoArxiuId;

	private Long entitatId;

	private Long fitxerId;

	public ReferenciaDTO() {
	}

	public ReferenciaDTO(Long id, String csvId, String uuId, String direccio, String hash, String emisor,
			String receptor, String urlVisible, String formatFirma, LocalDate dataCreacio) {
		this.id = id;
		this.csvId = csvId;
		this.uuId = uuId;
		this.direccio = direccio;
		this.hash = hash;
		this.emisor = emisor;
		this.receptor = receptor;
		this.urlVisible = urlVisible;
		this.formatFirma = formatFirma;
		this.dataCreacio = dataCreacio;
	}

	public ReferenciaDTO(Long id, String csvId, String uuId, String direccio, String emisor, String receptor,
			String formatFirma, LocalDate dataCreacio) {
		this.id = id;
		this.csvId = csvId;
		this.uuId = uuId;
		this.direccio = direccio;
		this.emisor = emisor;
		this.receptor = receptor;
		this.formatFirma = formatFirma;
		this.dataCreacio = dataCreacio;
	}

	// long, java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	// java.lang.String, java.langString, java.time.LocalDate, long, long, long,
	// java.lang.String, long

	public ReferenciaDTO(Long id, String csvId, String uuId, String direccio, String emisor, String receptor,
			String formatFirma, LocalDate dataCreacio, Long infoSignaturaId, Long infoArxiuId, Long fitxerId,
			String referencia, Long entitatId) {
		this.id = id;
		this.csvId = csvId;
		this.uuId = uuId;
		this.referencia = referencia;
		this.direccio = direccio;
		this.emisor = emisor;
		this.receptor = receptor;
		this.formatFirma = formatFirma;
		this.dataCreacio = dataCreacio;
		this.infoSignaturaId = infoSignaturaId;
		this.infoArxiuId = infoArxiuId;
		this.entitatId = entitatId;
		this.fitxerId = fitxerId;
	}

	public ReferenciaDTO(long id, String csvId, String uuId, String referencia, String direccio, String hash,
			String emisor, String receptor, String urlVisible, LocalDate dataCreacio, long infoSignaturaId,
			long infoArxiuId, long entitatId, String formatFirma, long fitxerId) {
		super();
		this.id = id;
		this.csvId = csvId;
		this.uuId = uuId;
		this.referencia = referencia;
		this.direccio = direccio;
		this.hash = hash;
		this.emisor = emisor;
		this.receptor = receptor;
		this.urlVisible = urlVisible;
		this.formatFirma = formatFirma;
		this.dataCreacio = dataCreacio;
		this.infoSignaturaId = infoSignaturaId;
		this.infoArxiuId = infoArxiuId;
		this.entitatId = entitatId;
		this.fitxerId = fitxerId;
	}

	public ReferenciaDTO(Long id, String csvId, String uuId, @NotNull @Size(max = 255) String referencia,
			@NotNull @Size(max = 255) String direccio, @Size(max = 255) String hash,
			@NotEmpty @Size(max = 50) String emisor, @NotEmpty @Size(max = 50) String receptor,
			@NotEmpty @Size(max = 255) String urlVisible, String formatFirma,
			@NotNull @PastOrPresent LocalDate dataCreacio, Long infoSignaturaId, Long infoArxiuId, Long entitatId,
			Long fitxerId) {
		super();
		this.id = id;
		this.csvId = csvId;
		this.uuId = uuId;
		this.referencia = referencia;
		this.direccio = direccio;
		this.hash = hash;
		this.emisor = emisor;
		this.receptor = receptor;
		this.urlVisible = urlVisible;
		this.formatFirma = formatFirma;
		this.dataCreacio = dataCreacio;
		this.infoSignaturaId = infoSignaturaId;
		this.infoArxiuId = infoArxiuId;
		this.entitatId = entitatId;
		this.fitxerId = fitxerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCsvId() {
		return csvId;
	}

	public void setCsvId(String csvId) {
		this.csvId = csvId;
	}

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

	public String getDireccio() {
		return direccio;
	}

	public void setDireccio(String direccio) {
		this.direccio = direccio;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public String getReceptor() {
		return receptor;
	}

	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}

	public String getUrlVisible() {
		return urlVisible;
	}

	public void setUrlVisible(String urlVisible) {
		this.urlVisible = urlVisible;
	}

	public String getFormatFirma() {
		return formatFirma;
	}

	public void setFormatFirma(String formatFirma) {
		this.formatFirma = formatFirma;
	}

	public LocalDate getDataCreacio() {
		return dataCreacio;
	}

	public void setDataCreacio(LocalDate dataCreacio) {
		this.dataCreacio = dataCreacio;
	}

	public Long getInfoSignaturaId() {
		return infoSignaturaId;
	}

	public void setInfoSignaturaId(Long infoSignaturaId) {
		this.infoSignaturaId = infoSignaturaId;
	}

	public Long getInfoArxiuId() {
		return infoArxiuId;
	}

	public void setInfoArxiuId(Long infoArxiuId) {
		this.infoArxiuId = infoArxiuId;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Long getEntitatId() {
		return entitatId;
	}

	public void setEntitatId(Long entitatId) {
		this.entitatId = entitatId;
	}

	public Long getFitxerId() {
		return fitxerId;
	}

	public void setFitxerId(Long fitxerId) {
		this.fitxerId = fitxerId;
	}

	@Override
	public String toString() {
		return "ReferenciaDTO [id=" + id + ", csvId=" + csvId + ", uuId=" + uuId + ", referencia=" + referencia
				+ ", direccio=" + direccio + ", hash=" + hash + ", emisor=" + emisor + ", receptor=" + receptor
				+ ", urlVisible=" + urlVisible + ", formatFirma=" + formatFirma + ", dataCreacio=" + dataCreacio
				+ ", infoSignaturaId=" + infoSignaturaId + ", infoArxiuId=" + infoArxiuId + ", entitatId=" + entitatId
				+ ", fitxerId=" + fitxerId + "]";
	}

}
