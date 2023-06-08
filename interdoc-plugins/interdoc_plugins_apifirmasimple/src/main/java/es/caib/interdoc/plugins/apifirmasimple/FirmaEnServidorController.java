package es.caib.interdoc.plugins.apifirmasimple;

import es.caib.interdoc.commons.utils.Utils;
import org.fundaciobit.apisib.apifirmasimple.v1.ApiFirmaEnServidorSimple;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.*;
import org.fundaciobit.apisib.apifirmasimple.v1.jersey.ApiFirmaEnServidorSimpleJersey;
import org.fundaciobit.apisib.core.exceptions.AbstractApisIBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.fundaciobit.pluginsib.core.utils.FileUtils;
import java.io.*;
import java.util.List;
import java.util.Properties;

public class FirmaEnServidorController {

    private static final Logger log = LoggerFactory.getLogger(FirmaEnServidorController.class);

    public static final String BASE_PACKAGE = "es.caib.interdoc.plugin.apifirmaenservidorsimple.";

    public static ApiFirmaEnServidorSimple api;

    public static List<FirmaSimpleAvailableProfile> perfils;


    public static final String PROFILE_PADES_PROPERTY = "PROFILE_PADES";
    public static final String PROFILE_XADES_PROPERTY = "PROFILE_XADES";
    public static final String PROFILE_CADES_PROPERTY = "PROFILE_CADES";
    public static final String PROFILE_MIX_PADES_XADES_CADES = "PROFILE_MIX_PADES_XADES_CADES";


    public static ApiFirmaEnServidorSimple getApiFirmaEnServidorSimple() {

        if (api != null) {
            return api;
        }

        Properties prop = System.getProperties();
        String endPoint = prop.getProperty(BASE_PACKAGE + "endpoint");
        String username = prop.getProperty(BASE_PACKAGE + "usuari");
        String password = prop.getProperty(BASE_PACKAGE + "password");

        if (endPoint == null || username == null || password == null) {
            return null;

        } else {

            log.info("\n Plugin firma servidor => \n" + endPoint);
            return new ApiFirmaEnServidorSimpleJersey(endPoint, username, password);

        }
    }

    public static List<FirmaSimpleAvailableProfile> selectProfiles() throws Exception {

        try {
            final String localeProperty = System.getProperty(BASE_PACKAGE + "locale");
            final String languageUI = Utils.isEmpty(localeProperty) ? "ca" : localeProperty;

            List<FirmaSimpleAvailableProfile> profiles = api.getAvailableProfiles(languageUI);

            if (profiles == null || profiles.size() == 0) {

                log.info("NO HI HA PERFILS !!!");
                throw new Exception("NO HI HA PERFILS PER AQUEST USUARI APLICACIÓ");

            } else {

                for (FirmaSimpleAvailableProfile p : profiles) {
                    log.info(" P[" + p.getCode() + "] " + p.getName() + " => " + p.getDescription());
                }

            }

            return profiles;

        } catch (AbstractApisIBException e) {
            String msg = "Aquesta configuració no és correcte: " + e.getMessage();
            log.error(msg, e);
            throw new Exception("NO HI HA PERFILS PER AQUEST USUARI APLICACIÓ");
        }
    }


    public static void main (String[] args) {

        api = getApiFirmaEnServidorSimple();
        try {
            perfils = selectProfiles();
            signaturePAdES();
        } catch (Exception e) {
            log.error("error amb el profile");
            throw new RuntimeException(e);
        }

    }


    public FirmaEnServidorController() {
        super();
    }


    public static void signaturePAdES() throws Exception, FileNotFoundException, IOException{


        final String perfil = System.getProperty(PROFILE_PADES_PROPERTY);

        FirmaSimpleFile fileToSign = getSimpleFileFromResource("hola.pdf", "application/pdf");

        System.out.println(" PERFIL => " + perfil);
        internalSignDocument(api, perfil, fileToSign);


    }


    public static FirmaSimpleFile getSimpleFileFromResource(String fileName, String mime)
            throws Exception {

        InputStream is = FileUtils.readResource(ApiFirmaEnServidorSimple.class, "testfiles/"
                + fileName);
        File tmp = File.createTempFile("testFile", fileName);
        tmp.deleteOnExit();
        ByteArrayOutputStream fos = new ByteArrayOutputStream();

        FileUtils.copy(is, fos);

        FirmaSimpleFile asf = new FirmaSimpleFile(fileName, mime, fos.toByteArray());

        return asf;

    }


    protected static FirmaSimpleSignatureResult internalSignDocument(ApiFirmaEnServidorSimple api, final String perfil,
                                                                     FirmaSimpleFile fileToSign) throws Exception,
            FileNotFoundException, IOException {
        String signID = "1";
        String name = fileToSign.getNom();
        String reason = "Per aprovar pressuposts";
        String location = "Palma";

        int signNumber = 1;
        String languageSign = "ca";
        long tipusDocumentalID = 99;

        FirmaSimpleFileInfoSignature fileInfoSignature = new FirmaSimpleFileInfoSignature(
                fileToSign, signID, name, reason, location, signNumber, languageSign, tipusDocumentalID);

        String languageUI = "ca";

        // Es la configuració del Servidor (deixam el valor per defecte)
        String username = null; // "anadal";
        String administrationID = null;
        String signerEmail = null;

        FirmaSimpleCommonInfo commonInfo;
        commonInfo = new FirmaSimpleCommonInfo(perfil, languageUI, username, administrationID, signerEmail);

        System.out.println("languageUI = |" + languageUI + "|");

        FirmaSimpleSignDocumentRequest signature;
        signature = new FirmaSimpleSignDocumentRequest(commonInfo, fileInfoSignature);

        FirmaSimpleSignatureResult fullResults = api.signDocument(signature);

        FirmaSimpleStatus transactionStatus = fullResults.getStatus();

        int status = transactionStatus.getStatus();

        switch (status) {

            case FirmaSimpleStatus.STATUS_INITIALIZING: // = 0;
                System.err.println("Initializing ...Unknown Error (???)");
                return null;

            case FirmaSimpleStatus.STATUS_IN_PROGRESS: // = 1;
                System.err.println("In PROGRESS ... Unknown Error (????) ");
                return null;

            case FirmaSimpleStatus.STATUS_FINAL_ERROR: // = -1;
            {
                System.err.println("Error durant la realització de les firmes: "
                        + transactionStatus.getErrorMessage());
                String desc = transactionStatus.getErrorStackTrace();
                if (desc != null) {
                    System.err.println(desc);
                }
                return null;
            }

            case FirmaSimpleStatus.STATUS_CANCELLED: // = -2;
            {
                System.err.println("S'ha cancel·lat el procés de firmat.");
                return null;
            }

            case FirmaSimpleStatus.STATUS_FINAL_OK: // = 2;
            {
                System.out.println(" ===== RESULTAT  =========");


                {
                    System.out.println(" ---- Signature [ " + fullResults.getSignID() + " ]");


                    System.err.println("  RESULT: OK");
                    FirmaSimpleFile fsf = fullResults.getSignedFile();
                    FileOutputStream fos = new FileOutputStream(fsf.getNom());
                    fos.write(fsf.getData());
                    fos.flush();
                    fos.close();
                    System.out.println("  RESULT: Fitxer signat guardat en '" + fsf.getNom() + "'");
                    System.out.println(FirmaSimpleSignedFileInfo.toString(fullResults.getSignedFileInfo()));

                    return fullResults;



                } // Final for de fitxers firmats
            } // Final Case Firma OK
        } // Final Switch Firma

        return null;
    }



}
