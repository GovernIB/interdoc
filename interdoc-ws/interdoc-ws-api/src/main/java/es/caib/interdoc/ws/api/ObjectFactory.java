
package es.caib.interdoc.ws.api;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.caib.interdoc.ws.api package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CsvQueryDocument_QNAME = new QName("http://impl.ws.interna.api.interdoc.caib.es/", "csvQueryDocument");
    private final static QName _CsvQueryDocumentResponse_QNAME = new QName("http://impl.ws.interna.api.interdoc.caib.es/", "csvQueryDocumentResponse");
    private final static QName _CsvQueryDocumentSecurity_QNAME = new QName("http://impl.ws.interna.api.interdoc.caib.es/", "csvQueryDocumentSecurity");
    private final static QName _CsvQueryDocumentSecurityResponse_QNAME = new QName("http://impl.ws.interna.api.interdoc.caib.es/", "csvQueryDocumentSecurityResponse");
    private final static QName _Exception_QNAME = new QName("http://impl.ws.interna.api.interdoc.caib.es/", "Exception");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.caib.interdoc.ws.api
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CsvQueryDocument }
     * 
     */
    public CsvQueryDocument createCsvQueryDocument() {
        return new CsvQueryDocument();
    }

    /**
     * Create an instance of {@link CsvQueryDocumentResponse }
     * 
     */
    public CsvQueryDocumentResponse createCsvQueryDocumentResponse() {
        return new CsvQueryDocumentResponse();
    }

    /**
     * Create an instance of {@link CsvQueryDocumentSecurity }
     * 
     */
    public CsvQueryDocumentSecurity createCsvQueryDocumentSecurity() {
        return new CsvQueryDocumentSecurity();
    }

    /**
     * Create an instance of {@link CsvQueryDocumentSecurityResponse }
     * 
     */
    public CsvQueryDocumentSecurityResponse createCsvQueryDocumentSecurityResponse() {
        return new CsvQueryDocumentSecurityResponse();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link CSVQueryDocumentRequest }
     * 
     */
    public CSVQueryDocumentRequest createCSVQueryDocumentRequest() {
        return new CSVQueryDocumentRequest();
    }

    /**
     * Create an instance of {@link CSVQueryDocumentSecurityRequest }
     * 
     */
    public CSVQueryDocumentSecurityRequest createCSVQueryDocumentSecurityRequest() {
        return new CSVQueryDocumentSecurityRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CsvQueryDocument }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CsvQueryDocument }{@code >}
     */
    @XmlElementDecl(namespace = "http://impl.ws.interna.api.interdoc.caib.es/", name = "csvQueryDocument")
    public JAXBElement<CsvQueryDocument> createCsvQueryDocument(CsvQueryDocument value) {
        return new JAXBElement<CsvQueryDocument>(_CsvQueryDocument_QNAME, CsvQueryDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CsvQueryDocumentResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CsvQueryDocumentResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://impl.ws.interna.api.interdoc.caib.es/", name = "csvQueryDocumentResponse")
    public JAXBElement<CsvQueryDocumentResponse> createCsvQueryDocumentResponse(CsvQueryDocumentResponse value) {
        return new JAXBElement<CsvQueryDocumentResponse>(_CsvQueryDocumentResponse_QNAME, CsvQueryDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CsvQueryDocumentSecurity }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CsvQueryDocumentSecurity }{@code >}
     */
    @XmlElementDecl(namespace = "http://impl.ws.interna.api.interdoc.caib.es/", name = "csvQueryDocumentSecurity")
    public JAXBElement<CsvQueryDocumentSecurity> createCsvQueryDocumentSecurity(CsvQueryDocumentSecurity value) {
        return new JAXBElement<CsvQueryDocumentSecurity>(_CsvQueryDocumentSecurity_QNAME, CsvQueryDocumentSecurity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CsvQueryDocumentSecurityResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CsvQueryDocumentSecurityResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://impl.ws.interna.api.interdoc.caib.es/", name = "csvQueryDocumentSecurityResponse")
    public JAXBElement<CsvQueryDocumentSecurityResponse> createCsvQueryDocumentSecurityResponse(CsvQueryDocumentSecurityResponse value) {
        return new JAXBElement<CsvQueryDocumentSecurityResponse>(_CsvQueryDocumentSecurityResponse_QNAME, CsvQueryDocumentSecurityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}
     */
    @XmlElementDecl(namespace = "http://impl.ws.interna.api.interdoc.caib.es/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

}
