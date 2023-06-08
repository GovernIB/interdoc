package es.caib.interdoc.ws.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.JAXBException;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.MTOM;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.service.facade.AccesServiceFacade;
import es.caib.interdoc.service.facade.ReferenciaServiceFacade;
import es.caib.interdoc.service.model.AccesDTO;
import es.caib.interdoc.service.model.ReferenciaDTO;
import es.caib.interdoc.commons.utils.MarshallUtil;
import es.caib.interdoc.ws.model.CSVQueryDocumentRequest;
import es.caib.interdoc.ws.model.CSVQueryDocumentSecurityRequest;
import es.caib.interdoc.ws.resposta.mtom.CSVQueryDocumentMtomSecurityResponse;
import es.caib.interdoc.ws.resposta.mtom.ContenidoMtomInfo;
import es.caib.interdoc.ws.resposta.mtom.DocumentoMtomResponse;

/**
 * @author jagarcia
 */

@Stateless(name = CSVQueryDocumentMtomWsImpl.NAME + "Ejb")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@org.apache.cxf.interceptor.InInterceptors(interceptors = { "es.caib.interdoc.ws.utilitats.WsInInterceptor"}) 
// "es.caib.interdoc.ws.utilitats.BasicAuthAuthorizationInterceptor" 
@org.apache.cxf.interceptor.InFaultInterceptors(interceptors = { "es.caib.interdoc.ws.utilitats.WsOutInterceptor" })
@MTOM(enabled = true, threshold = 2048)
@WebService(name = CSVQueryDocumentMtomWsImpl.NAME_WS, portName = CSVQueryDocumentMtomWsImpl.NAME_WS, serviceName = CSVQueryDocumentMtomWsImpl.NAME_WS
		+ "Service")
public class CSVQueryDocumentMtomWsImpl implements CSVQueryDocumentMtomWs {

	private static final Logger log = LoggerFactory.getLogger(CSVQueryDocumentMtomWsImpl.class);

	public static final String NAME = "CSVQueryDocumentMtom";

	public static final String NAME_WS = NAME + "Ws";
	
	@EJB
	protected ReferenciaServiceFacade referenciaService;
	
	@EJB
	protected AccesServiceFacade accessService;
	
	@Resource
	WebServiceContext wsctx;
	
	@WebMethod
	@Override
	public String csvQueryDocument(CSVQueryDocumentRequest csvQueryDocumentRequest) throws Exception {

		log.info("CSVQueryDocumentMtomWsImpl:csvQueryDocument INICI");

		return null;
	}

	@WebMethod
	@Override
	public String csvQueryDocumentSecurity(CSVQueryDocumentSecurityRequest csvQueryDocumentSecurityRequest)
			throws Exception {

		log.info("CSVQueryDocumentMtomWsImpl:csvQueryDocumentSecurity INICI");

		log.info("CSV => "
				+ (Utils.isNotEmpty(csvQueryDocumentSecurityRequest.getCsv()) ? csvQueryDocumentSecurityRequest.getCsv()
						: ""));
		log.info("DIR3 =>" + (Utils.isNotEmpty(csvQueryDocumentSecurityRequest.getDir3())
				? csvQueryDocumentSecurityRequest.getDir3()
				: ""));
		log.info("IDENI =>" + (Utils.isNotEmpty(csvQueryDocumentSecurityRequest.getIdEni())
				? csvQueryDocumentSecurityRequest.getIdEni()
				: ""));
		log.info("IP =>"
				+ (Utils.isNotEmpty(csvQueryDocumentSecurityRequest.getIp()) ? csvQueryDocumentSecurityRequest.getIp()
						: ""));
		log.info("NIF =>"
				+ (Utils.isNotEmpty(csvQueryDocumentSecurityRequest.getNif()) ? csvQueryDocumentSecurityRequest.getNif()
						: ""));
		log.info("ES DOCENI =>" + ((csvQueryDocumentSecurityRequest.getDocumentoEni() != null)
				? csvQueryDocumentSecurityRequest.getDocumentoEni().value()
				: ""));
		log.info("REC ORIGINAL =>" + ((csvQueryDocumentSecurityRequest.getRecuperacionOriginal() != null)
				? csvQueryDocumentSecurityRequest.getRecuperacionOriginal().value()
				: ""));
		log.info("TIPO ID =>" + ((csvQueryDocumentSecurityRequest.getTipoIdentificacion() != null)
				? csvQueryDocumentSecurityRequest.getTipoIdentificacion().value()
				: ""));

		// REVISAR AUTENTICACION

		doAuthentication();

		// RECUPERAR DOCUMENTO
		boolean fileExist = true;
		
		final String csvId = Utils.isNotEmpty(csvQueryDocumentSecurityRequest.getCsv()) ? csvQueryDocumentSecurityRequest.getCsv() : "";
		final String idEni = Utils.isNotEmpty(csvQueryDocumentSecurityRequest.getIdEni()) ? csvQueryDocumentSecurityRequest.getIdEni() : "";
		
		log.info((csvId != "") ? "CSVID => " + csvId : "CSVID IS NULL");
		log.info((idEni != "") ? "IDENI => " + idEni : "IDENI IS NULL");
		
		if (Utils.isEmpty(csvId) || Utils.isEmpty(idEni)) {
			return generateXmlErrorResponse("400", "Bad request");
		}
		
		Optional<ReferenciaDTO> referenciaDto = Utils.isNotEmpty(csvId) ? referenciaService.findByCSV(csvId) : referenciaService.findByUUID(idEni);
		
		if (referenciaDto.isPresent()) {
			
			log.info("Recuperam info Referencia");
			ReferenciaDTO ref = referenciaDto.get();
			log.info("ref.id =>" + ref.getId());
			log.info("ref.Receptor =>" + ref.getReceptor());
			log.info("ref.Emisor =>" + ref.getEmisor());
			log.info("ref.CsvId =>" + ref.getCsvId());
			log.info("ref.UUid =>" + ref.getUuId());
			log.info("ref.expendientId =>" + ref.getExpedientId());
			log.info("ref.estatExpedient =>" + ref.getEstatExpedientId());
			
			// CONNECT ARXIU
			//return generateXMLResponse(referenciaDto.get().get, referenciaDto.get().get)
			
			// TODO GUARDAR ACCESO
			/*
			AccesDTO accesDto = new AccesDTO();
			accesDto.setId(null); 
			accesDto.setIdentificacio("IDAPLICACIO-DNI");
			accesDto.setIp("IP");
			accesDto.setReferenciaId(ref.getId());
			accesDto.setTipusIdentificacio("TIPUS");
			accessService.create(accesDto);
			*/
			
			Config config = ConfigProvider.getConfig();
			String filePath = config.getValue("es.caib.interdoc.filesdirectory", String.class) + "\test.pdf";
	        log.info("Sending file: " + filePath);  
	        
	        DataSource dataSource = new FileDataSource(new File(filePath));
	        
			return (fileExist) ? generateXMLResponse(dataSource, "application/pdf")
					: generateXmlErrorResponse("500", "Referencia no existe");
		}
		
		return generateXmlErrorResponse("500", "Referencia no existe");
		
	}

