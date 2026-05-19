package es.caib.interdoc.ws.api;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import javax.xml.ws.BindingProvider;

public class TestCVSQueryDocument {

    public static void main(String[] args) {
        try {
            
            Properties prop = new Properties();
            prop.load(new FileInputStream(new File("test.properties")));
            
            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            
            CSVQueryDocumentMtomWs csvQueyDocumentWs = getCSVQueryDocumentService(url, username, password);
            
            
            /*String response = testGenerarEniDoc(referenciaWs, documentoInteresado,
                    receptor, numeroRegistroFormateado, fechaRegistro, tipoRegistro, MODO_FIRMA_ANEXO_SINFIRMA,
                    fitxerAnexo);*/
            

        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }}

    
    /*public static String testGenerarEniDoc() {
        
    }*/
        
        public static CSVQueryDocumentMtomWs getCSVQueryDocumentService(String server, String username, String password)
                throws java.lang.Exception {

            final String endpoint = server;

            URL wsdlLocation = null;
            wsdlLocation = new URL(endpoint + "?wsdl");

            CSVQueryDocumentMtomWsService service = new CSVQueryDocumentMtomWsService(wsdlLocation);

            CSVQueryDocumentMtomWs api = service.getCSVQueryDocumentMtomWs();

            Map<String, Object> reqContext = ((BindingProvider) api).getRequestContext();
            reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
            reqContext.put(BindingProvider.USERNAME_PROPERTY, username);
            reqContext.put(BindingProvider.PASSWORD_PROPERTY, password);

            reqContext.put("javax.xml.ws.client.connectionTimeout", 500000L);
            reqContext.put("javax.xml.ws.client.receiveTimeout", 500000L);

            return api;
        }
    
}
