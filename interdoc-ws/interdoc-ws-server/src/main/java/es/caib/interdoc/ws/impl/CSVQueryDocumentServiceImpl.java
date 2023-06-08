package es.caib.interdoc.ws.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.service.facade.ReferenciaServiceFacade;
import es.caib.interdoc.ws.model.CSVQueryDocumentRequest;
import es.caib.interdoc.ws.model.CSVQueryDocumentSecurityRequest;

/**
 * @author jagarcia
 */

@Stateless(name = CSVQueryDocumentServiceImpl.NAME + "Ejb")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, parameterStyle = SOAPBinding.ParameterStyle.BARE, use = SOAPBinding.Use.LITERAL)
@org.apache.cxf.interceptor.InInterceptors(interceptors = { "es.caib.interdoc.ws.utilitats.WebServiceAuthenticationInterceptor"}) 
@org.apache.cxf.interceptor.InFaultInterceptors(interceptors = { "es.caib.interdoc.ws.utilitats.WsOutInterceptor" })
@WebService(name = CSVQueryDocumentServiceImpl.NAME_WS, portName = CSVQueryDocumentServiceImpl.NAME_WS, serviceName = CSVQueryDocumentServiceImpl.NAME_WS
		+ "Service")
public class CSVQueryDocumentServiceImpl implements CSVQueryDocumentMtomWs {

	private static final Logger log = LoggerFactory.getLogger(CSVQueryDocumentServiceImpl.class);

	public static final String NAME = "CSVQueryDocumentService";

	public static final String NAME_WS = NAME + "Ws";
	
	@EJB
	protected ReferenciaServiceFacade referenciaService;

	@Override
	public String csvQueryDocument(CSVQueryDocumentRequest csvQueryDocumentRequest) throws Exception {
		log.info("csvQueryDocument INICI");
		
		
		
		return null;
	}

	@Override
	public String csvQueryDocumentSecurity(CSVQueryDocumentSecurityRequest csvQueryDocumentSecurityRequest)
			throws Exception {
		log.info("csvQueryDocumentSecurity INICI");
		return null;
	}
	
	
	
	
	
}
