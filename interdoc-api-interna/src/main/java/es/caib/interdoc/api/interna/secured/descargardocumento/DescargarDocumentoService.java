package es.caib.interdoc.api.interna.secured.descargardocumento;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.mail.util.ByteArrayDataSource;
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

import es.caib.interdoc.api.interna.secured.descargardocumento.request.DescargarDocumentoRequest;
import es.caib.interdoc.api.interna.secured.descargardocumento.request.DocumentoRequest;
import es.caib.interdoc.api.interna.secured.descargardocumento.resposta.CSVQueryDocumentMtomSecurityResponse;
import es.caib.interdoc.api.interna.secured.descargardocumento.resposta.ContenidoMtomInfo;
import es.caib.interdoc.api.interna.secured.descargardocumento.resposta.DocumentoMtomResponse;
import es.caib.interdoc.commons.utils.Base64;
import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.commons.utils.MarshallUtil;
import es.caib.interdoc.commons.utils.Version;
import es.caib.interdoc.service.facade.ReferenciaServiceFacade;
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

@Path("/secured/documento")
@OpenAPIDefinition(tags = @Tag(name = "Descarregar Document", description = "Descarregar un document desde Interdoc"))
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "BasicAuth", scheme = "basic")
public class DescargarDocumentoService {

	protected Logger log = LoggerFactory.getLogger(DescargarDocumentoService.class);

	@Inject
	private Version version;

	@EJB
	protected ReferenciaServiceFacade referenciaService;


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

