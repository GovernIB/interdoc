package es.caib.interdoc.api.interna.ws.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;
import javax.activation.DataHandler;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.mail.util.ByteArrayDataSource;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.security.SecurityContext;
import org.jboss.ws.api.annotation.TransportGuarantee;
import org.jboss.ws.api.annotation.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.ejb.PluginArxiuLogicaService;
import es.caib.interdoc.ejb.utils.XmlGenerator;
import es.caib.interdoc.plugins.arxiu.ArxiuPluginImpl;
import es.caib.interdoc.service.facade.AccesServiceFacade;
import es.caib.interdoc.service.facade.InfoArxiuServiceFacade;
import es.caib.interdoc.service.facade.ReferenciaServiceFacade;
import es.caib.interdoc.service.model.AccesDTO;
import es.caib.interdoc.service.model.InfoArxiuDTO;
import es.caib.interdoc.service.model.ReferenciaDTO;
import es.caib.interdoc.api.interna.ws.model.CSVQueryDocumentResponse;
import es.caib.interdoc.api.interna.ws.model.CSVQueryDocumentSecurityWSS;
import es.caib.interdoc.api.interna.ws.model.UeryDocumentSecurityRequest;
import es.caib.interdoc.api.interna.ws.resposta.mtom.CSVQueryDocumentMtomSecurityResponse;
import es.caib.interdoc.api.interna.ws.resposta.mtom.ContenidoMtomInfo;
import es.caib.interdoc.api.interna.ws.resposta.mtom.DocumentoMtomResponse;

/**
 * 
 * Servei web per descarregar documents
 * @author jagarcia
 */

@Stateless(name = CSVQueryDocumentServiceImpl.NAME + "Ejb")
@PermitAll
@SOAPBinding(
        style = SOAPBinding.Style.DOCUMENT,
        parameterStyle = SOAPBinding.ParameterStyle.BARE,
        use = SOAPBinding.Use.LITERAL)
@org.apache.cxf.interceptor.InInterceptors(
        interceptors = { "es.caib.interdoc.api.interna.ws.utilitats.WSSecurityInterceptor" })
@org.apache.cxf.interceptor.InFaultInterceptors(
        interceptors = { "es.caib.interdoc.api.interna.ws.utilitats.WsOutInterceptor" })
@WebService(
        name = CSVQueryDocumentServiceImpl.NAME_WS,
        portName = CSVQueryDocumentServiceImpl.NAME_WS,
        serviceName = CSVQueryDocumentServiceImpl.NAME_WS + "Service")
