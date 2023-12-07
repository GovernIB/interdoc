package es.caib.interdoc.api.interna.ws.impl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.soap.MTOM;

import es.caib.interdoc.api.interna.ws.model.CSVQueryDocumentResponse;
import es.caib.interdoc.api.interna.ws.model.CSVQueryDocumentSecurityWSS;
import es.caib.interdoc.api.interna.ws.resposta.mtom.CSVQueryDocumentMtomSecurityResponse;

@WebService(name = "CSVQueryDocumentWs", targetNamespace = "urn:es:gob:aapp:csvbroker:webservices:querydocument:v1.0")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@MTOM(enabled = true, threshold = 2048)
public interface CSVQueryDocumentService {

	@WebResult(name = "csvQueryDocumentResponse", targetNamespace = "urn:es:gob:aapp:csvbroker:webservices:querydocument:v1.0", partName = "parameters")
	@WebMethod
	public CSVQueryDocumentResponse csvQueryDocument(
			@WebParam(name = "csvQueryDocumentSecurityWSS") @XmlElement(required = true, name = "csvQueryDocumentSecurityWSS") CSVQueryDocumentSecurityWSS csvQueryDocumentSecurityWSS)
			throws Exception;

	@WebResult(name = "CSVQueryDocumentMtomSecurityResponse", targetNamespace = "urn:es:gob:aapp:csvbroker:webservices:querydocument:model:v1.0", partName = "parameters")
	@WebMethod
	public CSVQueryDocumentMtomSecurityResponse csvQueryDocumentSecurity(
			@WebParam(name = "csvQueryDocumentSecurityWSS") @XmlElement(required = true, name = "csvQueryDocumentSecurityWSS") CSVQueryDocumentSecurityWSS csvQueryDocumentSecurityWSS)
			throws Exception;

}
