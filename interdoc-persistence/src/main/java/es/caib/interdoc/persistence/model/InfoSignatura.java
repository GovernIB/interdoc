package es.caib.interdoc.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;
import java.util.Objects;

/**
 * Representació de la informació d'una signatura
 *
 * @author jagarcia
 */
@Entity
@SequenceGenerator(name = "infosignatura-sequence", sequenceName = "ITD_INFOSIGNATURA_SEQ", allocationSize = 1)
@Table(name = "ITD_INFOSIGNATURA",
        indexes = {
                @Index(name = "ITD_INFOSIGNATURA_PK_I", columnList = "INFOSIGNATURAID")
        }
)
@NamedQueries({
        @NamedQuery(name = InfoSignatura.GET_ALL, query = "select a from InfoSignatura a")
})
public class InfoSignatura extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String GET_ALL = "InfoSignatura.GET_ALL";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "infosignatura-sequence")
    @Column(name = "INFOSIGNATURAID", nullable = false, length = 19)
    private Long id;

    @NotNull
    @Column(name="signoperation",nullable = false,length = 10)
    private int signOperation;

    @NotNull
    @Column(name="signtype",nullable = false,length = 255)
    @Size( max = 255)
    private String signType;

    @Column(name="signalgorithm",length = 255)
    @Size( max = 255)
    private String signAlgorithm;

    @Column(name="signmode",length = 10)
    private Integer signMode;

    @Column(name="signaturestablelocation",length = 10)
    private Integer signaturesTableLocation;

    @Column(name="timestampincluded",length = 1)
    private Boolean timestampIncluded;

    @Column(name="policyincluded",length = 1)
    private Boolean policyIncluded;

    @Column(name="enitipofirma",length = 255)
    @Size( max = 255)
    private String eniTipoFirma;

    @Column(name="eniperfilfirma",length = 255)
    @Size( max = 255)
    private String eniPerfilFirma;

    @Column(name="enirolfirma",length = 255)
    @Size( max = 255)
    private String eniRolFirma;

    @Column(name="enisignername",length = 255)
    @Size( max = 255)
    private String eniSignerName;

    @Column(name="enisigneradministrationid",length = 255)
    @Size( max = 255)
    private String eniSignerAdministrationId;

    @Column(name="enisignlevel",length = 255)
    @Size( max = 255)
    private String eniSignLevel;

    @Column(name="checkadministrationidofsigner",length = 1)
    private Boolean checkAdministrationIdOfSigner;

    @Column(name="checkdocumentmodifications",length = 1)
    private Boolean checkDocumentModifications;

    @Column(name="checkvalidationsignature",length = 1)
    private Boolean checkValidationSignature;
    
    @Column(name = "signdate", nullable = false)
    private Date signDate;
    
    public InfoSignatura() {    	
    }

	public InfoSignatura(Long id, @NotNull int signOperation, @NotNull @Size(max = 255) String signType,
			@Size(max = 255) String signAlgorithm, Integer signMode, Integer signaturesTableLocation,
			Boolean timestampIncluded, Boolean policyIncluded, @Size(max = 255) String eniTipoFirma,
			@Size(max = 255) String eniPerfilFirma, @Size(max = 255) String eniRolFirma,
			@Size(max = 255) String eniSignerName, @Size(max = 255) String eniSignerAdministrationId,
			@Size(max = 255) String eniSignLevel, Boolean checkAdministrationIdOfSigner,
			Boolean checkDocumentModifications, Boolean checkValidationSignature, Date signDate) {
		super();
		this.id = id;
		this.signOperation = signOperation;
		this.signType = signType;
		this.signAlgorithm = signAlgorithm;
		this.signMode = signMode;
		this.signaturesTableLocation = signaturesTableLocation;
		this.timestampIncluded = timestampIncluded;
		this.policyIncluded = policyIncluded;
		this.eniTipoFirma = eniTipoFirma;
		this.eniPerfilFirma = eniPerfilFirma;
		this.eniRolFirma = eniRolFirma;
		this.eniSignerName = eniSignerName;
		this.eniSignerAdministrationId = eniSignerAdministrationId;
		this.eniSignLevel = eniSignLevel;
		this.checkAdministrationIdOfSigner = checkAdministrationIdOfSigner;
		this.checkDocumentModifications = checkDocumentModifications;
		this.checkValidationSignature = checkValidationSignature;
		this.signDate = signDate;
	}

	public InfoSignatura(@NotNull int signOperation, @NotNull @Size(max = 255) String signType,
			@Size(max = 255) String signAlgorithm, Integer signMode, Integer signaturesTableLocation,
			Boolean timestampIncluded, Boolean policyIncluded, @Size(max = 255) String eniTipoFirma,
			@Size(max = 255) String eniPerfilFirma, @Size(max = 255) String eniRolFirma,
			@Size(max = 255) String eniSignerName, @Size(max = 255) String eniSignerAdministrationId,
			@Size(max = 255) String eniSignLevel, Boolean checkAdministrationIdOfSigner,
			Boolean checkDocumentModifications, Boolean checkValidationSignature, Date signDate) {
		super();
		this.signOperation = signOperation;
		this.signType = signType;
		this.signAlgorithm = signAlgorithm;
		this.signMode = signMode;
		this.signaturesTableLocation = signaturesTableLocation;
		this.timestampIncluded = timestampIncluded;
		this.policyIncluded = policyIncluded;
		this.eniTipoFirma = eniTipoFirma;
		this.eniPerfilFirma = eniPerfilFirma;
		this.eniRolFirma = eniRolFirma;
		this.eniSignerName = eniSignerName;
		this.eniSignerAdministrationId = eniSignerAdministrationId;
		this.eniSignLevel = eniSignLevel;
		this.checkAdministrationIdOfSigner = checkAdministrationIdOfSigner;
		this.checkDocumentModifications = checkDocumentModifications;
		this.checkValidationSignature = checkValidationSignature;
		this.signDate = signDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSignOperation() {
		return signOperation;
	}

	public void setSignOperation(int signOperation) {
		this.signOperation = signOperation;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignAlgorithm() {
		return signAlgorithm;
	}

	public void setSignAlgorithm(String signAlgorithm) {
		this.signAlgorithm = signAlgorithm;
	}

	public Integer getSignMode() {
		return signMode;
	}

	public void setSignMode(Integer signMode) {
		this.signMode = signMode;
	}

	public Integer getSignaturesTableLocation() {
		return signaturesTableLocation;
	}

	public void setSignaturesTableLocation(Integer signaturesTableLocation) {
		this.signaturesTableLocation = signaturesTableLocation;
	}

	public Boolean getTimestampIncluded() {
		return timestampIncluded;
	}

	public void setTimestampIncluded(Boolean timestampIncluded) {
		this.timestampIncluded = timestampIncluded;
	}

	public Boolean getPolicyIncluded() {
		return policyIncluded;
	}

	public void setPolicyIncluded(Boolean policyIncluded) {
		this.policyIncluded = policyIncluded;
	}

	public String getEniTipoFirma() {
		return eniTipoFirma;
	}

	public void setEniTipoFirma(String eniTipoFirma) {
		this.eniTipoFirma = eniTipoFirma;
	}

	public String getEniPerfilFirma() {
		return eniPerfilFirma;
	}

	public void setEniPerfilFirma(String eniPerfilFirma) {
		this.eniPerfilFirma = eniPerfilFirma;
	}

	public String getEniRolFirma() {
		return eniRolFirma;
	}

	public void setEniRolFirma(String eniRolFirma) {
		this.eniRolFirma = eniRolFirma;
	}

	public String getEniSignerName() {
		return eniSignerName;
	}

	public void setEniSignerName(String eniSignerName) {
		this.eniSignerName = eniSignerName;
	}

	public String getEniSignerAdministrationId() {
		return eniSignerAdministrationId;
	}

	public void setEniSignerAdministrationId(String eniSignerAdministrationId) {
		this.eniSignerAdministrationId = eniSignerAdministrationId;
	}

	public String getEniSignLevel() {
		return eniSignLevel;
	}

	public void setEniSignLevel(String eniSignLevel) {
		this.eniSignLevel = eniSignLevel;
	}

	public Boolean getCheckAdministrationIdOfSigner() {
		return checkAdministrationIdOfSigner;
	}

	public void setCheckAdministrationIdOfSigner(Boolean checkAdministrationIdOfSigner) {
		this.checkAdministrationIdOfSigner = checkAdministrationIdOfSigner;
	}

	public Boolean getCheckDocumentModifications() {
		return checkDocumentModifications;
	}

	public void setCheckDocumentModifications(Boolean checkDocumentModifications) {
		this.checkDocumentModifications = checkDocumentModifications;
	}

	public Boolean getCheckValidationSignature() {
		return checkValidationSignature;
	}

	public void setCheckValidationSignature(Boolean checkValidationSignature) {
		this.checkValidationSignature = checkValidationSignature;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InfoSignatura other = (InfoSignatura) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "InfoSignatura [id=" + id + ", signOperation=" + signOperation + ", signType=" + signType
				+ ", signAlgorithm=" + signAlgorithm + ", signMode=" + signMode + ", signaturesTableLocation="
				+ signaturesTableLocation + ", timestampIncluded=" + timestampIncluded + ", policyIncluded="
				+ policyIncluded + ", eniTipoFirma=" + eniTipoFirma + ", eniPerfilFirma=" + eniPerfilFirma
				+ ", eniRolFirma=" + eniRolFirma + ", eniSignerName=" + eniSignerName + ", eniSignerAdministrationId="
				+ eniSignerAdministrationId + ", eniSignLevel=" + eniSignLevel + ", checkAdministrationIdOfSigner="
				+ checkAdministrationIdOfSigner + ", checkDocumentModifications=" + checkDocumentModifications
				+ ", checkValidationSignature=" + checkValidationSignature + ", signDate=" + signDate + "]";
	}
    
   
}
