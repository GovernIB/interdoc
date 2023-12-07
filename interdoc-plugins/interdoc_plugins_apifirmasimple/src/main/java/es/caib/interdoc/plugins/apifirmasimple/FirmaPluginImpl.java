package es.caib.interdoc.plugins.apifirmasimple;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.fundaciobit.apisib.apifirmasimple.v1.ApiFirmaEnServidorSimple;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleAvailableProfile;
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
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.commons.utils.Base64;
import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.service.model.FitxerDTO;
import es.caib.interdoc.service.model.InfoSignaturaDTO;

public class FirmaPluginImpl extends AbstractPluginProperties implements InterdocFirmaPlugin {

	protected final Logger LOG = LoggerFactory.getLogger(FirmaPluginImpl.class);

	private static final String PROPERTY_PROFILE_CADES = INTERDOC_FIRMA_PLUGIN_PROPERTY + "profilecades";
	private static final String PROPERTY_PROFILE_PADES = INTERDOC_FIRMA_PLUGIN_PROPERTY + "profilepades";
	private static final String PROPERTY_PROFILE_XADES = INTERDOC_FIRMA_PLUGIN_PROPERTY + "profilexades";
	private static final String PROPERTY_USERNAME = INTERDOC_FIRMA_PLUGIN_PROPERTY + "nombre";
	private static final String PROPERTY_ADMINISTRATIONID = INTERDOC_FIRMA_PLUGIN_PROPERTY + "administracionId";
	private static final String PROPERTY_EMAIL = INTERDOC_FIRMA_PLUGIN_PROPERTY + "email";
	private static final String PROPERTY_IDIOMA = INTERDOC_FIRMA_PLUGIN_PROPERTY + "languageUI";
	private static final String PROPERTY_IDIOMASIGNATURA = INTERDOC_FIRMA_PLUGIN_PROPERTY + "languageSign";
	private static final String PROPERTY_LOCALITZACIO = INTERDOC_FIRMA_PLUGIN_PROPERTY + "localizacion";
	private static final String PROPERTY_MOTIU = INTERDOC_FIRMA_PLUGIN_PROPERTY + "motivo";
	private static final String PROPERTY_TIPUSDOCUMENTALID = INTERDOC_FIRMA_PLUGIN_PROPERTY + "tipodocumentalid";
	private static final String PROPERTY_ALIAS = INTERDOC_FIRMA_PLUGIN_PROPERTY + "alias";
	private static final String PROPERTY_PERFIL = INTERDOC_FIRMA_PLUGIN_PROPERTY + "perfil";
	private static final String PROPERTY_SIGNEDPATH = INTERDOC_FIRMA_PLUGIN_PROPERTY + "path";
	private static final String PROPERTY_SIGNID = INTERDOC_FIRMA_PLUGIN_PROPERTY + "signId";
	private static final String PROPERTY_PROFILE = INTERDOC_FIRMA_PLUGIN_PROPERTY + "profile";

	private ApiFirmaEnServidorSimple plugin;

	private Properties propietats;

	public FirmaPluginImpl() {
		super();
		LOG.info("Inici del plugin Firma Impl");
	}

	public FirmaPluginImpl(String clase, Properties props) {
		carregarProperties(props);
	}

	public Properties getPropietats() {
		return propietats;
	}

	public void setPropietats(Properties propietats) {
		this.propietats = propietats;
	}

	public ApiFirmaEnServidorSimple getPlugin() {
		return plugin;
	}

	public void setPlugin(ApiFirmaEnServidorSimple plugin) {
		this.plugin = plugin;
	}

