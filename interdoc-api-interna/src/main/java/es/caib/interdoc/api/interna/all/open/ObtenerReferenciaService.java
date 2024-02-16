package es.caib.interdoc.api.interna.all.open;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.api.interna.ws.model.ObtenerReferenciaRequestInfo;
import es.caib.interdoc.api.interna.ws.resposta.EmisorBean;
import es.caib.interdoc.api.interna.ws.resposta.IdentificadorBean;
import es.caib.interdoc.api.interna.ws.resposta.MetadatoBean;
import es.caib.interdoc.api.interna.ws.resposta.MetadatosBean;
import es.caib.interdoc.api.interna.ws.resposta.PermisoBean;
import es.caib.interdoc.api.interna.ws.resposta.ReceptorBean;
import es.caib.interdoc.api.interna.ws.resposta.ReferenciaDocumentoBean;
import es.caib.interdoc.api.interna.ws.utilitats.HashCreator;
import es.caib.interdoc.api.interna.ws.utils.Metadada;
import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.commons.utils.MarshallUtil;
import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.service.facade.EntitatServiceFacade;
import es.caib.interdoc.service.facade.FitxerServiceFacade;
import es.caib.interdoc.service.facade.InfoArxiuServiceFacade;
import es.caib.interdoc.service.facade.InfoSignaturaServiceFacade;
import es.caib.interdoc.service.facade.ReferenciaServiceFacade;
import es.caib.interdoc.service.facade.ReferenciaXMLServiceFacade;
import es.caib.interdoc.service.model.EntitatDTO;
import es.caib.interdoc.service.model.InfoSignaturaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;

/**
 * Exemple de Servei JSON d'accés Públic
 * 
 */
@Path("/obtenerReferencia")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(tags = @Tag(name = "ObtenerReferenciaService", description = "Servei JSON per operar amb referències"))
public class ObtenerReferenciaService {

	protected static Logger log = LoggerFactory.getLogger(ObtenerReferenciaService.class);
	
	@EJB(mappedName = FitxerServiceFacade.JNDI_NAME)
	protected FitxerServiceFacade fitxerService;

	@EJB(mappedName = ReferenciaServiceFacade.JNDI_NAME)
	protected ReferenciaServiceFacade referenciaService;

	@EJB(mappedName = ReferenciaXMLServiceFacade.JNDI_NAME)
	protected ReferenciaXMLServiceFacade referenciaXMLService;

	@EJB(mappedName = InfoSignaturaServiceFacade.JNDI_NAME)
	protected InfoSignaturaServiceFacade infoSignaturaService;

	@EJB(mappedName = InfoArxiuServiceFacade.JNDI_NAME)
	protected InfoArxiuServiceFacade infoArxiuService;

	@EJB(mappedName = EntitatServiceFacade.JNDI_NAME)
	protected EntitatServiceFacade entitatService;
	
	private String baseWsUrl = null;
	
	public void setBaseWsUrl(String url) {
		this.baseWsUrl = url;
	}

	public String getBaseWsUrl() {
		return baseWsUrl;
	}
	

	@Path("/nova")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(tags = "Versió", operationId = "versio", summary = "Versio de l'Aplicació", method = "get")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Paràmetres incorrectes", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@ApiResponse(responseCode = "200", description = "Versió i idioma", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ExemplePojo.class))) })
	public Response nova(

			/*
			 * 
			 * @Parameter( description = "Codi de l'idioma", required = false, example =
			 * "ca", schema = @Schema(implementation = String.class))
			 * 
			 */
			@QueryParam("csv") String csv, @QueryParam("uuid") String uuid, @QueryParam("metadades") String metadades,
			@QueryParam("aplicacio") String aplicacio, @QueryParam("entitat") String entitat,
			@QueryParam("emisor") String emisor, @QueryParam("receptor") String receptor,
			@QueryParam("interessats") String interessats) {

		try {

			boolean isEnidoc = Utils.isNotEmpty(uuid);

			boolean isSigned = false;
			boolean isInArxiu = false;

			InfoSignaturaDTO infoSignatura = null;
			String identificadorExpedient = null;
			String identificadorDocument = null;

			Long infoSignaturaId = 0L;
			Long infoArxiuId = 0L;
			Long fitxerId = 0L;

			if (Configuracio.isDesenvolupament()) {
				log.info("ObtenerReferenciaService:nova INICI");
				log.info("------ Paràmetres entrada -------");
				log.info("CSV: " + ((csv != null) ? csv : "null"));
				log.info("UUID: " + ((uuid != null) ? uuid : "null"));
				log.info("METADADES: " + ((metadades != null) ? metadades : "null"));
				log.info("APPLICACIO_ID: " + ((aplicacio != null) ? aplicacio : "null"));
				log.info("EMISOR: " + ((emisor != null) ? emisor : "null"));
				log.info("RECEPTOR: " + ((receptor != null) ? receptor : "null"));
				log.info("INTERESSATS: " + ((interessats != null) ? interessats : "0"));
				log.info("ENTITATID: " + ((entitat != null) ? entitat : "0"));
				log.info("----------------------------------");
				log.info("isEnidoc:" + ((isEnidoc) ? "true" : "false"));
			}

			// Validació: ens ha d'arribar un identificador o un fitxer
			if (Utils.isEmpty(uuid) || Utils.isEmpty(csv)){
				return generateErrorResponse("Error: no ens arriba cap UUID ni fitxer. Al manco, ens ha d'arribar un d'ells.");
			}
			
			Long entitatId = 0L;
			if (Utils.isNotEmpty(entitat)) {
				EntitatDTO entitatDto = entitatService.findByCodiDir3(entitat).orElseThrow();
				if (entitatDto != null)
					entitatId = entitatDto.getId();

				log.info("CodiDir3 => " + entitat + " - id => " + entitatId);
			} else {
				return generateErrorResponse("Error: el codi d'entitat és obligatori.");
			}
			
			if(!isEnidoc) {
				
				// TODO
				log.info("NOT IS Enidoc");
				
				isSigned = true;
				isInArxiu = true;
			}

			if(isEnidoc || (isSigned && isInArxiu)) {
				String respostaXML = generateXMLResponse(csv, uuid, emisor, receptor, "referencia", "hash", null);
				return Response.ok().entity(respostaXML).build();
			}
			
			return Response.status(Response.Status.NO_CONTENT).entity("{}").build();
			
		} catch (Throwable th) {

			String msg = th.getMessage();
			log.error("Error cridada api rest 'nova': " + msg, th);

			return generateErrorResponse(msg);

		}
	}
	
