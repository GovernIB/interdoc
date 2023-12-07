package es.caib.interdoc.api.interna.ws.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import javax.activation.DataHandler;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.mail.util.ByteArrayDataSource;

import org.jboss.ws.api.annotation.TransportGuarantee;
import org.jboss.ws.api.annotation.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.plugins.arxiu.ArxiuController;
import es.caib.interdoc.service.facade.AccesServiceFacade;
import es.caib.interdoc.service.facade.InfoArxiuServiceFacade;
import es.caib.interdoc.service.facade.ReferenciaServiceFacade;
import es.caib.interdoc.service.model.InfoArxiuDTO;
import es.caib.interdoc.service.model.ReferenciaDTO;
import es.caib.interdoc.api.interna.ws.model.CSVQueryDocumentResponse;
import es.caib.interdoc.api.interna.ws.model.CSVQueryDocumentSecurityResponse;
import es.caib.interdoc.api.interna.ws.model.CSVQueryDocumentSecurityWSS;
import es.caib.interdoc.api.interna.ws.model.UeryDocumentSecurityRequest;
import es.caib.interdoc.api.interna.ws.resposta.mtom.CSVQueryDocumentMtomSecurityResponse;
import es.caib.interdoc.api.interna.ws.resposta.mtom.ContenidoMtomInfo;
import es.caib.interdoc.api.interna.ws.resposta.mtom.DocumentoMtomResponse;
import es.caib.interdoc.api.interna.ws.utils.InputStreamDataSource;

/**
 * 
 * Servei web per descarregar documents
 * @author jagarcia
 */

@Stateless(name = CSVQueryDocumentServiceImpl.NAME + "Ejb")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, parameterStyle = SOAPBinding.ParameterStyle.BARE, use = SOAPBinding.Use.LITERAL)
@org.apache.cxf.interceptor.InInterceptors(interceptors = { "es.caib.interdoc.api.interna.ws.utilitats.WSSecurityInterceptor" })
@org.apache.cxf.interceptor.InFaultInterceptors(interceptors = { "es.caib.interdoc.api.interna.ws.utilitats.WsOutInterceptor" })
@WebService(name = CSVQueryDocumentServiceImpl.NAME_WS, portName = CSVQueryDocumentServiceImpl.NAME_WS, serviceName = CSVQueryDocumentServiceImpl.NAME_WS
		+ "Service")
@WebContext(urlPattern = "/protected/" + CSVQueryDocumentServiceImpl.NAME_WS, transportGuarantee = TransportGuarantee.NONE, 
secureWSDLAccess = false)  
public class CSVQueryDocumentServiceImpl implements CSVQueryDocumentService {

	private static final Logger LOG = LoggerFactory.getLogger(CSVQueryDocumentServiceImpl.class);

	public static final String NAME = "CSVQueryDocument";

	public static final String NAME_WS = NAME + "Ws";

	@EJB(mappedName = ReferenciaServiceFacade.JNDI_NAME)
	protected ReferenciaServiceFacade referenciaService;

	@EJB(mappedName = InfoArxiuServiceFacade.JNDI_NAME)
	protected InfoArxiuServiceFacade infoArxiuService;

	@EJB(mappedName = AccesServiceFacade.JNDI_NAME)
	protected AccesServiceFacade accessService;

	@Override
	public CSVQueryDocumentResponse csvQueryDocument(
			@WebParam(name = "csvQueryDocumentSecurityWSS") CSVQueryDocumentSecurityWSS csvQueryDocumentSecurityWSS)
			throws Exception {

		return generateCSVQueryDocumentErrorResponse("200", "Utilitzau el servei securitzat csvQueryDocumentSecurity.");
	}


