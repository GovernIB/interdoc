
package es.caib.interdoc.ws.impl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import es.caib.interdoc.ws.model.CSVQueryDocumentRequest;
import es.caib.interdoc.ws.model.CSVQueryDocumentSecurityRequest;


@WebService(name = "CSVQueryDocumentMtomWs")
public interface CSVQueryDocumentMtomWs {
    
    @WebMethod
    public String csvQueryDocument(
    	@WebParam(name="csvQueryDocumentRequest")
    	CSVQueryDocumentRequest csvQueryDocumentRequest
    		) throws Exception;
    
    @WebMethod
    public String csvQueryDocumentSecurity(
    	@WebParam(name="csvQueryDocumentSecurityRequest")
    	CSVQueryDocumentSecurityRequest csvQueryDocumentSecurityRequest
    		) throws Exception;

}
