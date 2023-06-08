package es.caib.interdoc.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Dades referents a una aplicació.
 *
 * @author jagarcia
 */
@Schema(name = "InfoSignatura")
public class InfoSignaturaDTO {

    private Long id;
    
    @NotNull
    private int signOperation;

    @NotNull
    @Size(max = 255)
    private String signType;
    
    @Size(max = 255)
    private String signAlgorithm;
    
    private int signMode;
    
    private int signaturesTableLocation;
    
    private Boolean timestampIncluded;
    
    private Boolean policyIncluded;
    
    @Size(max = 255)
    private String eniTipoFirma;
    
    @Size(max = 255)
    private String eniPerfilFirma;
    
    @Size(max = 255)
    private String eniRolFirma;
    
    @Size(max = 255)
    private String eniSignerName;
    
    @Size(max = 255)
    private String eniSignerAdministrationId;
    
    private Boolean checkAdministrationIdOfSigner;
    
    private Boolean checkDocumentModifications;
    
    private Boolean checkValidationSignature;
    
	public InfoSignaturaDTO() {
		super();
	}

	public InfoSignaturaDTO(Long id, @NotNull int signOperation, @NotNull @Size(max = 255) String signType,
			@Size(max = 255) String signAlgorithm, int signMode, int signaturesTableLocation, Boolean timestampIncluded,
			Boolean policyIncluded, @Size(max = 255) String eniTipoFirma, @Size(max = 255) String eniPerfilFirma,
			@Size(max = 255) String eniRolFirma, @Size(max = 255) String eniSignerName,
			@Size(max = 255) String eniSignerAdministrationId, Boolean checkAdministrationIdOfSigner,
			Boolean checkDocumentModifications, Boolean checkValidationSignature) {
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
		this.checkAdministrationIdOfSigner = checkAdministrationIdOfSigner;
		this.checkDocumentModifications = checkDocumentModifications;
		this.checkValidationSignature = checkValidationSignature;
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

	public int getSignMode() {
		return signMode;
	}

	public void setSignMode(int signMode) {
		this.signMode = signMode;
	}

	public int getSignaturesTableLocation() {
		return signaturesTableLocation;
	}

	public void setSignaturesTableLocation(int signaturesTableLocation) {
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


	@Override
	public String toString() {
		return "InfoSignaturaDTO [id=" + id + ", signOperation=" + signOperation + ", signType=" + signType
				+ ", signAlgorithm=" + signAlgorithm + ", signMode=" + signMode + ", signaturesTableLocation="
				+ signaturesTableLocation + ", timestampIncluded=" + timestampIncluded + ", policyIncluded="
				+ policyIncluded + ", eniTipoFirma=" + eniTipoFirma + ", eniPerfilFirma=" + eniPerfilFirma
				+ ", eniRolFirma=" + eniRolFirma + ", eniSignerName=" + eniSignerName + ", eniSignerAdministrationId="
				+ eniSignerAdministrationId + ", checkAdministrationIdOfSigner=" + checkAdministrationIdOfSigner
				+ ", checkDocumentModifications=" + checkDocumentModifications + ", checkValidationSignature="
				+ checkValidationSignature + "]";
	}
    

    
    
    
}
