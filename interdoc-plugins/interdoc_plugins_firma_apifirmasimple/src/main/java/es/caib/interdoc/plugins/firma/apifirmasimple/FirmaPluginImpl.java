package es.caib.interdoc.plugins.firma.apifirmasimple;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.fundaciobit.apisib.apifirmasimple.v1.ApiFirmaEnServidorSimple;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleCommonInfo;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleFile;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleFileInfoSignature;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleSignDocumentRequest;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleSignatureResult;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleSignedFileInfo;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleSignerInfo;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleStatus;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleValidationInfo;
import org.fundaciobit.apisib.apifirmasimple.v1.jersey.ApiFirmaEnServidorSimpleJersey;
import org.fundaciobit.pluginsib.core.v3.utils.AbstractPluginProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.plugins.firma.api.InterdocFirmaPlugin;
import es.caib.interdoc.service.model.FitxerDTO;
import es.caib.interdoc.service.model.InfoSignaturaDTO;

public class FirmaPluginImpl extends AbstractPluginProperties implements InterdocFirmaPlugin {

    protected static final String APIFIRMASIMPLE_FIRMA_PLUGIN_PROPERTY = BASE_FIRMA_PLUGIN_PROPERTY+"apifirmasimple.";

	protected final Logger LOG = LoggerFactory.getLogger(FirmaPluginImpl.class);
	
	private static final String PROPERTY_ENDPOINT = APIFIRMASIMPLE_FIRMA_PLUGIN_PROPERTY + "endpoint";
	private static final String PROPERTY_USERNAME = APIFIRMASIMPLE_FIRMA_PLUGIN_PROPERTY + "usuari";
    private static final String PROPERTY_PASSWORD = APIFIRMASIMPLE_FIRMA_PLUGIN_PROPERTY + "password";
	
	private static final String PROPERTY_PROFILE_PADES = APIFIRMASIMPLE_FIRMA_PLUGIN_PROPERTY + "profilepades";
	private static final String PROPERTY_PROFILE_XADES = APIFIRMASIMPLE_FIRMA_PLUGIN_PROPERTY + "profilexades";
	
	private static final String PROPERTY_IDIOMASIGNATURA = APIFIRMASIMPLE_FIRMA_PLUGIN_PROPERTY + "languageSign";
	private static final String PROPERTY_LOCALITZACIO = APIFIRMASIMPLE_FIRMA_PLUGIN_PROPERTY + "localizacion";
	private static final String PROPERTY_MOTIU = APIFIRMASIMPLE_FIRMA_PLUGIN_PROPERTY + "motivo";
	private static final String PROPERTY_TIPUSDOCUMENTALID = APIFIRMASIMPLE_FIRMA_PLUGIN_PROPERTY + "tipodocumentalid";
	private static final String PROPERTY_ALIAS = APIFIRMASIMPLE_FIRMA_PLUGIN_PROPERTY + "alias";
	
	
    //private static final String PROPERTY_PROFILE_CADES = INTERDOC_FIRMA_PLUGIN_PROPERTY + "profilecades";
	//private static final String PROPERTY_USERNAME = INTERDOC_FIRMA_PLUGIN_PROPERTY + "nombre";
    //private static final String PROPERTY_ADMINISTRATIONID = INTERDOC_FIRMA_PLUGIN_PROPERTY + "administracionId";
    //private static final String PROPERTY_EMAIL = INTERDOC_FIRMA_PLUGIN_PROPERTY + "email";
	//private static final String PROPERTY_PERFIL = INTERDOC_FIRMA_PLUGIN_PROPERTY + "perfil";
	/*private static final String PROPERTY_SIGNEDPATH = INTERDOC_FIRMA_PLUGIN_PROPERTY + "path";
	private static final String PROPERTY_SIGNID = INTERDOC_FIRMA_PLUGIN_PROPERTY + "signId";
	private static final String PROPERTY_PROFILE = INTERDOC_FIRMA_PLUGIN_PROPERTY + "profile";*/

	//private Properties propietats;

	public FirmaPluginImpl() {
      super();
   }

   public FirmaPluginImpl(String propertyKeyBase) {
      super(propertyKeyBase);
   }

   public FirmaPluginImpl(String propertyKeyBase, Properties properties) {
      super(propertyKeyBase, properties);
   }

	

