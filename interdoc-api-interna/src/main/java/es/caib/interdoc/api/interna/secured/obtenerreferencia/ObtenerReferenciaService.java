package es.caib.interdoc.api.interna.secured.obtenerreferencia;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import es.caib.interdoc.api.interna.secured.obtenerreferencia.request.Metadada;
import es.caib.interdoc.api.interna.secured.obtenerreferencia.request.ObtenerReferenciaRequest;
import es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta.EmisorBean;
import es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta.IdentificadorBean;
import es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta.MetadatoBean;
import es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta.MetadatosBean;
import es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta.PermisoBean;
import es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta.ReceptorBean;
import es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta.ReferenciaDocumentoBean;
import es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta.RestringidoBean;
import es.caib.interdoc.commons.utils.Base64;
import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.commons.utils.HashCreator;
import es.caib.interdoc.commons.utils.MarshallUtil;
import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.commons.utils.Version;
import es.caib.interdoc.plugins.arxiu.ArxiuController;
import es.caib.interdoc.plugins.arxiu.DocumentInfo;
import es.caib.interdoc.plugins.arxiu.Fitxer;
import es.caib.interdoc.plugins.arxiu.Origen;
import es.caib.interdoc.service.facade.ReferenciaServiceFacade;
import es.caib.interdoc.service.facade.ReferenciaXMLServiceFacade;
import es.caib.interdoc.service.model.ReferenciaDTO;
import es.caib.interdoc.service.model.ReferenciaXMLDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * @author jagarcia
 *
 */

@Path("/secured/referencia")
@OpenAPIDefinition(tags = @Tag(name = "Obtenir Referencia", description = "Pujar un document a Interdoc i obtenir una referencia única"))
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "BasicAuth", scheme = "basic")
public class ObtenerReferenciaService {

	protected Logger log = LoggerFactory.getLogger(ObtenerReferenciaService.class);

	final String URL_DESCARGA = "http://192.168.35.90:8280/interdoc-ws-server/CSVQueryDocumentMtomWsService/CSVQueryDocumentMtomWs?wsdl";

	@Inject
	private Version version;

	@EJB
	protected ReferenciaServiceFacade referenciaService;

	@EJB
	protected ReferenciaXMLServiceFacade referenciaXMLService;

	@Path("/versio")
	@GET
	@RolesAllowed({ Constants.ITD_WS })
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(tags = "Versió", operationId = "versio", summary = "Versio de l'Aplicació", method = "get")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Paràmetres incorrectes", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@ApiResponse(responseCode = "200", description = "Versió i idioma", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class))) })
	public Response versio(

			@Parameter(description = "Codi de l'idioma", required = false, example = "ca", schema = @Schema(implementation = String.class)) @Pattern(regexp = "^ca|es$") @QueryParam("idioma") String idioma) {

		try {
			return Response.ok().entity(version.getVersion() + "_" + idioma).build();

		} catch (Throwable th) {

			String msg = th.getMessage();
			log.error("Error cridada api rest 'versio': " + msg, th);
			return Response.status(Response.Status.BAD_REQUEST).entity("{ \"error\" : " + "\"" + msg + "\" }").build();

		}
	}

	@Path("/new")
	@POST
	@RolesAllowed({ Constants.ITD_WS })
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(tags = "Nova referencia", operationId = "nova", summary = "Crear una nova referencia", method = "post")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Paràmetres incorrectes", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@ApiResponse(responseCode = "200", description = "XML Referència Única", content = @Content(mediaType = MediaType.APPLICATION_XML, schema = @Schema(implementation = String.class))) })

