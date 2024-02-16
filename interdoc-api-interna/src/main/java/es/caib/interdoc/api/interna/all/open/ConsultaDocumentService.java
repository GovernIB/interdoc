package es.caib.interdoc.api.interna.all.open;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.plugins.arxiu.ArxiuController;
import es.caib.plugins.arxiu.api.Document;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;

/**
 * Servei de consulta de documents
 * 
 */
@Path("/consulta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(tags = @Tag(name = "ConsultaDocumentService", description = "Servei JSON per consultar un document per UUID"))
public class ConsultaDocumentService {

	protected static Logger log = LoggerFactory.getLogger(ConsultaDocumentService.class);

	@Path("/document")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(tags = "Document", operationId = "Consulta document per UUID", summary = "Consulta de document", method = "get")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Paràmetres incorrectes", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@ApiResponse(responseCode = "200", description = "Document UUID", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class))) })
	public Response consultaDocument(

			@Parameter( description = "UUID del document", required = true, schema = @Schema(implementation = String.class))
			@QueryParam("uuid") String uuid,
		
			@Parameter( description = "codi de l'entitat", required = true, schema = @Schema(implementation = String.class))
			@QueryParam("entitatId") String entitatId) {
		try {

			if (Configuracio.isDesenvolupament()) {
				log.info("ConsultaDocumentService INICI");
				log.info("------ Paràmetres entrada -------");
				log.info("UUID: " + ((Utils.isNotEmpty(uuid)) ? uuid : "null"));
				log.info("EntitatId: " + (Utils.isNotEmpty(entitatId) ? entitatId: "null" ));
				log.info("----------------------------------");
			}
			
			if (Utils.isEmpty(uuid))
				return generateErrorResponse("Error: no ens arriba cap UUID ni fitxer. Al manco, ens ha d'arribar un d'ells.");		

			
			ArxiuController pluginArxiu = new ArxiuController(Long.parseLong(entitatId));
			
			Document doc = null; 
			
			if (pluginArxiu.getPlugin() != null)
				doc = pluginArxiu.getPlugin().descarregarDocument(uuid);
			
			if (doc != null) {
				log.info("--------- RECUPERAR DOCUMENT AMB UUID " + uuid + " -----------");
				log.info("document::nom => " + doc.getNom());
				log.info("document::descripcio => " + doc.getDescripcio());
				log.info("document::estat => " + doc.getEstat());
				log.info("---------------------------------------------------------------");
				
				byte[] contingut = doc.getContingut().getContingut();
				
				String headerValue = "attachment; filename=\"" + doc.getNom()+ "\"";
				return Response.ok().type(MediaType.APPLICATION_OCTET_STREAM).header(HttpHeaders.CONTENT_DISPOSITION, headerValue).entity(contingut).build();
			}
			
			return generateErrorResponse("Error: No s'ha recuperat cap fitxer amb aquest UUID");		
			
		} catch (Throwable th) {
			log.error("consultaDocument::error");
			th.printStackTrace();
			String msg = th.getMessage();
			return generateErrorResponse(msg);
		}
	}

	private Response generateErrorResponse(String msg) {
		log.error(msg);
		return Response.status(Response.Status.BAD_REQUEST).entity("{ \"error\" : " + "\"" + msg + "\" }").build();
	}

}