    @Override
	public InfoSignaturaDTO firmarDocument(FitxerDTO fitxer, String languageUI) throws Exception {

		FirmaSimpleFile fileToSign = new FirmaSimpleFile();
		fileToSign.setNom(fitxer.getNom());
		fileToSign.setMime(fitxer.getMime());
		fileToSign.setData(fitxer.getData());

		FirmaSimpleSignatureResult resultFirma = null;
		try {
			resultFirma = internalSignDocument(fileToSign, languageUI);
		} catch (Exception e) {
		    String msg = e.getMessage();
			LOG.error("S'ha produit un error durant el proces de firma: "+msg, e);
            throw new Exception(e);
		}

		if (resultFirma != null) {

			InfoSignaturaDTO infoFirma = new InfoSignaturaDTO();
			infoFirma.setSignId(resultFirma.getSignID());

			FirmaSimpleFile fsfile = resultFirma.getSignedFile();
			infoFirma.setFileName(fsfile.getNom());
			infoFirma.setFileMime(fsfile.getMime());
			infoFirma.setFileData(fsfile.getData());

			FirmaSimpleStatus fstatus = resultFirma.getStatus();
			infoFirma.setStatus(fstatus.getStatus());
			infoFirma.setErrorMessage(fstatus.getErrorMessage());
			infoFirma.setErrorStackTrace(fstatus.getErrorStackTrace());

			FirmaSimpleSignedFileInfo fssf = resultFirma.getSignedFileInfo();
			infoFirma.setSignOperation(fssf.getSignOperation());
			infoFirma.setSignType(fssf.getSignType());
			infoFirma.setSignAlgorithm(fssf.getSignAlgorithm());
			infoFirma.setSignMode(fssf.getSignMode());
			infoFirma.setSignaturesTableLocation(fssf.getSignaturesTableLocation());
			infoFirma.setTimestampIncluded(fssf.isTimeStampIncluded());
			infoFirma.setPolicyIncluded(fssf.isPolicyIncluded());
			infoFirma.setEniPerfilFirma(fssf.getEniPerfilFirma());
			infoFirma.setEniTipoFirma(fssf.getEniTipoFirma());

			FirmaSimpleSignerInfo fssi = fssf.getSignerInfo();

			if (fssi != null) {

				if (Utils.isNotEmpty(fssi.getEniRolFirma()))
					infoFirma.setEniRolFirma(fssi.getEniRolFirma());
				if (Utils.isNotEmpty(fssi.getEniSignerName()))
					infoFirma.setEniSignerName(fssi.getEniSignerName());
				if (Utils.isNotEmpty(fssi.getEniSignerAdministrationId()))
					infoFirma.setEniSignerAdministrationId(fssi.getEniSignerAdministrationId());
				if (Utils.isNotEmpty(fssi.getEniSignLevel()))
					infoFirma.setSignLevel(fssi.getEniSignLevel());
				if (fssi.getSignDate() != null)
					infoFirma.setSignDate(fssi.getSignDate());

			}
			FirmaSimpleValidationInfo fsvi = fssf.getValidationInfo();
			infoFirma.setCheckAdministrationIdOfSigner(fsvi.getCheckAdministrationIDOfSigner());
			infoFirma.setCheckValidationSignature(fsvi.getCheckValidationSignature());
			infoFirma.setCheckDocumentModifications(fsvi.getCheckDocumentModifications());

			return infoFirma;
		}

		return null;

	}

