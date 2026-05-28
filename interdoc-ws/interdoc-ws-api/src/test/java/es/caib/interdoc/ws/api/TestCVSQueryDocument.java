package es.caib.interdoc.ws.api;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Base64;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;

public class TestCVSQueryDocument {

    private static final String ENDPOINT_NS = "http://impl.ws.interna.api.interdoc.caib.es/";
    private static final String QUERYDOCUMENT_NS = "urn:es:gob:aapp:csvbroker:webservices:querydocument:v1.0";
    private static final String WSSE_NS =
        "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    private static final String WSU_NS =
        "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
    private static final String PASSWORD_TEXT_TYPE =
        "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText";

    public static void main(String[] args) {
        try {

            Properties prop = new Properties();
            prop.load(new FileInputStream(new File("test.properties")));

            String url = prop.getProperty("urlCsvQueryDocument");
            String httpUsername = prop.getProperty("username");
            String httpPassword = prop.getProperty("password");
            String wsseUsername = prop.getProperty("usuariCsvQueryDocument");
            String wssePassword = prop.getProperty("passwordCsvQueryDocument");

            System.out.println("Creant servei CSVQueryDocumentWs amb URL: " + url);
            Dispatch<SOAPMessage> dispatch = getCSVQueryDocumentService(url, httpUsername, httpPassword);
            System.out.println("Servei CSVQueryDocumentWs creat correctament.");

            String csv = prop.getProperty("csvId");
            String idEni = prop.getProperty("idEni");
            String documentoEni = prop.getProperty("documentoEni");

            String nif = prop.getProperty("nif");
            String tipoIdentificacion = prop.getProperty("tipoIdentificacion");
            String ip = prop.getProperty("ip");

            String response = testCSVQueryDocumentSecurity(
            dispatch,
                    csv,
                    idEni,
                    documentoEni,
                    nif,
                    tipoIdentificacion,
                    ip,
                        wsseUsername,
                        wssePassword);
            System.out.println("csvQueryDocumentSecurity response: " + response);

        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    public static Dispatch<SOAPMessage> getCSVQueryDocumentService(String server, String username, String password)
            throws java.lang.Exception {

        final String endpoint = server;

        URL wsdlLocation = new URL(endpoint + "?wsdl");

        QName serviceName = new QName(ENDPOINT_NS, "CSVQueryDocumentWsService");
        QName portName = new QName(ENDPOINT_NS, "CSVQueryDocumentWs");
        Service service = Service.create(wsdlLocation, serviceName);
        Dispatch<SOAPMessage> dispatch = service.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);

        Map<String, Object> reqContext = ((BindingProvider) dispatch).getRequestContext();
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
        reqContext.put(BindingProvider.USERNAME_PROPERTY, username);
        reqContext.put(BindingProvider.PASSWORD_PROPERTY, password);

        if (username != null && password != null) {
            String basicAuth = Base64.getEncoder()
                    .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
            Map<String, List<String>> headers = new HashMap<String, List<String>>();
            headers.put("Authorization", Collections.singletonList("Basic " + basicAuth));
            reqContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        }

        reqContext.put("javax.xml.ws.client.connectionTimeout", 500000L);
        reqContext.put("javax.xml.ws.client.receiveTimeout", 500000L);

        return dispatch;
    }

    private static String testCSVQueryDocumentSecurity(Dispatch<SOAPMessage> dispatch, String csv,
            String idEni,
            String documentoEni, String nif, String tipoIdentificacion, String ip,
            String username, String password) throws java.lang.Exception {

        MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
        SOAPMessage request = messageFactory.createMessage();

        SOAPPart soapPart = request.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("v1", QUERYDOCUMENT_NS);

        addUsernameTokenHeader(envelope, username, password);

        SOAPBody body = envelope.getBody();
        SOAPBodyElement wrapper = body.addBodyElement(envelope.createName("csvQueryDocumentSecurityWSS", "v1", QUERYDOCUMENT_NS));
        SOAPElement requestElement = wrapper.addChildElement("queryDocumentSecurityRequest");

        addIfPresent(requestElement, "csv", csv);
        addIfPresent(requestElement, "idEni", idEni);
        addIfPresent(requestElement, "documento_eni", documentoEni);
        addIfPresent(requestElement, "nif", nif);
        addIfPresent(requestElement, "tipoIdentificacion", tipoIdentificacion);
        addIfPresent(requestElement, "ip", ip);

        request.saveChanges();
        SOAPMessage response = dispatch.invoke(request);

        return soapMessageToString(response);
    }

    private static void addIfPresent(SOAPElement parent, String fieldName, String value) throws java.lang.Exception {
        if (value != null && !value.trim().isEmpty()) {
            parent.addChildElement(fieldName).addTextNode(value);
        }
    }

    private static void addUsernameTokenHeader(SOAPEnvelope envelope, String username, String password)
            throws java.lang.Exception {
        if (username == null || username.trim().isEmpty() || password == null) {
            return;
        }

        SOAPHeader header = envelope.getHeader();
        if (header == null) {
            header = envelope.addHeader();
        }

        envelope.addNamespaceDeclaration("wsse", WSSE_NS);
        envelope.addNamespaceDeclaration("wsu", WSU_NS);

        SOAPHeaderElement security = header.addHeaderElement(envelope.createName("Security", "wsse", WSSE_NS));
        security.setMustUnderstand(true);

        SOAPElement usernameToken = security.addChildElement("UsernameToken", "wsse");
        usernameToken.addAttribute(envelope.createName("Id", "wsu", WSU_NS),
                "UsernameToken-" + System.currentTimeMillis());

        usernameToken.addChildElement("Username", "wsse").addTextNode(username);

        SOAPElement passwordElement = usernameToken.addChildElement("Password", "wsse");
        passwordElement.addAttribute(envelope.createName("Type"), PASSWORD_TEXT_TYPE);
        passwordElement.addTextNode(password);
    }

    private static String soapMessageToString(SOAPMessage message) throws java.lang.Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        message.writeTo(output);
        return output.toString(StandardCharsets.UTF_8.name());
    }

}
