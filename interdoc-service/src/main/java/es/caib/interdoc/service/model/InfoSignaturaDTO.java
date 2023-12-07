package es.caib.interdoc.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

/**
 * Dades referents a la signatura d'un document
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
    
    private String signId;
    
    private String fileName;
    
    private String fileMime;
    
    private String signLevel;
    
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
    
    private Date signDate;
    
    private byte[] fileData;
    
    private int status;
    
    private String errorMessage;
    
    private String errorStackTrace;
    
	public InfoSignaturaDTO() {
		super();
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

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getSignId() {
		return signId;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileMime() {
		return fileMime;
	}

	public void setFileMime(String fileMime) {
		this.fileMime = fileMime;
	}

	public String getSignLevel() {
		return signLevel;
	}

	public void setSignLevel(String signLevel) {
		this.signLevel = signLevel;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorStackTrace() {
		return errorStackTrace;
	}

	public void setErrorStackTrace(String errorStackTrace) {
		this.errorStackTrace = errorStackTrace;
	}

	@Override
	public String toString() {
		return "InfoSignaturaDTO [id=" + id + ", signOperation=" + signOperation + ", signType=" + signType
				+ ", signAlgorithm=" + signAlgorithm + ", signId=" + signId + ", fileName=" + fileName + ", fileMime="
				+ fileMime + ", signLevel=" + signLevel + ", signMode=" + signMode + ", signaturesTableLocation="
				+ signaturesTableLocation + ", timestampIncluded=" + timestampIncluded + ", policyIncluded="
				+ policyIncluded + ", eniTipoFirma=" + eniTipoFirma + ", eniPerfilFirma=" + eniPerfilFirma
				+ ", eniRolFirma=" + eniRolFirma + ", eniSignerName=" + eniSignerName + ", eniSignerAdministrationId="
				+ eniSignerAdministrationId + ", checkAdministrationIdOfSigner=" + checkAdministrationIdOfSigner
				+ ", checkDocumentModifications=" + checkDocumentModifications + ", checkValidationSignature="
				+ checkValidationSignature + ", signDate=" + signDate + ", fileData=" + Arrays.toString(fileData)
				+ ", status=" + status + ", errorMessage=" + errorMessage + ", errorStackTrace=" + errorStackTrace
				+ "]";
	}

}
