package es.caib.interdoc.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Representació de la informació de la informació que ens retorna arxiu al guardar-hi un document.
 *
 * @author jagarcia
 */
@Entity
@SequenceGenerator(name = "infoarxiu-sequence", sequenceName = "ITD_INFOARXIU_SEQ", allocationSize = 1)
@Table(name = "ITD_INFOARXIU",
        indexes = {
                @Index(name = "ITD_INFOARXIU_PK_I", columnList = "INFOARXIUID")
        }
)
@NamedQueries({
        @NamedQuery(name = InfoArxiu.GET_ALL, query = "select a from InfoArxiu a")
})
public class InfoArxiu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String GET_ALL = "InfoArxiu.GET_ALL";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "infoarxiu-sequence")
    @Column(name = "INFOARXIUID", nullable = false, length = 19)
    private Long id;

    @Column(name = "ORIGINALFILEURL", nullable = true, length = 255)
    @Size(max = 255)
    private String originalFileUrl;
    
    @Column(name = "CSV", nullable = true, length = 255)
    @Size(max = 255)
    private String csv;

    @Column(name = "CSVGENERATIONDEFINITION", nullable = true, length = 255)
    @Size(max = 255)
    private String csvGenerationDefinition; 
    
    @Column(name = "CSVVALIDATIONWEB", nullable = true, length = 255)
    @Size(max = 255)
    private String csvValidationWeb;
    
    @Column(name = "ARXIUEXPEDIENTID", nullable = true, length = 255)
    @Size(max = 255)
    private String arxiuExpedientId;
    
    @Column(name = "ARXIUDOCUMENTID", nullable = true, length = 255)
    @Size(max = 255)
    private String arxiuDocumentId;
    
    @Column(name = "PRINTABLEURL", nullable = true, length = 255)
    @Size(max = 255)
    private String printableUrl;
    
    @Column(name = "ENIFILEURL", nullable = true, length = 255)
    @Size(max = 255)
    private String eniFileUrl;
    
    @Column(name = "VALIDATIONFILEURL", nullable = true, length = 255)
    @Size(max = 255)
    private String validationFileUrl;

    
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

	/*
   La implementació de equals i hashCode s'hauria de fer sempre que es pugui amb una clau natural, o en cas que
   no n'hi hagi amb l'id, però comparant-ho només si no és null, i retornant un valor fix al hashCode per evitar
   que canvii després de cridar persist.
   Veure: https://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html
   Apartat: 2.5.7. Implementing equals() and hashCode()
   */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InfoArxiu)) return false;
        InfoArxiu that = (InfoArxiu) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

	@Override
	public String toString() {
		return "InfoArxiu [id=" + id + ", originalFileUrl=" + originalFileUrl + ", csv=" + csv
				+ ", csvGenerationDefinition=" + csvGenerationDefinition + ", csvValidationWeb=" + csvValidationWeb
				+ ", arxiuExpedientId=" + arxiuExpedientId + ", arxiuDocumentId=" + arxiuDocumentId + ", printableUrl="
				+ printableUrl + ", eniFileUrl=" + eniFileUrl + ", validationFileUrl=" + validationFileUrl + "]";
	}

}
