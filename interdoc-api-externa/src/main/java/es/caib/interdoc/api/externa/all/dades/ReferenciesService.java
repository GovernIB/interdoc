package es.caib.interdoc.api.externa.all.dades;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.service.facade.AccesServiceFacade;
import es.caib.interdoc.service.facade.EntitatServiceFacade;
import es.caib.interdoc.service.facade.ReferenciaServiceFacade;
import es.caib.interdoc.service.model.AccesDTO;
import es.caib.interdoc.service.model.EntitatDTO;
import es.caib.interdoc.service.model.ReferenciaDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;

/**
 *
 * @author jagarcia
 *
 */
@Path("/public")
@OpenAPIDefinition(tags = @Tag(name = "Dades", description = "Servei per reutilització de dades"))
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "BasicAuth", scheme = "basic")
public class ReferenciesService {

	protected Logger log = LoggerFactory.getLogger(ReferenciesService.class);
	
	@EJB
	protected ReferenciaServiceFacade referenciesEjb;
	
	@EJB
	protected EntitatServiceFacade entitatEjb;
	
	@EJB
	protected AccesServiceFacade accessEjb;
	

	@Path("/dadesobertes/referencies")
	@GET
	@RolesAllowed({ Constants.ITD_WS })
	@SecurityRequirement(name = "BasicAuth")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(tags = {
			"Referencies" }, operationId = "getReferencies", summary = "Mostra les referencies creades per INTERDOC.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Paràmetres incorrectes", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@ApiResponse(responseCode = "401", description = "No Autenticat", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@ApiResponse(responseCode = "403", description = "No Autoritzat", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@ApiResponse(responseCode = "400", description = "Error durant el processament", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "200", description = "Enviat missatge correctament", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class))) })
	public Response getReferencies(

			@Parameter(description = "Data d'inici, en format YYYY-MM-DD, a partir de la qual volem obtenir estadistiques", required = false, example = "2023-07-01", schema = @Schema(implementation = String.class)) @QueryParam("inici") String dataIniciRequest,

			@Parameter(description = "Data fi, en format YYYY-MM-DD, fins la qual volem tenir estadistiques", required = false, example = "2023-07-30", schema = @Schema(implementation = String.class)) @QueryParam("fi") String dataFiRequest

	) {

		try {

			// Si no hi ha parametres de dates, es retorna per defecte el darrer mes
			// Li suman 1 dia per fer les cerques inclusives

			ZoneId zoneId = ZoneId.of("Europe/Madrid");
			ZonedDateTime dataFiDate;
			if (dataFiRequest == null) {
				dataFiDate = ZonedDateTime.now(zoneId).plusDays(1);
				dataFiRequest = dataFiDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			} else {
				dataFiDate = LocalDate.parse(dataFiRequest, DateTimeFormatter.ofPattern("yyyy-MM-dd")).plusDays(1)
						.atStartOfDay(zoneId);
				dataFiRequest = dataFiDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			}

			ZonedDateTime dataIniciDate;
			if (dataIniciRequest == null) {
				dataIniciDate = LocalDate.parse(dataFiRequest, DateTimeFormatter.ofPattern("yyyy-MM-dd")).minusMonths(1)
						.atStartOfDay(zoneId);
				dataIniciRequest = dataIniciDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			} else {
				dataIniciDate = LocalDate.parse(dataIniciRequest, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
						.atStartOfDay(zoneId);
			}

			if (Configuracio.isDesenvolupament()) {
				log.info("REQUEST API EXTERNA ACCESSOS: {inici=" + dataIniciRequest + "&fi=" + dataFiRequest + "&timezone="
						+ dataFiDate.getZone() + "}");
			}
			
			
			Optional<List<ReferenciaDTO>> llistat = referenciesEjb.findBetweenDates(dataIniciDate.toLocalDate(), dataFiDate.toLocalDate());
			List<ReferenciaStat> resposta = new ArrayList<ReferenciaStat>();
			
			if (!llistat.isEmpty()) {
				for (ReferenciaDTO item : llistat.get()) {
					
					String entitatNom = "";
					if (item.getEntitatId() != null && item.getEntitatId() > 0) {
						Optional<EntitatDTO> entitat = entitatEjb.findById(item.getEntitatId());
						entitatNom = (entitat.isPresent()) ? entitat.get().getNom() : "";
					}
					
					Optional<List<AccesDTO>> llistaAccessos = accessEjb.findByRefenciaId(item.getId());
					int numAccesos = (!llistaAccessos.isEmpty()) ? llistaAccessos.get().size() : 0;
				
					final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");
					
					ReferenciaStat refStat = new ReferenciaStat();
					refStat.setReferenciaId(String.valueOf(item.getId()));
					refStat.setIsCSV((Utils.isNotEmpty(item.getCsvId())) ? "Sí" : "No");
					refStat.setIsUUID((Utils.isNotEmpty(item.getUuId())) ? "Sí" : "No");
					refStat.setEmisor((Utils.isNotEmpty(item.getEmisor())) ? item.getEmisor() : "");
					refStat.setReceptor((Utils.isNotEmpty(item.getReceptor()) ? item.getReceptor() : ""));
					refStat.setDataCreacio(item.getDataCreacio().format(formatter));
					refStat.setEntitat(entitatNom);
					refStat.setFormatfirma((Utils.isNotEmpty(item.getFormatFirma()) ? item.getFormatFirma() : ""));
					refStat.setNumAccesos(numAccesos);
					resposta.add(refStat);
				}
			}

			return Response.status(Response.Status.ACCEPTED).entity(resposta).build();

		} catch (Throwable th) {

			String msg;
			msg = th.getMessage();
			log.error("Error desconegut en la cridada api rest enviar notificacions: " + msg, th);

			return generateError(msg);

		}

	}

	protected Response generateError(String msg) {
		return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
	}

}
