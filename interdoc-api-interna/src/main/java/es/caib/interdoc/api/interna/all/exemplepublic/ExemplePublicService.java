package es.caib.interdoc.api.interna.all.exemplepublic;

import javax.inject.Inject;
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

import es.caib.interdoc.commons.utils.Version;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;

/**
 *  Exemple de Servei JSON d'accés Públic
 
 */
@Path("/public/exemplepublic")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(tags = @Tag(name = "ExemplePublicService", description = "Exemple de Servei JSON d'accés Públic"))
public class ExemplePublicService {

    protected static Logger log = LoggerFactory.getLogger(ExemplePublicService.class);
    
    @Inject
    private Version version;

    @Path("/versio")
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            tags = "Versió",
            operationId = "versio",
            summary = "Versio de l'Aplicació",
            method = "get")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "Paràmetres incorrectes",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON)),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Versió i idioma",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = ExemplePojo.class))) })
    public Response versio(

            @Parameter(
                    description = "Codi de l'idioma",
                    required = false,
                    example = "ca",
                    schema = @Schema(implementation = String.class)) @Pattern(regexp = "^ca|es$") @QueryParam("idioma")
            String idioma) {

        try {
            ExemplePojo exemple = new ExemplePojo(version.getVersion() + "_" + idioma);

            return Response.ok().entity(exemple).build();

        } catch (Throwable th) {

            String msg = th.getMessage();
            log.error("Error cridada api rest 'versio': " + msg, th);

            return Response.status(Response.Status.BAD_REQUEST).entity("{ \"error\" : " + "\"" + msg + "\" }").build();

        }
    }

}