@WebContext(
        urlPattern = "/protected/" + CSVQueryDocumentServiceImpl.NAME_WS,
        transportGuarantee = TransportGuarantee.NONE,
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
    protected AccesServiceFacade accesService;

    @EJB(mappedName = PluginArxiuLogicaService.JNDI_NAME)
    private PluginArxiuLogicaService pluginArxiuService;

    @WebMethod
    @PermitAll
    @Override
    public CSVQueryDocumentResponse csvQueryDocument(@WebParam(name = "csvQueryDocumentSecurityWSS")
    CSVQueryDocumentSecurityWSS csvQueryDocumentSecurityWSS) throws Exception {

        return generateCSVQueryDocumentErrorResponse("200", "Utilitzau el servei securitzat csvQueryDocumentSecurity.");
    }

    @WebMethod
    @PermitAll
    @Override
    public CSVQueryDocumentMtomSecurityResponse csvQueryDocumentSecurity(@WebParam(name = "csvQueryDocumentSecurityWSS")
    CSVQueryDocumentSecurityWSS csvQueryDocumentSecurityWSS) throws Exception {

        try {

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

            Optional<ReferenciaDTO> referenciaDto = null;
            String resultatArxiu = null;

            Long entitatId = 1L;

            if (Utils.isNotEmpty(idEni)) {
                // Es tracta d'un fitxer pujat previament a Arxiu
                LOG.info("Recuperam el fitxer a partir de UUId: " + idEni);

                referenciaDto = referenciaService.findByUUID(idEni);

                ReferenciaDTO ref = referenciaDto.get();

                if (ref != null && Configuracio.isDesenvolupament()) {
                    LOG.info("ref.id =>" + ref.getId());
                    LOG.info("ref.Receptor =>" + ref.getReceptor());
                    LOG.info("ref.Emisor =>" + ref.getEmisor());
                    LOG.info("ref.CsvId =>" + ref.getCsvId());
                    LOG.info("ref.UUid =>" + ref.getUuId());
                    LOG.info("ref.fitxerId =>" + ref.getFitxerId());
                    LOG.info("ref.infoSignaturaId =>" + ref.getInfoSignaturaId());
                    LOG.info("ref.infoArxiuId => " + ref.getInfoArxiuId());
                    LOG.info("ref.entitatId => " + ref.getEntitatId());
                }

                if (ref == null || ref.getEntitatId() < 1) {
                    return generateCSVQueryDocumentSecurityMtomErrorResponse("200",
                            "No existeix cap referencia amb el UUID indicat");
                }
                entitatId = ref.getEntitatId();
                                
                ArxiuPluginImpl plugin = pluginArxiuService.getInstanceOfPlugin(entitatId);
                                
                
                try {
                    if(Utils.isXadesFormat(ref.getFormatFirma())) {
                        LOG.info("Generació EniDoc a partir del ID: " + idEni);
                        LOG.info("-- idEni: " + idEni);
                        LOG.info("-- FormatFirma: "+ref.getFormatFirma());
                        resultatArxiu = XmlGenerator.generarEniDocXades(plugin, ref.getUuId());
                        LOG.info("===== ENIDOC GENERAT ===== ");
                        LOG.info(resultatArxiu);
                        LOG.info("===== FINAL ENIDOC GENERAT ===== ");
                    }else{
                        resultatArxiu = plugin.generarEniDoc(idEni);

                    }
                } catch (Exception e) {
                    LOG.error("Error generació EniDoc. Generant EniDoc internament.");
                    // Generacio de EniDoc
                    if (Utils.isXadesFormat(ref.getFormatFirma())) {
                        resultatArxiu = XmlGenerator.generarEniDocXades(plugin, idEni);
                    } else {
                        resultatArxiu = XmlGenerator.generarEniDocXades(plugin, idEni);
                    }
                }
                
                // Si document_eni => RETORNAM EL PDF TODO
                if (isDescarregaPDF) {
                    LOG.info("isDescarregaPDF => true");
                }

            } else if (Utils.isNotEmpty(csvId)) {

                // Es tracta d'un fitxer amb CSV, pujat previament per Interdoc
                LOG.info("Recuperam el fitxer a partir del CSVID: " + csvId);

                referenciaDto = referenciaService.findByCSV(csvId);

                ReferenciaDTO ref = referenciaDto.get();

                if (ref != null && Configuracio.isDesenvolupament()) {
                    LOG.info("ref.id =>" + ref.getId());
                    LOG.info("ref.Receptor =>" + ref.getReceptor());
                    LOG.info("ref.Emisor =>" + ref.getEmisor());
                    LOG.info("ref.CsvId =>" + ref.getCsvId());
                    LOG.info("ref.UUid =>" + ref.getUuId());
                    LOG.info("ref.fitxerId =>" + ref.getFitxerId());
                    LOG.info("ref.infoSignaturaId =>" + ref.getInfoSignaturaId());
                    LOG.info("ref.infoArxiuId => " + ref.getInfoArxiuId());
                    LOG.info("ref.entitatId => " + ref.getEntitatId());
                }

                if (ref != null && ref.getEntitatId() > 0) {
                    entitatId = ref.getEntitatId();
                }

                if (ref.getInfoArxiuId() != null && ref.getInfoArxiuId() > 0) {

                    Optional<InfoArxiuDTO> arxiu = infoArxiuService.findById(ref.getInfoArxiuId());

                    InfoArxiuDTO infoArxiu = null;

                    if (arxiu.isPresent()) {
                        infoArxiu = arxiu.get();

                        if (Configuracio.isDesenvolupament()) {
                            LOG.info("InforArxiu => " + infoArxiu.toString());
                            LOG.info("infoArxiu.getArxiuDocumentId => " + infoArxiu.getArxiuDocumentId());
                            LOG.info("infoArxiu.getEniFileUrl => " + infoArxiu.getEniFileUrl());
                            LOG.info("infoArxiu.getOriginalFileUrl => " + infoArxiu.getOriginalFileUrl());
                        }

                        ArxiuPluginImpl plugin = pluginArxiuService.getInstanceOfPlugin(entitatId);

                        // Generam el ENIDOC
                        try {
                    if(Utils.isXadesFormat(ref.getFormatFirma())) {
                        LOG.info("Generació EniDoc a partir del ID: " + infoArxiu.getArxiuDocumentId());
                        LOG.info("-- idEni: NULL");
                        LOG.info("-- FormatFirma: "+ref.getFormatFirma());
                        resultatArxiu = XmlGenerator.generarEniDocXades(plugin, infoArxiu.getArxiuDocumentId());
                        LOG.info("===== ENIDOC GENERAT ===== ");
                        LOG.info(resultatArxiu);
                        LOG.info("===== FINAL ENIDOC GENERAT ===== ");
                    }else{
                        resultatArxiu = plugin.generarEniDoc(infoArxiu.getArxiuDocumentId());

                    }
                } catch (Exception e) {
                    LOG.error("Error generació EniDoc. Generant EniDoc internament.");
                    // Generacio de EniDoc
                    if (Utils.isXadesFormat(ref.getFormatFirma())) {
                        resultatArxiu = XmlGenerator.generarEniDocXades(plugin, ref.getUuId());
                    } else {
                        resultatArxiu = XmlGenerator.generarEniDocXades(plugin, ref.getUuId());
                    }
                }

                        
                        // Si document_eni => RETORNAM EL PDF
                        if (isDescarregaPDF && infoArxiu.getOriginalFileUrl() != null) {
                            LOG.info("isDescarregaPDF => true");
                            byte[] filePDF = downloadUrl(new URL(infoArxiu.getOriginalFileUrl()));
                        }

                    } else {
                        return generateCSVQueryDocumentSecurityMtomErrorResponse("200",
                                "No existeix cap referencia amb les dades indicades a l'arxiu");
                    }

                }

            } else {
                // Error
                LOG.info("Error: EnidId i CSVId són nuls.");
                return generateCSVQueryDocumentSecurityMtomErrorResponse("400",
                        "Es necessari introduir un CSV o un UUID.");
            }

            if (Utils.isEmpty(idEni) && (referenciaDto == null || referenciaDto.isEmpty())) {
                return generateCSVQueryDocumentSecurityMtomErrorResponse("200",
                        "No existeix cap referencia amb les dades indicades");
            }

            // Enregistrar l'accés si disposam de la informació
            if (referenciaDto.isPresent() && ueriRequest.getTipoIdentificacion() != null) {
                try {
                    AccesDTO acces = new AccesDTO();

                    if (referenciaDto.isPresent()) {
                        ReferenciaDTO ref = referenciaDto.get();
                        acces.setReferenciaId(ref.getId());
                    }

                    if (ueriRequest.getNif() != null)
                        acces.setIdentificacio(ueriRequest.getNif());

                    if (ueriRequest.getIp() != null)
                        acces.setIp(ueriRequest.getIp());
                    else
                        acces.setIp("0.0.0.0");

                    if (ueriRequest.getTipoIdentificacion() != null)
                        acces.setTipusIdentificacio(ueriRequest.getTipoIdentificacion().value());

                    acces.setDataCreacio(LocalDate.now());

                    accesService.create(acces);

                } catch (Exception e) {
                    LOG.error("Error enregistrament acces => " + e.getMessage());
                    e.printStackTrace();
                }
            } else {

                // Agafam el nom del usuari que ha fet la petició
                Message message = PhaseInterceptorChain.getCurrentMessage();
                SecurityContext context = message.get(SecurityContext.class);
                Principal principal2 = context.getUserPrincipal();
                if (principal2 != null) {
                    LOG.info("principal2 => " + principal2.getName());

                    // obtener todo de message.getContextualPropertyKeys()
                    for (String key : message.getContextualPropertyKeys()) {
                        LOG.info("key => " + key);
                    }
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

            LOG.info("CSV QUERY DOCUMENT SERVICE: Retorna resultat: ");
            LOG.info(response.toString());
            return response;
        } catch (Exception e) {
            final String csv = csvQueryDocumentSecurityWSS.getQueryDocumentSecurityRequest().getCsv();
            String msg = "Error en la consulta del documento [" + csv + "]: " + e.getMessage();
            LOG.error(msg, e);
            return generateCSVQueryDocumentSecurityMtomErrorResponse("500", "Error en la consulta del documento.");
        }
    }

    private CSVQueryDocumentResponse generateCSVQueryDocumentErrorResponse(String codigo, String descripcion)
            throws Exception {

        CSVQueryDocumentResponse response = new CSVQueryDocumentResponse();
        response.setCode(codigo);
        response.setDescription(descripcion);
        response.setDocumentResponse(null);
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
    
}
