package es.caib.interdoc.plugins.apifirmasimple;


import es.caib.interdoc.commons.utils.Utils;
import org.fundaciobit.apisib.apifirmasimple.v1.ApiFirmaEnServidorSimple;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.*;
import org.fundaciobit.apisib.apifirmasimple.v1.jersey.ApiFirmaEnServidorSimpleJersey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiFirmaSimpleServidor {

    private static final Logger log = LoggerFactory.getLogger(ApiFirmaSimpleServidor.class);

    public static final String PROFILE_PADES_PROPERTY = "PROFILE_PADES";

    public static final String PROFILE_XADES_PROPERTY = "PROFILE_XADES";

    public static final String PROFILE_CADES_PROPERTY = "PROFILE_CADES";

    public static final String PROFILE_MIX_PADES_XADES_CADES = "PROFILE_MIX_PADES_XADES_CADES";
    public static final String BASE_PACKAGE = "es.caib.interdoc.plugin.apifirmaenservidorsimple.";

    public FirmaSimpleFile fileToSign = null;
    public ApiFirmaEnServidorSimple api = null;
    public String perfil = null;

    public ApiFirmaSimpleServidor(FirmaSimpleFile file){

        try {
            this.fileToSign = file;
            this.perfil = getPerfilFromProperties();
            if (this.api == null) {
                this.api = getApiFirmaEnServidorSimple();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ApiFirmaEnServidorSimple getApiFirmaEnServidorSimple() throws Exception {

        Properties prop = System.getProperties();
        final String endPoint = prop.getProperty(BASE_PACKAGE + "endpoint");
        final String username = prop.getProperty(BASE_PACKAGE + "usuari");
        final String password = prop.getProperty(BASE_PACKAGE + "password");

        if (endPoint == null || username == null || password == null)
            return null;

        return new ApiFirmaEnServidorSimpleJersey(endPoint, username, password);

    }

    public String getPerfilFromProperties() throws Exception {
        final String perfil = System.getProperty(BASE_PACKAGE + "perfil");
        return (!Utils.isEmpty(perfil)) ? perfil : null;
    }

    public FirmaSimpleSignatureResult signDocument() throws Exception, FileNotFoundException, IOException {

        // TODO d'on agafar aquesta informació?
        String signID = "1";
        String name = fileToSign.getNom();
        String reason = "Per aprovar pressuposts";
        String location = "Palma";

        int signNumber = 1;
        String languageSign = "ca";
        long tipusDocumentalID = 99; // =TD99

        FirmaSimpleFileInfoSignature fileInfoSignature = new FirmaSimpleFileInfoSignature(
                fileToSign, signID, name, reason, location, signNumber, languageSign, tipusDocumentalID);

        // TODO Es la configuració del Servidor (deixam el valor per defecte)
        String languageUI = "ca";
        String username = null;
        String administrationID = null;
        String signerEmail =  null;

        FirmaSimpleCommonInfo commonInfo;
        commonInfo = new FirmaSimpleCommonInfo(perfil, languageUI, username, administrationID, signerEmail);

        log.info("languageUI = |" + languageUI + "|");

        // -------------------------------------------------------------

        log.info("INFO PREVIA SIGNATURA");

        log.info("CommonInfo: administracionID => " + commonInfo.getAdministrationID());
        log.info("CommonInfo: languageUI => " + commonInfo.getLanguageUI());
        log.info("CommonInfo: username => " + commonInfo.getUsername());
        log.info("CommonInfo: signer => " + commonInfo.getSignerEmail());
        log.info("CommonInfo: perfil => " + commonInfo.getSignProfile());

        log.info("FirmaSimpleFileInfoSignature: getSignID => " + fileInfoSignature.getSignID());
        log.info("FirmaSimpleFileInfoSignature: getName => " + fileInfoSignature.getName());
        log.info("FirmaSimpleFileInfoSignature: getFileToSign => " + fileInfoSignature.getFileToSign());
        log.info("FirmaSimpleFileInfoSignature: getLanguageSign => " + fileInfoSignature.getLanguageSign());
        log.info("FirmaSimpleFileInfoSignature: getLocation => " + fileInfoSignature.getLocation());
        log.info("FirmaSimpleFileInfoSignature: getReason => " + fileInfoSignature.getReason());
        log.info("FirmaSimpleFileInfoSignature: getDocumentType => " + fileInfoSignature.getDocumentType());
        log.info("FirmaSimpleFileInfoSignature: getSignNumber => " + fileInfoSignature.getSignNumber());

        log.info("FI PREVIA SIGNATURA");

        // --------------------------------------------------------------

        FirmaSimpleSignDocumentRequest signature;
        signature = new FirmaSimpleSignDocumentRequest(commonInfo, fileInfoSignature);

        FirmaSimpleSignatureResult fullResults = api.signDocument(signature);

        FirmaSimpleStatus transactionStatus = fullResults.getStatus();

        int status = transactionStatus.getStatus();

        switch (status) {

            case FirmaSimpleStatus.STATUS_INITIALIZING: // = 0;
                log.error("Initializing ...Unknown Error (???)");
                return null;

            case FirmaSimpleStatus.STATUS_IN_PROGRESS: // = 1;
                log.error("In PROGRESS ... Unknown Error (????) ");
                return null;

            case FirmaSimpleStatus.STATUS_FINAL_ERROR: // = -1;
            {
                log.error("Error durant la realització de les firmes: "
                        + transactionStatus.getErrorMessage());
                String desc = transactionStatus.getErrorStackTrace();
                if (desc != null) {
                    log.error(desc);
                }
                return null;
            }

            case FirmaSimpleStatus.STATUS_CANCELLED: // = -2;
            {
                log.error("S'ha cancel·lat el procés de firmat.");
                return null;
            }

            case FirmaSimpleStatus.STATUS_FINAL_OK: // = 2;
            {
                log.info(" ===== RESULTAT  =========");


                {
                    log.info(" ---- Signature [ " + fullResults.getSignID() + " ]");

                    log.info("  RESULT: OK");
                    FirmaSimpleFile fsf = fullResults.getSignedFile();
                    FileOutputStream fos = new FileOutputStream(fsf.getNom());
                    fos.write(fsf.getData());
                    fos.flush();
                    fos.close();
                    log.info("  RESULT: Fitxer signat guardat en '" + fsf.getNom() + "'");
                    log.info(FirmaSimpleSignedFileInfo.toString(fullResults.getSignedFileInfo()));

                    return fullResults;

                } // Final for de fitxers firmats
            } // Final Case Firma OK
        } // Final Switch Firma

        return null;
        
    }
    
    
}
