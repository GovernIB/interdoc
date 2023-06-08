
package es.caib.interdoc.ws.impl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import es.caib.interdoc.ws.model.ObtenerReferenciaRequestInfo;
import es.caib.interdoc.ws.utils.Fitxer;


@WebService(name = "ObtenerReferenciaWs")
public interface ObtenerReferenciaWs {


    /**
     * 
     * @param echo
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @RequestWrapper(localName = "echo", className = "es.caib.interdoc.ws.api.Echo")
    @ResponseWrapper(localName = "echoResponse", className = "es.caib.interdoc.ws.api.EchoResponse")
    public String echo(
        @WebParam(name = "echo")
        String echo);

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @RequestWrapper(localName = "getVersionWs", className = "es.caib.interdoc.ws.api.GetVersionWs")
    @ResponseWrapper(localName = "getVersionWsResponse", className = "es.caib.interdoc.ws.api.GetVersionWsResponse")
    public String getVersionWs();

    /**
     * 
     * @param obtenerReferenciaRequest
     * @return
     *     returns java.lang.String
     * @throws Exception
     * 
     */
    @WebMethod
    public String creaReferencia(
        @WebParam(name = "obtenerReferenciaRequest")
        ObtenerReferenciaRequestInfo obtenerReferenciaRequest)
        throws Exception;

}