	public Response obtenerReferencia(@RequestBody ObtenerReferenciaRequest obtenerReferenciaRequest) {

		log.info("obtenerReferencia.new INICI");

		log.info("---------------- DADES ENTRADA -------------------");
		log.info("csv: " + ((obtenerReferenciaRequest.getCsv() != null) ? obtenerReferenciaRequest.getCsv() : "null"));
		log.info("uuid: "
				+ ((obtenerReferenciaRequest.getUuid() != null) ? obtenerReferenciaRequest.getUuid() : "null"));
		log.info("document size: " + ((obtenerReferenciaRequest.getDocument() != null)
				? obtenerReferenciaRequest.getDocument().getData().length
				: "null"));
		log.info("metadades: "
				+ ((obtenerReferenciaRequest.getMetadades() != null) ? obtenerReferenciaRequest.getMetadades().size()
						: "null"));
		log.info("aplicacioId: "
				+ ((obtenerReferenciaRequest.getAplicacioId() != null) ? obtenerReferenciaRequest.getAplicacioId()
						: "null"));
		log.info("emisor: "
				+ ((obtenerReferenciaRequest.getEmisor() != null) ? obtenerReferenciaRequest.getEmisor() : "null"));
		log.info("receptor: "
				+ ((obtenerReferenciaRequest.getReceptor() != null) ? obtenerReferenciaRequest.getReceptor() : "null"));
		log.info("interessats: " + ((obtenerReferenciaRequest.getInteressats() != null)
				? obtenerReferenciaRequest.getInteressats().size()
				: "null"));
		log.info("isBase64: " + (Base64.isBase64(obtenerReferenciaRequest.getDocument().getData()) ? "true" : "false"));
		log.info("--------------------------------------------------");

		if (Utils.isEmpty(obtenerReferenciaRequest.getCsv()) && Utils.isEmpty(obtenerReferenciaRequest.getUuid())
				&& (obtenerReferenciaRequest.getDocument() != null
						&& obtenerReferenciaRequest.getDocument().getData().length < 1)) {
			return Response.ok().entity("Error: el camp CSV, UUID o fitxer binary és obligatori").build();
		}

		boolean isEnidoc = Utils.isNotEmpty(obtenerReferenciaRequest.getUuid());
		boolean isSigned = false;
		boolean isInArxiu = false;

		String estatExpedientId = "";
		String expedientId = "";

		log.info("isEnidoc:" + ((isEnidoc) ? "true" : "false"));

		if (!isEnidoc && obtenerReferenciaRequest.getDocument() != null) {
			// Si no és un fitxer ENIDOC, s'ha de firmar i pujar a l'arxiu

			/* INICI FIRMA EN SERVIDOR */
			isSigned = true;
			/* FI FIRMA EN SERVIDOR */

			if (isSigned) {
				/* INICI PUJADA ARXIU */

				log.info("Pujada a Arxiu");

				List<Metadada> metadadesList = obtenerReferenciaRequest.getMetadades();
				final int numMetadades = metadadesList.size();
				Map<String, Object> metadadesInfoObj = new HashMap<>(numMetadades);
				if (numMetadades > 0) {
					for (Metadada m : metadadesList) {
						log.info("metadada.clau => " + m.getClau() + " metadada.valor => " + m.getValor());
						metadadesInfoObj.put(m.getClau(), m.getValor());
					}
				}

				byte[] fitxerDescodificat = null;
				try {
					fitxerDescodificat = Base64.decode(obtenerReferenciaRequest.getDocument().getData(), 0,
							obtenerReferenciaRequest.getDocument().getData().length, false);
				} catch (IOException e) {
					e.printStackTrace();
				}

				Fitxer fitxerInfo = new Fitxer();
				if (fitxerDescodificat != null) {
					fitxerInfo.setArxiuNom("f_" + System.currentTimeMillis());
					fitxerInfo.setContingut(fitxerDescodificat);
					fitxerInfo.setTamany(fitxerDescodificat.length);
					// fitxerInfo.setTipusMime(fitxerTmp.getMime());
					fitxerInfo.setExtensio(null);
				}

				ArxiuController arxiu = new ArxiuController();

				DocumentInfo documentInfo = new DocumentInfo();
				documentInfo.setNom("prova_exp_" + System.currentTimeMillis());
				documentInfo.setOrgans(Arrays.asList(obtenerReferenciaRequest.getEmisor()));
				documentInfo.setInteressats(obtenerReferenciaRequest.getInteressats());
				documentInfo.setMetadades(metadadesInfoObj);
				documentInfo.setOrigen(Origen.ADMINISTRACIO);
				documentInfo.setFitxer(fitxerInfo);

				log.info(documentInfo.toString());

				// arxiu.crearExpedient(documentInfo);

				expedientId = "expedientId";
				estatExpedientId = "estatExpedientId";

				isInArxiu = true;

				/* FI PUJADA ARXIU */

			} else {
				log.info("Error: El fitxer   no está firmat i no es pot pujar a Arxiu");
			}

		}

		if (isEnidoc || (isSigned && isInArxiu)) {

			/* CREAM LA REFERENCIA */
			String referencia = createReferencia();
			log.info("Referencia generada: " + referencia);

			// Generate Hash
			String hash = null;
			if (obtenerReferenciaRequest.getDocument() != null) {

				byte[] fitxerDescodificat;
				try {
					fitxerDescodificat = Base64.decode(obtenerReferenciaRequest.getDocument().getData(), 0,
							obtenerReferenciaRequest.getDocument().getData().length, false);
					MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
					hash = getFileChecksum(shaDigest, fitxerDescodificat);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			/* GUARDAM LA INFORMACIÓ DE LA REFERENCIA */
			ReferenciaDTO nuevaReferenciaDto = new ReferenciaDTO();
			nuevaReferenciaDto.setCsvId(obtenerReferenciaRequest.getCsv());
			nuevaReferenciaDto.setDataCreacio(LocalDate.now());
			nuevaReferenciaDto.setDireccio(URL_DESCARGA);
			nuevaReferenciaDto.setEmisor(obtenerReferenciaRequest.getEmisor());
			nuevaReferenciaDto.setFormatFirma(1L); // TODO
			nuevaReferenciaDto.setHash(hash);
			nuevaReferenciaDto.setReceptor(obtenerReferenciaRequest.getReceptor());
			nuevaReferenciaDto.setUrlVisible(null); // TODO
			nuevaReferenciaDto.setUuId(referencia);
			nuevaReferenciaDto.setId(null);
			nuevaReferenciaDto.setEstatExpedientId(estatExpedientId);
			nuevaReferenciaDto.setExpedientId(expedientId);
			Long referenciaDB = referenciaService.create(nuevaReferenciaDto);
			log.info("ReferenciaDB => " + String.valueOf(referenciaDB));

			// TODO permisos
			/*
			 * PUBLICO PRIVADO RESTRINGIDO
			 * 
			 */
			String permiso = Constants.PRIVADO;

			/*
			 * TODO firma eEMGDE.Firma.TipoFirma.FormatoFirma TF01 - CSV TF02 - XAdES
			 * internally detached signature TF03 - XAdES enveloped signature TF04 - CAdES
			 * detached/explicit signature TF05 - CAdES attached/implicit signature TF06 -
			 * PadES TF07 - XAdES manifest
			 */
			String tipoFirma = "TF06";

			/* GENERAM EL XML DE RESPOSTA */
			List<Metadada> metadadesList = obtenerReferenciaRequest.getMetadades();

			// TODO referencia = CSV o UUID
			String respostaXML = generateXMLResponse(referencia, obtenerReferenciaRequest.getEmisor(),
					obtenerReferenciaRequest.getReceptor(), permiso, tipoFirma, metadadesList, hash);

			/* GUARDAM EL XML GENERAT */
			ReferenciaXMLDTO referenciaXMLDto = new ReferenciaXMLDTO();
			referenciaXMLDto.setReferenciaId(referenciaDB);
			referenciaXMLDto.setResultat(respostaXML);
			referenciaXMLDto.setDataCreacio(LocalDate.now());
			referenciaXMLService.create(referenciaXMLDto);

			log.info("-------------------  INICI RESPOSTA XML ------------------------");
			log.info(respostaXML);
			log.info("-------------------  FI RESPOSTA XML ------------------------");

			log.info("ObtenirReferencia.nova FI");
			return Response.ok().entity(respostaXML).build();

		} else {
			log.error("Fitxer no s'ha pogut guardar");
			return Response.ok().entity("Error").build();
		}
	}

	@Path("/nova")
	@POST
	@RolesAllowed({ Constants.ITD_WS })
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(tags = "Nova referencia", operationId = "nova", summary = "Crear una nova referencia", method = "post")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Paràmetres incorrectes", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@ApiResponse(responseCode = "200", description = "XML Referència Única", content = @Content(mediaType = MediaType.APPLICATION_XML, schema = @Schema(implementation = String.class))) })
	public Response obtenerReferenciaCreate(

			@Parameter(description = "CSV", required = false, example = "CSV", schema = @Schema(implementation = String.class)) @QueryParam("csv") String csv,

			@Parameter(description = "UUID", required = false, example = "UUID", schema = @Schema(implementation = String.class)) @QueryParam("uuid") String uuid,

			@Parameter(description = "aplicacioId", required = false, example = "aplicacioId", schema = @Schema(implementation = String.class)) @QueryParam("aplicacioId") String aplicacioId,

			@Parameter(description = "emisor", required = false, example = "emisor", schema = @Schema(implementation = String.class)) @QueryParam("emisor") String emisor,

			@Parameter(description = "receptor", required = false, example = "receptor", schema = @Schema(implementation = String.class)) @QueryParam("receptor") String receptor,

			@Parameter(description = "interessats", required = false, example = "interessats", schema = @Schema(implementation = String.class)) @QueryParam("interessats") String interessats,

			@Parameter(description = "metadades", required = false, example = "metadades", schema = @Schema(implementation = String.class)) @QueryParam("metadades") String metadades,

			@Parameter(description = "fitxer", required = false, example = "fitxer", schema = @Schema(implementation = String.class)) @QueryParam("fitxer") String fitxer

	) {
		log.info("ObtenirReferencia.nova INICI");

		log.info("---------------- DADES ENTRADA -------------------");
		log.info("csv: " + ((csv != null) ? csv : "null"));
		log.info("uuid: " + ((uuid != null) ? uuid : "null"));
		log.info("document size: " + ((fitxer != null) ? fitxer.length() : "null"));
		log.info("metadades: " + ((metadades != null) ? metadades : "null"));
		log.info("aplicacioId: " + ((aplicacioId != null) ? aplicacioId : "null"));
		log.info("emisor: " + ((emisor != null) ? emisor : "null"));
		log.info("receptor: " + ((receptor != null) ? receptor : "null"));
		log.info("interessats: " + interessats);
		log.info("isBase64: " + (Base64.isBase64(fitxer) ? "true" : "false"));
		log.info("--------------------------------------------------");

		if (Utils.isEmpty(csv) && Utils.isEmpty(uuid) && fitxer.length() < 1) {
			return Response.ok().entity("Error: el camp CSV, UUID o fitxer binary és obligatori").build();
		}

		boolean isEnidoc = Utils.isNotEmpty(uuid);
		boolean isSigned = false;
		boolean isInArxiu = false;

		String estatExpedientId = "";
		String expedientId = "";

		log.info("isEnidoc:" + ((isEnidoc) ? "true" : "false"));

		if (!isEnidoc && Utils.isNotEmpty(fitxer)) {
			// Si no és un fitxer ENIDOC, s'ha de firmar i pujar a l'arxiu

			/* INICI FIRMA EN SERVIDOR */
			isSigned = true;
			/* FI FIRMA EN SERVIDOR */

			if (isSigned) {
				/* INICI PUJADA ARXIU */

				log.info("Pujada a Arxiu");

				/* Metadades */
				Type listType = new TypeToken<ArrayList<Metadada>>() {
				}.getType();
				List<Metadada> metadadesList = new Gson().fromJson(metadades, listType);
				final int numMetadades = metadadesList.size();
				Map<String, Object> metadadesInfoObj = new HashMap<>(numMetadades);
				if (numMetadades > 0) {
					for (Metadada m : metadadesList) {
						log.info("metadada.clau => " + m.getClau() + " metadada.valor => " + m.getValor());
						metadadesInfoObj.put(m.getClau(), m.getValor());
					}
				}

				byte[] fitxerDescodificat = null;
				try {
					fitxerDescodificat = Base64.decode(fitxer);
					// Base64.getDecoder().decode(fitxer.getBytes("UTF-8"));
				} catch (IOException e) {
					e.printStackTrace();
				}

				Fitxer fitxerInfo = new Fitxer();
				if (fitxerDescodificat != null) {
					fitxerInfo.setArxiuNom("f_" + System.currentTimeMillis());
					fitxerInfo.setContingut(fitxerDescodificat);
					fitxerInfo.setTamany(fitxerDescodificat.length);
					// fitxerInfo.setTipusMime(fitxerTmp.getMime());
					fitxerInfo.setExtensio(null);
				}

				ArxiuController arxiu = new ArxiuController();

				DocumentInfo documentInfo = new DocumentInfo();
				documentInfo.setNom("prova_exp_" + System.currentTimeMillis());
				documentInfo.setOrgans(Arrays.asList(emisor));
				documentInfo.setInteressats(Arrays.asList(interessats));
				documentInfo.setMetadades(metadadesInfoObj);
				documentInfo.setOrigen(Origen.ADMINISTRACIO);
				documentInfo.setFitxer(fitxerInfo);

				log.info(documentInfo.toString());

				// arxiu.crearExpedient(documentInfo);

				// TODO GUARDAM expedientId i estatExpedientId
				expedientId = "expedientId";
				estatExpedientId = "estatExpedientId";

				isInArxiu = true;

				/* FI PUJADA ARXIU */

			} else {
				log.info("Error: El fitxer   no está firmat i no es pot pujar a Arxiu");
			}

		}

		if (isEnidoc || (isSigned && isInArxiu)) {

			/* CREAM LA REFERENCIA */
			String referencia = createReferencia();
			log.info("Referencia generada: " + referencia);

			// Generate Hash
			String hash = null;
			if (fitxer != null) {

				byte[] fitxerDescodificat;
				try {
					fitxerDescodificat = (fitxer != null) ? Base64.decode(fitxer) : null;
					// Base64.getDecoder().decode(fitxer.getBytes())
					MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
					hash = getFileChecksum(shaDigest, fitxerDescodificat);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			/* GUARDAM LA INFORMACIÓ DE LA REFERENCIA */
			ReferenciaDTO nuevaReferenciaDto = new ReferenciaDTO();
			nuevaReferenciaDto.setCsvId(csv);
			nuevaReferenciaDto.setDataCreacio(LocalDate.now());
			nuevaReferenciaDto.setDireccio(URL_DESCARGA);
			nuevaReferenciaDto.setEmisor(emisor);
			nuevaReferenciaDto.setFormatFirma(1L); // TODO
			nuevaReferenciaDto.setHash(hash);
			nuevaReferenciaDto.setReceptor(receptor);
			nuevaReferenciaDto.setUrlVisible(null); // TODO
			nuevaReferenciaDto.setUuId(referencia);
			nuevaReferenciaDto.setId(null);
			nuevaReferenciaDto.setEstatExpedientId(estatExpedientId);
			nuevaReferenciaDto.setExpedientId(expedientId);
			Long referenciaDB = referenciaService.create(nuevaReferenciaDto);
			log.info("ReferenciaDB => " + String.valueOf(referenciaDB));

			// TODO permisos
			/*
			 * PUBLICO PRIVADO RESTRINGIDO
			 * 
			 */
			String permiso = Constants.PRIVADO;

			/*
			 * TODO firma eEMGDE.Firma.TipoFirma.FormatoFirma TF01 - CSV TF02 - XAdES
			 * internally detached signature TF03 - XAdES enveloped signature TF04 - CAdES
			 * detached/explicit signature TF05 - CAdES attached/implicit signature TF06 -
			 * PadES TF07 - XAdES manifest
			 */
			String tipoFirma = "TF06";

			/* GENERAM EL XML DE RESPOSTA */
			Type listType = new TypeToken<ArrayList<Metadada>>() {
			}.getType();
			List<Metadada> metadadesList = new Gson().fromJson(metadades, listType);

			// TODO referencia = CSV o UUID
			String respostaXML = generateXMLResponse(referencia, emisor, receptor, permiso, tipoFirma, metadadesList,
					hash);

			/* GUARDAM EL XML GENERAT */
			ReferenciaXMLDTO referenciaXMLDto = new ReferenciaXMLDTO();
			referenciaXMLDto.setReferenciaId(referenciaDB);
			referenciaXMLDto.setResultat(respostaXML);
			referenciaXMLDto.setDataCreacio(LocalDate.now());
			referenciaXMLService.create(referenciaXMLDto);

			log.info("-------------------  INICI RESPOSTA XML ------------------------");
			log.info(respostaXML);
			log.info("-------------------  FI RESPOSTA XML ------------------------");

			log.info("ObtenirReferencia.nova FI");
			return Response.ok().entity(respostaXML).build();

		} else {
			log.error("Fitxer no s'ha pogut guardar");
			return Response.ok().entity("Error").build();
		}

	}

	private String generateXMLResponse(String referencia, String emisor, String receptor, String permiso,
			String formatoFirma, List<Metadada> metadadesInfo, String hash) {

		String resposta = null;

		try {

			ReferenciaDocumentoBean referenciaDocumentoBean = new ReferenciaDocumentoBean();

			referenciaDocumentoBean.setDireccion(URL_DESCARGA);

			EmisorBean emisorBean = new EmisorBean();
			emisorBean.setOrganismo(emisor);
			referenciaDocumentoBean.setEmisorBean(emisorBean);
			referenciaDocumentoBean.setHash(hash);

			IdentificadorBean identificadorBean = new IdentificadorBean();
			identificadorBean.setSecuenciaIdentificador(referencia);
			referenciaDocumentoBean.setIdentificadorBean(identificadorBean);

			final int numMetadades = metadadesInfo.size();
			if (numMetadades > 0) {

				MetadatosBean metadatosBean = new MetadatosBean();
				List<MetadatoBean> metadatos = new ArrayList<MetadatoBean>(numMetadades);
				for (Metadada item : metadadesInfo) {
					MetadatoBean metadatoBean = new MetadatoBean();
					metadatoBean.setNombre(item.getClau());
					metadatoBean.setValor(item.getValor());
					metadatos.add(metadatoBean);
				}
				metadatosBean.setMetadatoBeanList(metadatos);
				referenciaDocumentoBean.setMetadatosBean(metadatosBean);
			}

			PermisoBean permisoBean = new PermisoBean();

			switch (permiso) {
			case Constants.PRIVADO:
				permisoBean.setPrivado(permiso);
				break;
			case Constants.PUBLICO:
				permisoBean.setPublico(permiso);
				break;
			// case Constants.RESTRINGIDO:
			// RestringidoBean restringidoBean = new RestringidoBean();
			// permisoBean.setRestringidoBean(null);
			default:
				permisoBean.setPrivado(permiso);
				break;
			}
			referenciaDocumentoBean.setPermisoBean(permisoBean);

			ReceptorBean receptorBean = new ReceptorBean();
			receptorBean.setOrganismo(receptor);
			referenciaDocumentoBean.setReceptorBean(receptorBean);

			referenciaDocumentoBean.setFormatoFirma(formatoFirma);

			// TODO trazabilidad

			log.info("referenciaDocumentoBean toString");
			resposta = MarshallUtil.generateXML(ReferenciaDocumentoBean.class, referenciaDocumentoBean);
			log.info(resposta);

		} catch (Exception e) {
			log.error("EXCEPTION" + e.getMessage());
			e.printStackTrace();
		}

		return resposta;

	}

	private String createReferencia() {
		try {
			HashCreator nouHash = new HashCreator();
			return nouHash.createPasswordHashWithSalt(String.valueOf(System.currentTimeMillis()));
		} catch (Exception e) {
			log.error("ObtenerReferenciaService : createReferencia : No s'ha pogut crear la referencia");
			return null;
		}
	}

	private static String getFileChecksum(MessageDigest digest, byte[] data) throws IOException {

		ByteArrayInputStream bis = new ByteArrayInputStream(data);

		// Create byte array to read data in chunks
		byte[] byteArray = new byte[1024];
		int bytesCount = 0;

		while ((bytesCount = bis.read(byteArray)) != -1) {
			digest.update(byteArray, 0, bytesCount);
		}

		// Get the hash's bytes
		byte[] bytes = digest.digest();

		// This bytes[] has bytes in decimal format;
		// Convert it to hexadecimal format
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}

}
