package es.caib.interdoc.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import javax.validation.constraints.Size;

/**
 * Dades referents a una aplicació.
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
    private String arxiuExpedientID;
    
    @Size(max = 255)
    private String arxiuDocumentID;
    
    @Size(max = 255)
    private String printableUrl;
    
    @Size(max = 255)
    private String eniFileUrl;
    
    @Size(max = 255)
    private String validationFileUrl;

    
	public InfoArxiuDTO() {
		super();
	}


	public InfoArxiuDTO(Long id, @Size(max = 255) String originalFileUrl, @Size(max = 255) String csv,
			@Size(max = 255) String csvGenerationDefinition, @Size(max = 255) String csvValidationWeb,
			@Size(max = 255) String arxiuExpedientID, @Size(max = 255) String arxiuDocumentID,
			@Size(max = 255) String printableUrl, @Size(max = 255) String eniFileUrl,
			@Size(max = 255) String validationFileUrl) {
		super();
		this.id = id;
		this.originalFileUrl = originalFileUrl;
		this.csv = csv;
		this.csvGenerationDefinition = csvGenerationDefinition;
		this.csvValidationWeb = csvValidationWeb;
		this.arxiuExpedientID = arxiuExpedientID;
		this.arxiuDocumentID = arxiuDocumentID;
		this.printableUrl = printableUrl;
		this.eniFileUrl = eniFileUrl;
		this.validationFileUrl = validationFileUrl;
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

	public String getArxiuExpedientID() {
		return arxiuExpedientID;
	}

	public void setArxiuExpedientID(String arxiuExpedientID) {
		this.arxiuExpedientID = arxiuExpedientID;
	}

	public String getArxiuDocumentID() {
		return arxiuDocumentID;
	}

	public void setArxiuDocumentID(String arxiuDocumentID) {
		this.arxiuDocumentID = arxiuDocumentID;
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

	@Override
	public String toString() {
		return "InfoArxiuDTO [id=" + id + ", originalFileUrl=" + originalFileUrl + ", csv=" + csv
				+ ", csvGenerationDefinition=" + csvGenerationDefinition + ", csvValidationWeb=" + csvValidationWeb
				+ ", arxiuExpedientID=" + arxiuExpedientID + ", arxiuDocumentID=" + arxiuDocumentID + ", printableUrl="
				+ printableUrl + ", eniFileUrl=" + eniFileUrl + ", validationFileUrl=" + validationFileUrl + "]";
	}
   
}