	private Response generateErrorResponse(String msg) {
		return Response.status(Response.Status.BAD_REQUEST).entity("{ \"error\" : " + "\"" + msg + "\" }").build();
	}
	
	private String getCsvQueryDocumentWebserviceURL() {
		return Configuracio.getCsvQueryDocumentWebserviceURL();
	}
	
	private String generateXMLResponse(String csv, String uuid, String emisor, String receptor, String referencia,
			String hash, List<Metadada> metadades) throws Exception {

		 
		
		ReferenciaDocumentoBean referenciaDocumentoBean = new ReferenciaDocumentoBean();

		referenciaDocumentoBean.setDireccion(getBaseWsUrl() + getCsvQueryDocumentWebserviceURL() + "?wsdl");

		if (Utils.isNotEmpty(emisor)) {
			EmisorBean emisorBean = new EmisorBean();
			emisorBean.setOrganismo(emisor);
			referenciaDocumentoBean.setEmisorBean(emisorBean);
		}

		if (Utils.isNotEmpty(hash)) {
			referenciaDocumentoBean.setHash(hash);
		}

		IdentificadorBean identificadorBean = new IdentificadorBean();
		if (Utils.isNotEmpty(csv)) {
			identificadorBean.setValorCSV(csv);
		} else if (Utils.isNotEmpty(uuid)) {
			identificadorBean.setSecuenciaIdentificador(uuid);
		} else {
			identificadorBean.setSecuenciaIdentificador(referencia);
		}
		referenciaDocumentoBean.setIdentificadorBean(identificadorBean);

		if (metadades != null) {
			final int numMetadades = metadades.size();
			if (numMetadades > 0) {

				MetadatosBean metadatosBean = new MetadatosBean();
				List<MetadatoBean> metadatos = new ArrayList<MetadatoBean>(numMetadades);

				List<Metadada> metadadesInfo = new ArrayList<Metadada>(numMetadades);
				metadadesInfo = metadades;

				for (Metadada item : metadadesInfo) {
					MetadatoBean metadatoBean = new MetadatoBean();
					metadatoBean.setNombre(item.getClau());
					metadatoBean.setValor(item.getValor());
					metadatos.add(metadatoBean);
				}
				metadatosBean.setMetadatoBeanList(metadatos);
				referenciaDocumentoBean.setMetadatosBean(metadatosBean);
			}
		}

		// TODO permisos restringidos por usuario/aplicacion
		PermisoBean permisoBean = new PermisoBean();
		permisoBean.setPublico(Constants.PUBLICO);
		referenciaDocumentoBean.setPermisoBean(permisoBean);

		if (Utils.isNotEmpty(receptor)) {
			ReceptorBean receptorBean = new ReceptorBean();
			receptorBean.setOrganismo(receptor);
			referenciaDocumentoBean.setReceptorBean(receptorBean);
		}

		/*
		 * TODO eEMGDE.Firma.TipoFirma.FormatoFirma TF01 - CSV TF02 - XAdES internally
		 * detached signature TF03 - XAdES enveloped signature TF04 - CAdES
		 * detached/explicit signature TF05 - CAdES attached/implicit signature TF06 -
		 * PadES TF07 - XAdES manifest
		 * 
		 */
		// TODO referenciaDocumentoBean.setFormatoFirma("TF06");

		// TODO trazabilidad

		String sw = MarshallUtil.generateXML(ReferenciaDocumentoBean.class, referenciaDocumentoBean);

		if (Configuracio.isDesenvolupament())
			log.info(sw);

		return sw;

	}
	
	

}