	@Override
	public CSVQueryDocumentMtomSecurityResponse csvQueryDocumentSecurity(
			@WebParam(name = "csvQueryDocumentSecurityWSS") CSVQueryDocumentSecurityWSS csvQueryDocumentSecurityWSS)
			throws Exception {

		UeryDocumentSecurityRequest ueriRequest = csvQueryDocumentSecurityWSS.getQueryDocumentSecurityRequest();

		if (Configuracio.isDesenvolupament()) {
			LOG.info("------ Parametres entrada csvQueryDocumentSecurity -------");
			LOG.info("csv: " + ((ueriRequest.getCsv() != null) ? ueriRequest.getCsv() : "null"));
			LOG.info("idEni: " + ((ueriRequest.getIdEni() != null) ? ueriRequest.getIdEni() : "null"));
			LOG.info("IP: " + ((ueriRequest.getIp() != null) ? ueriRequest.getIp() : "null"));
			LOG.info("DocumentoEni: "
					+ ((ueriRequest.getDocumentoEni() != null) ? ueriRequest.getDocumentoEni().value() : "null"));
			LOG.info("NIF: " + ((ueriRequest.getNif() != null) ? ueriRequest.getNif() : "null"));
			LOG.info("tipoIdentificacion: "
					+ ((ueriRequest.getTipoIdentificacion() != null) ? ueriRequest.getTipoIdentificacion().value()
							: "null"));
			LOG.info("----------------------------------");

		}

		// VALIDACIONES
		final String csvId = Utils.isNotEmpty(ueriRequest.getCsv()) ? ueriRequest.getCsv() : "";
		final String idEni = Utils.isNotEmpty(ueriRequest.getIdEni()) ? ueriRequest.getIdEni() : "";
		final Boolean isDescarregaPDF = (ueriRequest.getDocumentoEni() != null
				&& "S".equals(ueriRequest.getDocumentoEni().value())) ? true : false;

		if (Utils.isEmpty(csvId) && Utils.isEmpty(idEni)) {
			return generateCSVQueryDocumentSecurityMtomErrorResponse("400", "Es necessari introduir un CSV o un UUID.");
		}

		if (Configuracio.isCsvQueryDocumentServiceTest()) {
			return respostaEnidocMadrid();
		}
		
		// Recuperam la referencia
		Optional<ReferenciaDTO> referenciaDto = Utils.isNotEmpty(idEni) ? referenciaService.findByUUID(idEni)
				: referenciaService.findByCSV(csvId);

		// Recuperam infoArxiu
		if (referenciaDto == null || referenciaDto.isEmpty()) {
			return generateCSVQueryDocumentSecurityMtomErrorResponse("200",
					"No existeix cap referencia amb les dades indicades");
		}

		ReferenciaDTO ref = referenciaDto.get();
		
		if (Configuracio.isDesenvolupament()) {
			LOG.info("ref.id =>" + ref.getId());
			LOG.info("ref.Receptor =>" + ref.getReceptor());
			LOG.info("ref.Emisor =>" + ref.getEmisor());
			LOG.info("ref.CsvId =>" + ref.getCsvId());
			LOG.info("ref.UUid =>" + ref.getUuId());
			LOG.info("ref.fitxerId =>" + ref.getFitxerId());
			LOG.info("ref.infoSignaturaId =>" + ref.getInfoSignaturaId());
			LOG.info("ref.infoArxiuId => " + ref.getInfoArxiuId());
		}

		String resultatArxiu = null;

		if (ref.getInfoArxiuId() != null && ref.getInfoArxiuId() > 0) {

			Optional<InfoArxiuDTO> arxiu = infoArxiuService.findById(ref.getInfoArxiuId());

			InfoArxiuDTO infoArxiu = null;

			if (arxiu.isPresent()) {
				infoArxiu = arxiu.get();

				if (Configuracio.isDesenvolupament()) {
					LOG.info("infoArxiu.getArxiuDocumentID => " + infoArxiu.getArxiuDocumentID());
					LOG.info("infoArxiu.getEniFileUrl => " + infoArxiu.getEniFileUrl());
					LOG.info("infoArxiu.getOriginalFileUrl => " + infoArxiu.getOriginalFileUrl());
				}

				Long entitatId = 1L;
				ArxiuController pluginArxiu = new ArxiuController(entitatId);

				// Generam el ENIDOC
				resultatArxiu = pluginArxiu.getPlugin().generarEniDoc(infoArxiu.getArxiuDocumentID());

				if (Configuracio.isDesenvolupament())
					LOG.info(resultatArxiu);

				// Si document_eni => RETORNAM EL PDF
				if (isDescarregaPDF && infoArxiu.getOriginalFileUrl() != null) {
					LOG.info("isDescarregaPDF => true");
					byte[] filePDF = downloadUrl(new URL(infoArxiu.getOriginalFileUrl()));
					LOG.info("byteArray length => " + filePDF.length);
				}

			} else {
				return generateCSVQueryDocumentSecurityMtomErrorResponse("200",
						"No existeix cap referencia amb les dades indicades a l'arxiu");
			}

		}

		// Montam la resposta
		CSVQueryDocumentMtomSecurityResponse response = new CSVQueryDocumentMtomSecurityResponse();

		response.setCode("200");
		response.setDescription("Operación con éxito");

		DocumentoMtomResponse documentoMtomResponse = new DocumentoMtomResponse();

		ContenidoMtomInfo contenido = new ContenidoMtomInfo();

		DataHandler result = null;
		if (resultatArxiu != null) {
			ByteArrayDataSource barrds = new ByteArrayDataSource(resultatArxiu.getBytes(StandardCharsets.UTF_8),
					"application/octet-stream");
			result = new DataHandler(barrds);
			LOG.info("Datahandler inicializado");
		}
		contenido.setContenido(result);
		contenido.setTipoMIME("text/xml");

		documentoMtomResponse.setContenido(contenido);

		response.setDocumentoMtomResponse(documentoMtomResponse);

		return response;
	}

