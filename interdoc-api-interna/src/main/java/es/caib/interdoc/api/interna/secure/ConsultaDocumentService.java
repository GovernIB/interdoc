package es.caib.interdoc.api.interna.secure;

import java.io.StringWriter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.ejb.facade.PluginArxiuServiceFacade;
import es.caib.interdoc.plugins.arxiu.DocumentInfo;
import es.caib.interdoc.plugins.arxiu.InterdocArxiuPlugin;
import es.caib.interdoc.service.facade.ReferenciaServiceFacade;
import es.caib.interdoc.service.model.ReferenciaDTO;
import es.caib.pluginsib.arxiu.api.Document;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;

/**
 * Servei de consulta de documents
 * 
 */
@Path("/secure/consulta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(tags = @Tag(name = ConsultaDocumentService.TAG_NAME, description = "Servei JSON per consultar un document per UUID"))
@SecurityScheme(type = SecuritySchemeType.HTTP, name = ConsultaDocumentService.SECURITY_NAME, scheme = "basic")
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
public class ConsultaDocumentService {

    protected static final String TAG_NAME = "ConsultaDocumentService";

    protected static final String SECURITY_NAME = "BasicAuth";

    protected static Logger log = LoggerFactory.getLogger(ConsultaDocumentService.class);

    @EJB(mappedName = PluginArxiuServiceFacade.JNDI_NAME)
    private PluginArxiuServiceFacade pluginService;

    @EJB(mappedName = ReferenciaServiceFacade.JNDI_NAME)
    private ReferenciaServiceFacade referenciaService;

    @Path("/consultaDocument")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(tags = "Document", operationId = "consultaDocument", summary = "Consulta document per UUID")
    @SecurityRequirement(name = ConsultaDocumentService.SECURITY_NAME)
    @ApiResponses(value = {
            // @ApiResponse(responseCode = "404", description = "Paràmetres incorrectes",
            // content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @ApiResponse(responseCode = "200", description = "Document UUID", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class))) })
    public Response consultaDocument(

            @Parameter(description = "UUID del document", required = true, schema = @Schema(implementation = String.class)) @QueryParam("uuid") String uuid,

            @Parameter(description = "codi de l'entitat", required = true, schema = @Schema(implementation = String.class)) @QueryParam("entitatId") String entitatId,

            @Parameter(description = "Enidoc", required = false, schema = @Schema(implementation = String.class)) @QueryParam("enidoc") String enidoc) {

        try {

            if (Configuracio.isDesenvolupament()) {
                log.info("ConsultaDocumentService INICI");
                log.info("------ Paràmetres entrada -------");
                log.info("UUID: " + ((Utils.isNotEmpty(uuid)) ? uuid : "null"));
                log.info("EntitatId: " + (Utils.isNotEmpty(entitatId) ? entitatId : "null"));
                log.info("Enidoc: " + (Utils.isNotEmpty(enidoc) ? enidoc : "null"));
                log.info("----------------------------------");
            }

            if (Utils.isEmpty(uuid))
                return generateErrorResponse(
                        "Error: no ens arriba cap UUID ni fitxer. Al manco, ens ha d'arribar un d'ells.");

            // ArxiuController pluginArxiu = new ArxiuController(Long.parseLong(entitatId));

            InterdocArxiuPlugin plugin = pluginService.getPlugin(Long.parseLong(entitatId));

            if ("true".equalsIgnoreCase(enidoc)) {

                String enidocXml = "";
                
                // XYZ ZZZ - Llevar comentaris quan s'hagui acabat de testejar la funcionalitat
                // de generar l'ENIDOC XML a partir del document.
                /*if (plugin != null){
                    enidocXml = plugin.generarEniDoc(uuid);
                    if (enidocXml == null) {
                        log.error("Error: No s'ha pogut trobat l'ENIDOC XML. Intentant generar-lo a partir del document:");*/
                        //generarEnidoc(plugin, uuid);
                    /*}
                }*/
                 enidocXml = generarEnidoc(plugin, uuid);
                
                    

                if (enidocXml != null) {

                    log.info("--------- RECUPERAR ENIDOC AMB UUID " + uuid + " -----------");
                    log.info("enidoc::xml => " + enidocXml);
                    log.info("---------------------------------------------------------------");

                    return Response.ok().type(MediaType.APPLICATION_XML).entity(enidocXml).build();
                }

            } else {

                Document doc = null;

                if (plugin != null)
                    doc = plugin.descarregarDocument(uuid);

                if (doc != null) {
                    log.info("--------- RECUPERAR DOCUMENT AMB UUID " + uuid + " -----------");
                    log.info("document::nom => " + doc.getNom());
                    log.info("document::descripcio => " + doc.getDescripcio());
                    log.info("document::estat => " + doc.getEstat());
                    log.info("---------------------------------------------------------------");

                    byte[] contingut = doc.getContingut().getContingut();

                    String headerValue = "attachment; filename=\"" + doc.getNom() + "\"";
                    return Response.ok().type(MediaType.APPLICATION_OCTET_STREAM)
                            .header(HttpHeaders.CONTENT_DISPOSITION, headerValue).entity(contingut).build();
                }
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

        private String generarEnidoc(InterdocArxiuPlugin plugin, String uuid) throws Exception {
        
            
        //Recuperacio de la referencia corresponent, 
        // en cas que fos necessaria informacio adicional    
        //ReferenciaDTO referencia = referenciaService.findByUUID(uuid).get();
        

        es.caib.pluginsib.arxiu.api.Document doc = plugin.descarregarDocument(uuid);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document xmlDoc = builder.newDocument();

        /*org.w3c.dom.Element root = xmlDoc.createElement("document");
        xmlDoc.appendChild(root);

        org.w3c.dom.Element id = xmlDoc.createElement("id");
        id.setTextContent("CONTENT_ID_1");
        root.appendChild(id);

        org.w3c.dom.Element metadata = xmlDoc.createElement("metadades");
        metadata.setTextContent(String.valueOf(doc.getMetadades()));
        root.appendChild(metadata);*/

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(xmlDoc), new StreamResult(writer));
        String xmlString = writer.toString();

        // Arrel del document ENIDOC XML
        org.w3c.dom.Element rootEnidoc = xmlDoc.createElementNS(
                "http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e", "enidoc:documento");
        rootEnidoc.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:enidoc",
                "http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e");
        rootEnidoc.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:enids",
                "http://administracionelectronica.gob.es/ENI/XSD/v1.0/firma");
        rootEnidoc.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:enidocmeta",
                "http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/metadatos");
        rootEnidoc.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:enifile",
                "http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/contenido");
        xmlDoc.appendChild(rootEnidoc);

        org.w3c.dom.Element contenido = xmlDoc.createElementNS(
                "http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/contenido", "enifile:contenido");
        contenido.setAttribute("Id", "CONTENT_ID_1");
        rootEnidoc.appendChild(contenido);

        org.w3c.dom.Element valorBinario = xmlDoc.createElement("enifile:ValorBinario");
        byte[] contingut = (doc.getContingut() != null) ? doc.getContingut().getContingut() : null;
        String contingutBase64 = (contingut != null) ? Base64.getEncoder().encodeToString(contingut) : "";
        valorBinario.setTextContent(contingutBase64);
        contenido.appendChild(valorBinario);

        org.w3c.dom.Element nombreFormato = xmlDoc.createElement("enifile:NombreFormato");
        nombreFormato.setTextContent(doc.getMetadades().getFormat().toString());
        contenido.appendChild(nombreFormato);

        

        // Metadatos
        
        org.w3c.dom.Element metadatos = xmlDoc.createElement("enidocmeta:metadatos");
        metadatos.setAttribute("Id", "METADATA_1");
        

        org.w3c.dom.Element versionNTI = xmlDoc.createElementNS(
        "http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e/metadatos",
        "enidocmeta:VersionNTI");
        versionNTI.setTextContent("http://administracionelectronica.gob.es/ENI/XSD/v1.0/documento-e");
        metadatos.appendChild(versionNTI);

        org.w3c.dom.Element identificador = xmlDoc.createElement("enidocmeta:Identificador");
        identificador.setTextContent(doc.getMetadades().getIdentificador());
        metadatos.appendChild(identificador);

        org.w3c.dom.Element organo = xmlDoc.createElement("enidocmeta:Organo");
        List<String> organs = doc.getMetadades().getOrgans();
        organo.setTextContent(!organs.isEmpty() ? organs.get(0) : "");
        metadatos.appendChild(organo);

        org.w3c.dom.Element fechaCaptura = xmlDoc.createElement("enidocmeta:FechaCaptura");
        fechaCaptura.setTextContent(doc.getMetadades().getDataCaptura().toString());
        metadatos.appendChild(fechaCaptura);

        org.w3c.dom.Element origenCiudadanoAdministracion = xmlDoc
                .createElement("enidocmeta:OrigenCiudadanoAdministracion");
        origenCiudadanoAdministracion.setTextContent(doc.getMetadades().getOrigen().toString());
        metadatos.appendChild(origenCiudadanoAdministracion);

        org.w3c.dom.Element estadoElaboracion = xmlDoc.createElement("enidocmeta:EstadoElaboracion");
        metadatos.appendChild(estadoElaboracion);

        org.w3c.dom.Element valorEstadoElaboracion = xmlDoc.createElement("enidocmeta:ValorEstadoElaboracion");
        valorEstadoElaboracion.setTextContent(doc.getMetadades().getEstatElaboracio().toString());
        estadoElaboracion.appendChild(valorEstadoElaboracion);

        org.w3c.dom.Element tipoDocumental = xmlDoc.createElement("enidocmeta:TipoDocumental");
        tipoDocumental.setTextContent(doc.getMetadades().getTipusDocumental().toString());
        metadatos.appendChild(tipoDocumental);

        // Firma
        org.w3c.dom.Element firmas = xmlDoc
                .createElementNS("http://administracionelectronica.gob.es/ENI/XSD/v1.0/firma", "enids:firmas");
        rootEnidoc.appendChild(firmas);

        org.w3c.dom.Element firma = xmlDoc.createElement("enids:firma");
        firma.setAttribute("Id", "SIGNATURE_ID_1");
        firmas.appendChild(firma);

        org.w3c.dom.Element tipoFirma = xmlDoc.createElement("enids:TipoFirma");
        tipoFirma.setTextContent(doc.getMetadades().getMetadadesAddicionals().get("TipoFirma").toString());
        firma.appendChild(tipoFirma);

        org.w3c.dom.Element contenidoFirma = xmlDoc.createElement("enids:ContenidoFirma");
        firma.appendChild(contenidoFirma);

        org.w3c.dom.Element firmaConCertificado = xmlDoc.createElement("enids:FirmaConCertificado");
        contenidoFirma.appendChild(firmaConCertificado);

        org.w3c.dom.Element referenciaFirma = xmlDoc.createElement("enids:ReferenciaFirma");
        referenciaFirma.setTextContent("#CONTENT_ID_1");
        firmaConCertificado.appendChild(referenciaFirma);

        transformer.transform(new DOMSource(xmlDoc), new StreamResult(writer));
        xmlString = writer.toString();
        return "return";
    }

}