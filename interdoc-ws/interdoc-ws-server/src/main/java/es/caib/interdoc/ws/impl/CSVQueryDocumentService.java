
package es.caib.interdoc.ws.impl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

import es.caib.interdoc.ws.model.CSVQueryDocumentRequest;
import es.caib.interdoc.ws.model.CSVQueryDocumentResponse;
import es.caib.interdoc.ws.model.CSVQueryDocumentSecurityRequest;
import es.caib.interdoc.ws.model.CSVQueryDocumentSecurityResponse;
import es.caib.interdoc.ws.model.WSCredential;

@WebService(name = "CSVQueryDocumentMtomWs")
public interface CSVQueryDocumentService {

	@WebMethod
	public CSVQueryDocumentResponse csvQueryDocument(
			@WebParam(name = "credential") @XmlElement(required = true, name = "credential") WSCredential wsCredential,
			@WebParam(name = "queryDocumentRequest") @XmlElement(required = true, name = "queryDocumentRequest") CSVQueryDocumentRequest csvQueryDocumentRequest)
			throws Exception;

	@WebMethod
	public CSVQueryDocumentSecurityResponse csvQueryDocumentSecurity(
			@WebParam(name = "credential") @XmlElement(required = true, name = "credential") WSCredential wsCredential,
			@WebParam(name = "queryDocumentSecurityRequest") @XmlElement(required = true, name = "queryDocumentSecurityRequest") CSVQueryDocumentSecurityRequest csvQueryDocumentSecurityRequest)
			throws Exception;

}