	private void doAuthentication() {

		log.info("Comprobació autenticació");
		
		MessageContext mctx = wsctx.getMessageContext();
		Map http_headers = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
		
		for (String objeto : mctx.keySet()) {
			
			if ("org.apache.cxf.configuration.security.AuthorizationPolicy".equals(objeto.toString())) {
				log.info("--------------- TROBAT ------------------------");		
				AuthorizationPolicy policy = (AuthorizationPolicy) mctx.get("org.apache.cxf.configuration.security.AuthorizationPolicy");
				if (policy != null) {
					log.info(policy.getAuthorization());
					log.info(policy.getUserName());
					log.info(policy.getPassword());
				}
			}
		}
		
		if (wsctx.getUserPrincipal() != null)
			log.info(wsctx.getUserPrincipal().getName());
		else
			log.info("(wsctx.getUserPrincipal() is NULL");
		

		ArrayList list = (ArrayList) http_headers.get("Authorization");
		if (list == null || list.size() == 0) {
			log.error("Authentication failed! This WS needs BASIC Authentication!");
			return;
			//throw new RuntimeException("Authentication failed! This WS needs BASIC Authentication!");
		}

		String userpass = (String) list.get(0);
		userpass = userpass.substring(5);
		byte[] buf = Base64.decodeBase64(userpass.getBytes());
		String credentials = new String(buf);

		String username = null;
		String password = null;
		int p = credentials.indexOf(":");
		if (p > -1) {
			username = credentials.substring(0, p);
			password = credentials.substring(p + 1);
		} else {
			//throw new RuntimeException("There was an error while decoding the Authentication!");
			log.error("There was an error while decoding the Authentication!");
			return;
		}

		// This should be changed to a DB / Ldap authentication check
		if (username.equals("interdoc") && password.equals("interdoc")) {
			log.info("============== Authentication OK =============");
			return;
		} else {
			//throw new RuntimeException("Authentication failed! Wrong username / password!");
			log.error("Authentication failed! Wrong username / password!");
			return;
		}

	}

	private String generateXmlErrorResponse(String codigo, String descripcion) throws Exception {
		try {

			CSVQueryDocumentMtomSecurityResponse respuesta = new CSVQueryDocumentMtomSecurityResponse();
			respuesta.setCode(codigo);
			respuesta.setDescription(descripcion);
			return MarshallUtil.generateXML(CSVQueryDocumentMtomSecurityResponse.class, respuesta);

		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String generateXMLResponse(DataSource fichero, String mime) throws Exception {

		CSVQueryDocumentMtomSecurityResponse respuesta = new CSVQueryDocumentMtomSecurityResponse();

		respuesta.setCode("0");
		respuesta.setDescription("La operación se ha realizado con éxito.");

		DocumentoMtomResponse documentoMtomResponse = new DocumentoMtomResponse();

		ContenidoMtomInfo contenido = new ContenidoMtomInfo();
		contenido.setContenido(new DataHandler(fichero));
		contenido.setTipoMIME(mime);

		documentoMtomResponse.setContenido(contenido);

		respuesta.setDocumentoMtomResponse(documentoMtomResponse);

		try {
			return MarshallUtil.generateXML(CSVQueryDocumentMtomSecurityResponse.class, respuesta);
		} catch (JAXBException e) {
			e.printStackTrace();
			return generateXmlErrorResponse("500", "Ha ocurrido un error.");
		}
	}

}

/*
 * 
 * @Override public CSVQueryDocumentMtomSecurityResponse
 * csvQueryDocumentSecurity( CsvQueryDocumentSecurityWSS
 * csvQueryDocumentSecurityWSS) throws CSVQueryDocumentException {
 * Auto-generated method stub return null; }
 *  
 * /* es.caib.plugins.arxiu.caib.documentExportarEni(id)
 * 
 */

// } 