	private CSVQueryDocumentResponse generateCSVQueryDocumentErrorResponse(String codigo, String descripcion)
			throws Exception {

		CSVQueryDocumentResponse response = new CSVQueryDocumentResponse();
		response.setCode(codigo);
		response.setDescription(descripcion);
		response.setDocumentResponse(null);
		return response;

	}

	private CSVQueryDocumentSecurityResponse generateCSVQueryDocumentSecurityErrorResponse(String codigo,
			String descripcion) throws Exception {

		CSVQueryDocumentSecurityResponse response = new CSVQueryDocumentSecurityResponse();
		response.setCode(codigo);
		response.setDescription(descripcion);
		response.setDocumentUrlResponse(null);
		return response;

	}

	private CSVQueryDocumentMtomSecurityResponse generateCSVQueryDocumentSecurityMtomErrorResponse(String codigo,
			String descripcion) throws Exception {

		CSVQueryDocumentMtomSecurityResponse response = new CSVQueryDocumentMtomSecurityResponse();
		response.setCode(codigo);
		response.setDescription(descripcion);
		response.setDocumentoMtomResponse(null);
		return response;

	}

	private byte[] downloadUrl(URL toDownload) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			byte[] chunk = new byte[4096];
			int bytesRead;
			InputStream stream = toDownload.openStream();

			while ((bytesRead = stream.read(chunk)) > 0) {
				outputStream.write(chunk, 0, bytesRead);
			}

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return outputStream.toByteArray();
	}
	
	private CSVQueryDocumentMtomSecurityResponse respostaTest() {
		// Retornam un fitxer de test des d'arxiu
		Long entitatId = 1L;
		String resultatArxiu = null;
		
		try {
			String expedienteId = "092e59a9-063f-45bf-bb27-d21abc37cb63";
			String documentoId = "9ae1da2c-64e5-4a26-910f-cd514b1fd3c4";

			ArxiuController arxiu = new ArxiuController(entitatId);
			resultatArxiu = arxiu.getPlugin().generarEniDoc(documentoId);
			LOG.info("Resultat arxiu => " + resultatArxiu);
		} catch (Exception e) {
			LOG.error("Error inicialització arxiu => " + e.getMessage());
			e.printStackTrace();
		}

		// MONTAM LA RESPOSTA
		CSVQueryDocumentMtomSecurityResponse response = new CSVQueryDocumentMtomSecurityResponse();

		response.setCode("0");
		response.setDescription("Operación con éxito");

		DocumentoMtomResponse documentoMtomResponse = new DocumentoMtomResponse();

		ContenidoMtomInfo contenido = new ContenidoMtomInfo();

		DataHandler result = null;
		if (resultatArxiu != null) {
			ByteArrayDataSource barrds = new ByteArrayDataSource(resultatArxiu.getBytes(StandardCharsets.UTF_8),
					"application/octet-stream");
			result = new DataHandler(barrds);
			LOG.info("Datahandler inicializado");
		}
		contenido.setContenido(result);
		contenido.setTipoMIME("text/xml");

		documentoMtomResponse.setContenido(contenido);

		response.setDocumentoMtomResponse(documentoMtomResponse);

		return response;
	}
	
	private CSVQueryDocumentMtomSecurityResponse respostaEnidocMadrid() {
		
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("/test_enidoc_madrid.xml");
		InputStreamDataSource isds = new InputStreamDataSource(in);

		// MONTAM LA RESPOSTA
		CSVQueryDocumentMtomSecurityResponse response = new CSVQueryDocumentMtomSecurityResponse();

		response.setCode("0");
		response.setDescription("Operación con éxito");

		DocumentoMtomResponse documentoMtomResponse = new DocumentoMtomResponse();
		ContenidoMtomInfo contenido = new ContenidoMtomInfo();

		DataHandler result = new DataHandler(isds);
		LOG.info("Datahandler test_enidoc_madrid inicializado");
		
		contenido.setContenido(result);
		contenido.setTipoMIME("text/xml");

		documentoMtomResponse.setContenido(contenido);

		response.setDocumentoMtomResponse(documentoMtomResponse);

		return response;
		
	}

}

