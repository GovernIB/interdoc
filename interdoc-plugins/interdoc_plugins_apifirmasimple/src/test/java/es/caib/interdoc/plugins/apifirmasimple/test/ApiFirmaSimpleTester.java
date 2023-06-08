package es.caib.interdoc.plugins.apifirmasimple.test;

import org.fundaciobit.apisib.apifirmasimple.v1.ApiFirmaEnServidorSimple;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.*;
import org.fundaciobit.apisib.apifirmasimple.v1.exceptions.NoAvailablePluginException;
import org.fundaciobit.apisib.apifirmasimple.v1.jersey.ApiFirmaEnServidorSimpleJersey;
import org.fundaciobit.apisib.core.exceptions.ApisIBClientException;
import org.fundaciobit.apisib.core.exceptions.ApisIBServerException;
import org.fundaciobit.pluginsib.core.utils.FileUtils;

import java.io.*;
import java.util.Properties;

public class ApiFirmaSimpleTester {

    public static final String PROFILE_PADES_PROPERTY = "PROFILE_PADES";

    public static final String PROFILE_XADES_PROPERTY = "PROFILE_XADES";

    public static final String PROFILE_CADES_PROPERTY = "PROFILE_CADES";

    public static final String PROFILE_MIX_PADES_XADES_CADES = "PROFILE_MIX_PADES_XADES_CADES";

    public static void main(String[] args) {

        try {

            System.out.println("INICI");

            ApiFirmaSimpleTester tester = new ApiFirmaSimpleTester();

            tester.testSignatureServerPAdES();

        }catch (NoAvailablePluginException nape) {

            nape.printStackTrace();

            System.err
                    .println("No s'ha trobat cap plugin que pugui realitzar la firma o alguna de les firmes sol·licitades.");

        } catch (ApisIBClientException client) {

            client.printStackTrace();

            System.err
                    .println("S'ha produït un error intentant contactar amb el servidor intermedi:"
                            + client.getMessage());

        } catch (ApisIBServerException server) {

            server.printStackTrace();

            System.err.println("S'ha produït un error en el servidor intermedi:"
                    + server.getMessage());

        } catch (Exception e) {
            e.printStackTrace();

            System.err.println("Error desconegut intentant realitzar les firmes: " + e.getMessage());
        }

        System.out.println("FI");
    }

    public void testSignatureServerPAdES() throws Exception, FileNotFoundException, IOException {

        Properties prop = getConfigProperties();

        ApiFirmaEnServidorSimple api = getApiFirmaEnServidorSimple(prop);

        final String perfil = prop.getProperty(PROFILE_PADES_PROPERTY);
        if (perfil == null) {
            logErrorPerfilBuit(PROFILE_PADES_PROPERTY);
        }

        FirmaSimpleFile fileToSign = getSimpleFileFromResource("hola.pdf", "application/pdf");

        System.out.println(" PERFIL => " + perfil);
        internalSignDocument(api, perfil, fileToSign);
    }

    protected FirmaSimpleSignatureResult internalSignDocument(ApiFirmaEnServidorSimple api,
                                                              final String perfil,
                                                              FirmaSimpleFile fileToSign) throws Exception,
            FileNotFoundException, IOException {

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
        String username =  null;
        String administrationID = null;
        String signerEmail =  null;

        FirmaSimpleCommonInfo commonInfo;
        commonInfo = new FirmaSimpleCommonInfo(perfil, languageUI, username, administrationID, signerEmail);

        System.out.println("languageUI = |" + languageUI + "|");

        // -------------------------------------------------------------

        System.out.println("INFO PREVIA SIGNATURA");

        System.out.println("CommonInfo: administracionID => " + commonInfo.getAdministrationID());
        System.out.println("CommonInfo: languageUI => " + commonInfo.getLanguageUI());
        System.out.println("CommonInfo: username => " + commonInfo.getUsername());
        System.out.println("CommonInfo: signer => " + commonInfo.getSignerEmail());
        System.out.println("CommonInfo: perfil => " + commonInfo.getSignProfile());

        System.out.println("FirmaSimpleFileInfoSignature: getSignID => " + fileInfoSignature.getSignID());
        System.out.println("FirmaSimpleFileInfoSignature: getName => " + fileInfoSignature.getName());
        System.out.println("FirmaSimpleFileInfoSignature: getFileToSign => " + fileInfoSignature.getFileToSign());
        System.out.println("FirmaSimpleFileInfoSignature: getLanguageSign => " + fileInfoSignature.getLanguageSign());
        System.out.println("FirmaSimpleFileInfoSignature: getLocation => " + fileInfoSignature.getLocation());
        // System.out.println("FirmaSimpleFileInfoSignature: getExpedientCodi => " + fileInfoSignature.getExpedientCodi());
        // System.out.println("FirmaSimpleFileInfoSignature: getExpedientNom => " + fileInfoSignature.getExpedientNom());
        // System.out.println("FirmaSimpleFileInfoSignature: getExpedientUrl => " + fileInfoSignature.getExpedientUrl());
        // System.out.println("FirmaSimpleFileInfoSignature: getProcedimentCodi => " + fileInfoSignature.getProcedimentCodi());
        // System.out.println("FirmaSimpleFileInfoSignature: getProcedimentNom => " + fileInfoSignature.getProcedimentNom());
        System.out.println("FirmaSimpleFileInfoSignature: getReason => " + fileInfoSignature.getReason());
        System.out.println("FirmaSimpleFileInfoSignature: getDocumentType => " + fileInfoSignature.getDocumentType());
        System.out.println("FirmaSimpleFileInfoSignature: getSignNumber => " + fileInfoSignature.getSignNumber());

        System.out.println("FI PREVIA SIGNATURA");

        // --------------------------------------------------------------

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
                    printSignatureInfo(fullResults);

                    return fullResults;

                } // Final for de fitxers firmats
            } // Final Case Firma OK
        } // Final Switch Firma

        return null;
    }


    public ApiFirmaSimpleTester() {
    }

    protected static Properties getConfigProperties() throws IOException, FileNotFoundException {
        Properties prop = new Properties();

        prop.load(ApiFirmaEnServidorSimple.class.getClassLoader().getResourceAsStream("apifirmaenservidorsimple.properties"));
        return prop;
    }

    public static ApiFirmaEnServidorSimple getApiFirmaEnServidorSimple(Properties prop)
            throws Exception {

        return new ApiFirmaEnServidorSimpleJersey(prop.getProperty("endpoint"),
                prop.getProperty("username"), prop.getProperty("password"));

    }

    protected void logErrorPerfilBuit(final String perfilProperty) {
        System.err
                .println("La propietat "
                        + perfilProperty
                        + " està buida. Això significa que si l'usuari aplicacio té més d'un perfil assignat, llavors llançarà un error.");
    }

    public static FirmaSimpleFile getSimpleFileFromResource(String fileName, String mime)
            throws Exception {

        InputStream is = FileUtils.readResource(ApiFirmaSimpleTester.class, "testfiles/"
                + fileName);
        File tmp = File.createTempFile("testFile", fileName);
        tmp.deleteOnExit();
        ByteArrayOutputStream fos = new ByteArrayOutputStream();

        FileUtils.copy(is, fos);

        FirmaSimpleFile asf = new FirmaSimpleFile(fileName, mime, fos.toByteArray());

        return asf;

    }

    public static void printSignatureInfo(FirmaSimpleSignatureResult fssr) {
        System.out.println(FirmaSimpleSignedFileInfo.toString(fssr.getSignedFileInfo()));
    }

}
