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

    private Long formatFirma;

    @NotNull
    @PastOrPresent
    private LocalDate dataCreacio;
    
    private String expedientId;
    
    private String estatExpedientId;    

	public ReferenciaDTO() {
    }

	public ReferenciaDTO(Long id, String csvId, String uuId, String direccio, String hash, String emisor, String receptor, String urlVisible, Long formatFirma, LocalDate dataCreacio) {
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

    public ReferenciaDTO(Long id, String csvId, String uuId, String direccio, String emisor, String receptor, Long formatFirma, LocalDate dataCreacio) {
        this.id = id;
        this.csvId = csvId;
        this.uuId = uuId;
        this.direccio = direccio;
        this.emisor = emisor;
        this.receptor = receptor;
        this.formatFirma = formatFirma;
        this.dataCreacio = dataCreacio;
    }

    public ReferenciaDTO(Long id, String csvId, String uuId, String direccio, String hash, String emisor, String receptor, String urlVisible, Long formatFirma,
			LocalDate dataCreacio, String expedientId, String estatExpedientId, String referencia) {
		super();
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
		this.expedientId = expedientId;
		this.estatExpedientId = estatExpedientId;
		this.referencia = referencia;
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

    public Long getFormatFirma() {
        return formatFirma;
    }

    public void setFormatFirma(Long formatFirma) {
        this.formatFirma = formatFirma;
    }

    public LocalDate getDataCreacio() {
        return dataCreacio;
    }

    public void setDataCreacio(LocalDate dataCreacio) {
        this.dataCreacio = dataCreacio;
    }
    
    public String getExpedientId() {
		return expedientId;
	}

	public void setExpedientId(String expedientId) {
		this.expedientId = expedientId;
	}

	public String getEstatExpedientId() {
		return estatExpedientId;
	}

	public void setEstatExpedientId(String estatExpedientId) {
		this.estatExpedientId = estatExpedientId;
	}

    public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	@Override
    public String toString() {
        return "ReferenciaDTO{" +
                "id=" + id +
                ", csvId='" + csvId + '\'' +
                ", uuId='" + uuId + '\'' +
                ", referencia='" + referencia + '\'' +
                ", direccio='" + direccio + '\'' +
                ", hash='" + hash + '\'' +
                ", emisor='" + emisor + '\'' +
                ", receptor='" + receptor + '\'' +
                ", urlVisible='" + urlVisible + '\'' +
                ", formatFirma=" + formatFirma +
                ", dataCreacio=" + dataCreacio +
                ", expedientId=" + expedientId +
                ", estatExpedientId=" + estatExpedientId +
                '}';
    }
}