	private void carregarPropertiesFile() {

		Config config = ConfigProvider.getConfig();

		propietats.put(PROPERTY_PROFILE_CADES, config.getValue(PROPERTY_PROFILE_CADES, String.class));
		propietats.put(PROPERTY_PROFILE_PADES, config.getValue(PROPERTY_PROFILE_PADES, String.class));
		propietats.put(PROPERTY_PROFILE_XADES, config.getValue(PROPERTY_PROFILE_XADES, String.class));

		propietats.put(PROPERTY_USERNAME, config.getValue(PROPERTY_USERNAME, String.class));
		propietats.put(PROPERTY_ADMINISTRATIONID, config.getValue(PROPERTY_ADMINISTRATIONID, String.class));
		propietats.put(PROPERTY_EMAIL, config.getValue(PROPERTY_EMAIL, String.class));
		propietats.put(PROPERTY_IDIOMA, config.getValue(PROPERTY_IDIOMA, String.class));
		propietats.put(PROPERTY_IDIOMASIGNATURA, config.getValue(PROPERTY_IDIOMASIGNATURA, String.class));
		propietats.put(PROPERTY_LOCALITZACIO, config.getValue(PROPERTY_LOCALITZACIO, String.class));
		propietats.put(PROPERTY_MOTIU, config.getValue(PROPERTY_MOTIU, String.class));
		propietats.put(PROPERTY_TIPUSDOCUMENTALID, config.getValue(PROPERTY_TIPUSDOCUMENTALID, String.class));
		propietats.put(PROPERTY_ALIAS, config.getValue(PROPERTY_ALIAS, String.class));
		propietats.put(PROPERTY_SIGNID, config.getValue(PROPERTY_SIGNID, String.class));
		propietats.put(PROPERTY_PROFILE, config.getValue(PROPERTY_PROFILE, String.class));

		try {

			if (Utils.isEmpty(propietats.getProperty(PROPERTY_PERFIL))) {
				logErrorPerfilBuit("PROFILE_PADES");
				propietats.put(PROPERTY_PERFIL, propietats.getProperty(PROPERTY_PROFILE_PADES));
			}

			if (Utils.isEmpty(propietats.getProperty(PROPERTY_SIGNEDPATH))) {
				propietats.put(PROPERTY_SIGNEDPATH, config.getValue(PROPERTY_SIGNEDPATH, String.class));
			}

			setPropietats(propietats);

			if (Configuracio.isDesenvolupament()) {
				LOG.info("------------ PROPIEDADES FIRMA FILE -------------------");
				propietats.stringPropertyNames().forEach(x -> LOG.info(x + " => " + propietats.getProperty(x)));
				LOG.info("---------------------------------------------------");
			}

		} catch (Exception e) {
			LOG.error("S'ha produit un error alhora de carregar les propietats. ");
			e.printStackTrace();
		}

	}

