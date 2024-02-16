package es.caib.interdoc.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import javax.validation.constraints.Size;

/**
 * Dades referents a les dades d'un document que es puja a l'Arxiu.
 *
 * @author jagarcia
 */

@Schema(name = "InfoArxiu")
public class InfoArxiuDTO {

    private Long id;

    @Size(max = 255)
    private String originalFileUrl;

    @Size(max = 255)
    private String csv;
   
    @Size(max = 255)
    private String csvGenerationDefinition;
    
    @Size(max = 255)
    private String csvValidationWeb;
    
    @Size(max = 255)
    private String arxiuExpedientId;
    
    @Size(max = 255)
    private String arxiuDocumentId;
    
    @Size(max = 255)
    private String printableUrl;
    
    @Size(max = 255)
    private String eniFileUrl;
    
    @Size(max = 255)
    private String validationFileUrl;
    
    @Size(max = 5)
    private String estatExpedient;

    
	public InfoArxiuDTO() {
		super();
	}


	public InfoArxiuDTO(Long id, @Size(max = 255) String originalFileUrl, @Size(max = 255) String csv,
			@Size(max = 255) String csvGenerationDefinition, @Size(max = 255) String csvValidationWeb,
			@Size(max = 255) String arxiuExpedientId, @Size(max = 255) String arxiuDocumentId,
			@Size(max = 255) String printableUrl, @Size(max = 255) String eniFileUrl,
			@Size(max = 255) String validationFileUrl, @Size(max = 5) String estatExpedient) {
		super();
		this.id = id;
		this.originalFileUrl = originalFileUrl;
		this.csv = csv;
		this.csvGenerationDefinition = csvGenerationDefinition;
		this.csvValidationWeb = csvValidationWeb;
		this.arxiuExpedientId = arxiuExpedientId;
		this.arxiuDocumentId = arxiuDocumentId;
		this.printableUrl = printableUrl;
		this.eniFileUrl = eniFileUrl;
		this.validationFileUrl = validationFileUrl;
		this.estatExpedient = estatExpedient;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOriginalFileUrl() {
		return originalFileUrl;
	}

	public void setOriginalFileUrl(String originalFileUrl) {
		this.originalFileUrl = originalFileUrl;
	}

	public String getCsv() {
		return csv;
	}

	public void setCsv(String csv) {
		this.csv = csv;
	}

	public String getCsvGenerationDefinition() {
		return csvGenerationDefinition;
	}

	public void setCsvGenerationDefinition(String csvGenerationDefinition) {
		this.csvGenerationDefinition = csvGenerationDefinition;
	}

	public String getCsvValidationWeb() {
		return csvValidationWeb;
	}

	public void setCsvValidationWeb(String csvValidationWeb) {
		this.csvValidationWeb = csvValidationWeb;
	}

	public String getArxiuExpedientId() {
		return arxiuExpedientId;
	}

	public void setArxiuExpedientId(String arxiuExpedientId) {
		this.arxiuExpedientId = arxiuExpedientId;
	}

	public String getArxiuDocumentId() {
		return arxiuDocumentId;
	}

	public void setArxiuDocumentId(String arxiuDocumentId) {
		this.arxiuDocumentId = arxiuDocumentId;
	}

	public String getPrintableUrl() {
		return printableUrl;
	}

	public void setPrintableUrl(String printableUrl) {
		this.printableUrl = printableUrl;
	}

	public String getEniFileUrl() {
		return eniFileUrl;
	}

	public void setEniFileUrl(String eniFileUrl) {
		this.eniFileUrl = eniFileUrl;
	}

	public String getValidationFileUrl() {
		return validationFileUrl;
	}

	public void setValidationFileUrl(String validationFileUrl) {
		this.validationFileUrl = validationFileUrl;
	}

	public String getEstatExpedient() {
		return estatExpedient;
	}

	public void setEstatExpedient(String estatExpedient) {
		this.estatExpedient = estatExpedient;
	}

	@Override
	public String toString() {
		return "InfoArxiuDTO [id=" + id + ", originalFileUrl=" + originalFileUrl + ", csv=" + csv
				+ ", csvGenerationDefinition=" + csvGenerationDefinition + ", csvValidationWeb=" + csvValidationWeb
				+ ", arxiuExpedientId=" + arxiuExpedientId + ", arxiuDocumentId=" + arxiuDocumentId + ", printableUrl="
				+ printableUrl + ", eniFileUrl=" + eniFileUrl + ", validationFileUrl=" + validationFileUrl
				+ ", estatExpedient=" + estatExpedient + "]";
	}
	
   
}
