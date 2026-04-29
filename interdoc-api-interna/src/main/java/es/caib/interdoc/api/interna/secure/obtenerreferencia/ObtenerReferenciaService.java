package es.caib.interdoc.api.interna.secure.obtenerreferencia;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/secure/obtenerreferencia")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(tags = @Tag(name = ObtenerReferenciaService.TAG_NAME, description = "Servei JSON per consultar un document per UUID"))
@SecurityScheme(type = SecuritySchemeType.HTTP, name = ObtenerReferenciaService.SECURITY_NAME, scheme = "basic")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Paràmetres incorrectes", content = @Content(mediaType = MediaType.APPLICATION_JSON
        /* schema = @Schema(implementation = RestExceptionInfo.class) */)),
        @ApiResponse(responseCode = "401", description = "No Autenticat", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON
                /* schema = @Schema(implementation = RestExceptionInfo.class) */) }),
        @ApiResponse(responseCode = "403", description = "No autoritzat", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON
                /* schema = @Schema(implementation = RestExceptionInfo.class) */) }),
        @ApiResponse(responseCode = "500", description = "Error no controlat", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON
                /* schema = @Schema(implementation = RestExceptionInfo.class) */) })
})
public class ObtenerReferenciaService {

    protected static final String TAG_NAME = "ConsultaDocumentService";

    protected static final String SECURITY_NAME = "BasicAuth";

    protected static Logger log = LoggerFactory.getLogger(ObtenerReferenciaService.class);

    
    
    
}