	public void carregarProperties(Properties props) {
		setPropietats(props);

		if (Utils.isEmpty(propietats.getProperty(PROPERTY_PERFIL))) {
			logErrorPerfilBuit("PROFILE_PADES");
			propietats.put(PROPERTY_PERFIL, propietats.getProperty(PROPERTY_PROFILE_PADES));
			LOG.info("Set perfil => " + propietats.getProperty(PROPERTY_PROFILE_PADES));
		}

		if (Configuracio.isDesenvolupament()) {
			try {
				LOG.info("------------ PROPIEDADES FIRMA DB -------------------");
				propietats.stringPropertyNames().forEach(x -> LOG.info(x + " => " + propietats.getProperty(x)));
				LOG.info("---------------------------------------------------");

			} catch (Exception e) {
				LOG.error("S'ha produit un error alhora de carregar les propietats");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getAvailableProfiles() throws Exception {

		final String languagesUI[] = new String[] { "ca", "es" };

		for (String languageUI : languagesUI) {
			LOG.info(" ==== LanguageUI : " + languageUI + " ===========");

			if (plugin == null)
				plugin = getApiFirmaEnServidorSimple();

			List<FirmaSimpleAvailableProfile> listProfiles = plugin.getAvailableProfiles(languageUI);
			if (listProfiles.size() == 0) {
				LOG.info("NO HI HA PERFILS PER AQUEST USUARI APLICACIÓ");
			} else {
				for (FirmaSimpleAvailableProfile ap : listProfiles) {
					LOG.info("  + " + ap.getName() + ":");
					LOG.info("      * Codi: " + ap.getCode());
					LOG.info("      * Desc: " + ap.getDescription());
				}
			}

		}

	}

	@Override
	public InfoSignaturaDTO firmarDocument(FitxerDTO fitxer) throws Exception {

		FirmaSimpleFile fileToSign = new FirmaSimpleFile();
		fileToSign.setNom(fitxer.getNom());
		fileToSign.setMime(fitxer.getMime());
		fileToSign.setData(fitxer.getData());

		LOG.info("fileToSign.nom => " + fileToSign.getNom());
		LOG.info("fileToSign.mime => " + fileToSign.getMime());
		LOG.info("fileToSign.data => " + fileToSign.getData().toString());
		LOG.info("fileToSign.data string => " + Base64.encode(fileToSign.getData()));

		FirmaSimpleSignatureResult resultFirma = null;
		resultFirma = internalSignDocument(fileToSign);

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

	protected FirmaSimpleSignatureResult internalSignDocument(FirmaSimpleFile fileToSign)
			throws Exception, FileNotFoundException, IOException {

		String signID = String.valueOf(System.currentTimeMillis());
		String name = fileToSign.getNom();
		String reason = propietats.getProperty(PROPERTY_MOTIU);
		String location = propietats.getProperty(PROPERTY_LOCALITZACIO);

		int signNumber = 1;
		String languageSign = propietats.getProperty(PROPERTY_IDIOMASIGNATURA);
		long tipusDocumentalID = Long.parseLong(propietats.getProperty(PROPERTY_TIPUSDOCUMENTALID)); // =TD99

		FirmaSimpleFileInfoSignature fileInfoSignature = new FirmaSimpleFileInfoSignature(fileToSign, signID, name,
				reason, location, signNumber, languageSign, tipusDocumentalID);

		String languageUI = propietats.getProperty(PROPERTY_IDIOMA);
		String username = propietats.getProperty(PROPERTY_ALIAS);
		String administrationID = "";
		String signerEmail = "";
		String perfil = propietats.getProperty(PROPERTY_PERFIL);

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

		if (plugin == null) {
			plugin = getApiFirmaEnServidorSimple();
		}

		FirmaSimpleSignatureResult fullResults = plugin.signDocument(signature);

		// Corregir error perfil de firma no informat
//			if (fullResults != null)
//				if(fullResults.getSignedFileInfo() != null)
//					if (Utils.isEmpty(fullResults.getSignedFileInfo().getEniPerfilFirma()))
//						fullResults.getSignedFileInfo().setEniPerfilFirma(commonInfo.getSignProfile());
//					else
//						logError("fullResults.getSignedFileInfo().getEniPerfilFirma()", "");
//				else
//					logError("getSignedFileInfo", "");
//			else
//				logError("fullResults", "");

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
				LOG.info(" ===== RESULTAT OK =========");
				{
					LOG.info(" ---- Signature [ " + fullResults.getSignID() + " ]");
	
					FirmaSimpleFile fsf = fullResults.getSignedFile();
	
					FileOutputStream fos = new FileOutputStream(fsf.getNom());
					fos.write(fsf.getData());
					fos.flush();
					fos.close();
	
					if (Configuracio.isDesenvolupament()) {
						LOG.info("  RESULT: Fitxer signat guardat en '" + new File(".").getAbsolutePath() + fsf.getNom()
								+ "'");
						printSignatureInfo(fullResults);
					}
	
					return fullResults;
	
				} // Final for de fitxers firmats
			} // Final Case Firma OK
		} // Final Switch Firma

		return null;
	}

	@Override
	public void printSignatureInfo(FirmaSimpleSignatureResult fssr) {
		LOG.info("printSignatureInfo =>" + FirmaSimpleSignedFileInfo.toString(fssr.getSignedFileInfo()));
	}

	private void logErrorPerfilBuit(final String perfilProperty) {
		LOG.error("La propietat " + perfilProperty
				+ " està buida. Això significa que si l'usuari aplicacio té més d'un perfil assignat, llavors llançarà un error.");
	}

	private void logError(final String property, String message) {
		LOG.error("La propietat " + property + " està buida. " + message);
	}

	private ApiFirmaEnServidorSimple getApiFirmaEnServidorSimple() throws Exception {

		final String endPoint = Configuracio.getProperty(INTERDOC_FIRMA_PLUGIN_PROPERTY + "endpoint");
		final String username = Configuracio.getProperty(INTERDOC_FIRMA_PLUGIN_PROPERTY + "usuari");
		final String password = Configuracio.getProperty(INTERDOC_FIRMA_PLUGIN_PROPERTY + "password");

		if (endPoint == null || username == null || password == null) {
			LOG.error("Falten les credencials del plugin firma");
			return null;
		}

		return new ApiFirmaEnServidorSimpleJersey(endPoint, username, password);

	}

}