	protected FirmaSimpleSignatureResult internalSignDocument(FirmaSimpleFile fileToSign, String languageUI)
			throws Exception, FileNotFoundException, IOException {

		String signID = String.valueOf(System.currentTimeMillis());
		String name = fileToSign.getNom();
		String reason = this.getPropertyRequired(PROPERTY_MOTIU);
		String location = this.getProperty(PROPERTY_LOCALITZACIO);

		int signNumber = 1;
		String languageSign = this.getPropertyRequired(PROPERTY_IDIOMASIGNATURA);
		long tipusDocumentalID = Long.parseLong(this.getPropertyRequired(PROPERTY_TIPUSDOCUMENTALID)); // =TD99

		FirmaSimpleFileInfoSignature fileInfoSignature = new FirmaSimpleFileInfoSignature(fileToSign, signID, name,
				reason, location, signNumber, languageSign, tipusDocumentalID);
        
		String username = this.getProperty(PROPERTY_ALIAS);
		String administrationID = "";
		String signerEmail = "";

		// Si és un fitxer PDF utilitzarem ENVIAFIB_PADES, sino CADES_ATACHED
		String perfil = (fileInfoSignature.getName().contains(".pdf")) ? this.getPropertyRequired(PROPERTY_PROFILE_PADES)
				: this.getPropertyRequired(PROPERTY_PROFILE_XADES);

		FirmaSimpleCommonInfo commonInfo;
		commonInfo = new FirmaSimpleCommonInfo(perfil, languageUI, username, administrationID, signerEmail);

		if (Configuracio.isDesenvolupament()) {
			LOG.info("INFO PREVIA SIGNATURA");

			LOG.info("CommonInfo: administracionID => " + commonInfo.getAdministrationID());
			LOG.info("CommonInfo: languageUI => " + commonInfo.getLanguageUI());
			LOG.info("CommonInfo: username => " + commonInfo.getUsername());
			LOG.info("CommonInfo: signer => " + commonInfo.getSignerEmail());
			LOG.info("CommonInfo: perfil => " + commonInfo.getSignProfile());

			LOG.info("FirmaSimpleFileInfoSignature: getSignID => " + fileInfoSignature.getSignID());
			LOG.info("FirmaSimpleFileInfoSignature: getName => " + fileInfoSignature.getName());
			LOG.info("FirmaSimpleFileInfoSignature: getFileToSign => " + fileInfoSignature.getFileToSign());
			LOG.info("FirmaSimpleFileInfoSignature: getLanguageSign => " + fileInfoSignature.getLanguageSign());
			LOG.info("FirmaSimpleFileInfoSignature: getLocation => " + fileInfoSignature.getLocation());
			LOG.info("FirmaSimpleFileInfoSignature: getReason => " + fileInfoSignature.getReason());
			LOG.info("FirmaSimpleFileInfoSignature: getDocumentType => " + fileInfoSignature.getDocumentType());
			LOG.info("FirmaSimpleFileInfoSignature: getSignNumber => " + fileInfoSignature.getSignNumber());

			LOG.info("FI PREVIA SIGNATURA");
		}

        
		FirmaSimpleSignDocumentRequest signature = new FirmaSimpleSignDocumentRequest(commonInfo, fileInfoSignature);
		
		ApiFirmaEnServidorSimple apiFirmaEnServidorSimple = getApiFirmaEnServidorSimple();

		if(apiFirmaEnServidorSimple == null) {
		    String msg = "No s'ha pogut inicialitzar el client de firma en servidor simple. Revisa la configuració del plugin.";
            LOG.error(msg);
            throw new Exception();
        }
		
		FirmaSimpleSignatureResult fullResults = apiFirmaEnServidorSimple.signDocument(signature);

		FirmaSimpleStatus transactionStatus = fullResults.getStatus();
		int status = transactionStatus.getStatus();

		switch (status) {

		case FirmaSimpleStatus.STATUS_INITIALIZING: // = 0;
			LOG.error("Initializing ...Unknown Error (???)");
			return null;

		case FirmaSimpleStatus.STATUS_IN_PROGRESS: // = 1;
			LOG.error("In PROGRESS ... Unknown Error (????) ");
			return null;

		case FirmaSimpleStatus.STATUS_FINAL_ERROR: // = -1;
		{
			LOG.error("Error durant la realització de les firmes: " + transactionStatus.getErrorMessage());
			String desc = transactionStatus.getErrorStackTrace();
			if (desc != null) {
				LOG.error(desc);
			}
			return null;
		}

		case FirmaSimpleStatus.STATUS_CANCELLED: // = -2;
		{
			LOG.error("S'ha cancel·lat el procés de firmat.");
			return null;
		}

		case FirmaSimpleStatus.STATUS_FINAL_OK: // = 2;
		{
			
			{
			    				
				FirmaSimpleFile fsf = fullResults.getSignedFile();

				FileOutputStream fos = new FileOutputStream(fsf.getNom());
				fos.write(fsf.getData());
				fos.flush();
				fos.close();

				if (Configuracio.isDesenvolupament()) {

					LOG.info("  RESULT: Fitxer signat guardat en '" + new File(".").getAbsolutePath() + fsf.getNom()
							+ "'");
					printSignatureInfo(fullResults);
					
						// Guardam copia a la ruta de files signed
						String filePath = Configuracio.getFileTempPath() + fullResults.getSignID() + "_" + fsf.getNom();
						FileOutputStream fos2 = new FileOutputStream(filePath);
						fos2.write(fsf.getData());
						fos2.close();
						LOG.info("Save file Signed: " + filePath);
					
				}

				return fullResults;

			} // Final for de fitxers firmats
		} // Final Case Firma OK
		} // Final Switch Firma

		return null;
	}

	
	public void printSignatureInfo(FirmaSimpleSignatureResult fssr) {
		LOG.info("printSignatureInfo =>" + FirmaSimpleSignedFileInfo.toString(fssr.getSignedFileInfo()));
	}

	
	private ApiFirmaEnServidorSimple getApiFirmaEnServidorSimple() throws Exception {
	    
        final String endPoint = getProperty(PROPERTY_ENDPOINT);
		final String username = getProperty(PROPERTY_USERNAME);
		final String password = getProperty(PROPERTY_PASSWORD);



		if (endPoint == null || username == null || password == null) {
			LOG.error("Falten les credencials del plugin firma");
			return null;
		}

		return new ApiFirmaEnServidorSimpleJersey(endPoint, username, password);
	}

}