			@Parameter(description = "Codi de l'idioma", required = false, example = "ca", schema = @Schema(implementation = String.class)) 
			@Pattern(regexp = "^ca|es$") 
			@QueryParam("idioma") String idioma) {

		try {
			return Response.ok().entity(version.getVersion() + "_" + idioma).build();

		} catch (Throwable th) {

			String msg = th.getMessage();
			log.error("Error cridada api rest 'versio': " + msg, th);
			return Response.status(Response.Status.BAD_REQUEST).entity("{ \"error\" : " + "\"" + msg + "\" }").build();

		}
	}
	
	
	@Path("/upload")
	@POST
	@RolesAllowed({ Constants.ITD_WS })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(tags = "Carregar document", operationId = "carregar", summary = "Carrega un document", method = "post")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Paràmetres incorrectes", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@ApiResponse(responseCode = "200", description = "XML Referència Única", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class))) })
	public Response cargaDocumento(@RequestBody DocumentoRequest documentoRequest) {
		
		String filePath = "C:/Dades/Server/Upload/" + documentoRequest.getNombre();
        
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            BufferedOutputStream outputStream = new BufferedOutputStream(fos);
            outputStream.write(Base64.decode(documentoRequest.getDocumento()));
            outputStream.close();
             
            System.out.println("Received file: " + filePath);
            
            return Response.status(Response.Status.OK).build();
             
        } catch (IOException ex) {
            System.err.println(ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
	}
	
	@Path("/download")
	@GET
	@RolesAllowed({ Constants.ITD_WS })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(tags = "Descarregar document", operationId = "descarregar", summary = "Descarrega un document", method = "post")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Paràmetres incorrectes", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@ApiResponse(responseCode = "200", description = "XML Referència Única", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class))) })
	public byte[] download( @QueryParam("fileName") String fileName) {
        String filePath = "C:/Dades/Server/Upload/" + fileName;
        System.out.println("Sending file: " + filePath);
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream inputStream = new BufferedInputStream(fis);
            byte[] fileBytes = new byte[(int) file.length()];
            inputStream.read(fileBytes);
            inputStream.close();         
            return fileBytes;
        } catch (IOException ex) {
            System.err.println(ex);
            return null;
        }      
    }
			
	@Path("/descargar")
	@POST
	@RolesAllowed({ Constants.ITD_WS })
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(tags = "Descarregar document", operationId = "descarregar", summary = "Descarrega un document a partir de la seva referencia", method = "post")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Paràmetres incorrectes", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@ApiResponse(responseCode = "200", description = "XML Referència Única", content = @Content(mediaType = MediaType.APPLICATION_XML, schema = @Schema(implementation = String.class))) })

	public Response descargaDocumento(@RequestBody DescargarDocumentoRequest descargarDocumentoRequest) {
		
		log.info("descargaDocumento.descargar INICI");

		log.info("---------------- DADES ENTRADA -------------------");
		log.info("csv: " + ((descargarDocumentoRequest.getCsv() != null) ? descargarDocumentoRequest.getCsv() : "null"));
		log.info("dir3: " + ((descargarDocumentoRequest.getDir3() != null) ? descargarDocumentoRequest.getDir3() : "null"));
		log.info("recuperacionOriginal: " + ((descargarDocumentoRequest.getRecuperacionOriginal() != null) ? descargarDocumentoRequest.getRecuperacionOriginal() : "null"));
		log.info("documentoEni: "	+ ((descargarDocumentoRequest.getDocumentoEni() != null) ? descargarDocumentoRequest.getDocumentoEni() : "null"));
		log.info("idEni: " + ((descargarDocumentoRequest.getIdEni() != null) ? descargarDocumentoRequest.getIdEni()	: "null"));
		log.info("nif: "	+ ((descargarDocumentoRequest.getNif() != null) ? descargarDocumentoRequest.getNif() : "null")); 
		log.info("tipoIdentificacion: " + ((descargarDocumentoRequest.getTipoIdentificacion() != null) ? descargarDocumentoRequest.getTipoIdentificacion() : "null"));
		log.info("ip: " + ((descargarDocumentoRequest.getIp() != null) ? descargarDocumentoRequest.getIp() : "null"));
		log.info("--------------------------------------------------");

			String codigo = "000";
			String descripcion = "Correcto";
			String mime = "application/pdf";
			byte[] fitxer = null;
			try {
				fitxer = descargarArchivoUrl("https://www.boe.es/boe/dias/2023/04/05/pdfs/BOE-S-2023-81.pdf");
				log.info("Fitxer length" + fitxer.length);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String respostaXML = generateXMLResponse(codigo, descripcion, mime, fitxer);
			
			return Response.ok().entity(respostaXML).build();

	}

	
	private String generateXMLResponse(String codigo, String descripcion, String mime, byte[] data) {

		String resposta = null;

		try {

			CSVQueryDocumentMtomSecurityResponse csvQueryDocumentResponse = new CSVQueryDocumentMtomSecurityResponse();
			
			csvQueryDocumentResponse.setCode(codigo);
			csvQueryDocumentResponse.setDescription(descripcion);
			
			DocumentoMtomResponse documentoMtom = new DocumentoMtomResponse();
			documentoMtom.setCodigo(codigo);
			documentoMtom.setDescripcion(descripcion);
			
			if( data != null) {
				ContenidoMtomInfo contenidoMtom = new ContenidoMtomInfo();
				DataSource dataSource = new ByteArrayDataSource(data, mime);
				DataHandler dataHandler = new DataHandler(dataSource);
				contenidoMtom.setContenido(dataHandler);
				contenidoMtom.setTipoMIME(mime);
				csvQueryDocumentResponse.setDocumentoMtomResponse(documentoMtom);
			}
			
			log.info("generateXMLResponse toString");
			resposta = MarshallUtil.generateXML(CSVQueryDocumentMtomSecurityResponse.class, csvQueryDocumentResponse);
			log.info(resposta);

		} catch (Exception e) {
			log.error("EXCEPTION" + e.getMessage());
			e.printStackTrace();
		}

		return resposta;

	}

	
	public byte[] descargarArchivoUrl (String url) throws Exception {
	
		byte[] data = null;
        BufferedInputStream in = null;
        ByteArrayOutputStream fout = null;
        
        try {
        	
        	in = new BufferedInputStream(new URL(url).openStream());
        	fout = new ByteArrayOutputStream();
        	
        	final byte[] buffer = new byte[1024];
            int count;
            while ((count = in.read(buffer, 0, 1024)) != -1) {
                fout.write(buffer, 0, count);
            }
            data = fout.toByteArray();
        	
        } catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
        
        return data;
	}
	

	
}
