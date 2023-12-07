package es.caib.interdoc.ws.api;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.2.5.redhat-00001
 * 2023-03-28T15:30:45.259+02:00
 * Generated source version: 3.2.5.redhat-00001
 *
 */
@WebService(targetNamespace = "http://impl.ws.interna.api.interdoc.caib.es/", name = "ObtenerReferenciaWs")
@XmlSeeAlso({ObjectFactory.class})
public interface ObtenerReferenciaWs {

    @WebMethod
    @RequestWrapper(localName = "creaReferencia", targetNamespace = "http://impl.ws.interna.api.interdoc.caib.es/", className = "es.caib.interdoc.ws.api.CreaReferencia")
    @ResponseWrapper(localName = "creaReferenciaResponse", targetNamespace = "http://impl.ws.interna.api.interdoc.caib.es/", className = "es.caib.interdoc.ws.api.CreaReferenciaResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.lang.String creaReferencia(
        @WebParam(name = "obtenerReferenciaRequest", targetNamespace = "")
        es.caib.interdoc.ws.api.ObtenerReferenciaRequestInfo obtenerReferenciaRequest
    ) throws Exception_Exception;

    @WebMethod
    @RequestWrapper(localName = "echo", targetNamespace = "http://impl.ws.interna.api.interdoc.caib.es/", className = "es.caib.interdoc.ws.api.Echo")
    @ResponseWrapper(localName = "echoResponse", targetNamespace = "http://impl.ws.interna.api.interdoc.caib.es/", className = "es.caib.interdoc.ws.api.EchoResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.lang.String echo(
        @WebParam(name = "echo", targetNamespace = "")
        java.lang.String echo
    );

    @WebMethod
    @RequestWrapper(localName = "getVersionWs", targetNamespace = "http://impl.ws.interna.api.interdoc.caib.es/", className = "es.caib.interdoc.ws.api.GetVersionWs")
    @ResponseWrapper(localName = "getVersionWsResponse", targetNamespace = "http://impl.ws.interna.api.interdoc.caib.es/", className = "es.caib.interdoc.ws.api.GetVersionWsResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.lang.String getVersionWs();
}
