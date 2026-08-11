package es.caib.interdoc.back.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.ejb.PluginArxiuLogicaService;
import es.caib.interdoc.plugins.arxiu.ArxiuPluginImpl;
import es.caib.interdoc.service.facade.FitxerServiceFacade;
import es.caib.interdoc.service.facade.InfoArxiuServiceFacade;
import es.caib.interdoc.service.facade.ReferenciaServiceFacade;
import es.caib.interdoc.service.facade.ReferenciaXMLServiceFacade;
import es.caib.interdoc.service.facade.UsuariEntitatServiceFacade;
import es.caib.interdoc.service.facade.UsuariServiceFacade;
import es.caib.interdoc.service.model.FitxerDTO;
import es.caib.interdoc.service.model.InfoArxiuDTO;
import es.caib.interdoc.service.model.Pagina;
import es.caib.interdoc.service.model.ReferenciaDTO;
import es.caib.interdoc.service.model.ReferenciaXMLDTO;
import es.caib.interdoc.service.model.UsuariDTO;
import es.caib.interdoc.service.model.UsuariEntitatAtribut;
import es.caib.interdoc.service.model.UsuariEntitatDTO;
import es.caib.pluginsib.arxiu.api.Document;

public class DownloadFitxerController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String DOWNLOAD_ENI_PATH = "/download/eni";
    private static final String GENERAR_ENI_DOCUMENT_PATH = "/download/generarEniDocument";

    private static final Logger LOG = LoggerFactory.getLogger(DownloadFitxerController.class);

    @EJB(mappedName = ReferenciaServiceFacade.JNDI_NAME)
    private ReferenciaServiceFacade referenciaService;

    @EJB(mappedName = InfoArxiuServiceFacade.JNDI_NAME)
    private InfoArxiuServiceFacade infoArxiuService;

    @EJB(mappedName = ReferenciaXMLServiceFacade.JNDI_NAME)
    private ReferenciaXMLServiceFacade referenciaXMLService;

    @EJB(mappedName = PluginArxiuLogicaService.JNDI_NAME)
    private PluginArxiuLogicaService pluginArxiuService;

    @EJB(mappedName = UsuariServiceFacade.JNDI_NAME)
    private UsuariServiceFacade usuariService;

    @EJB(mappedName = UsuariEntitatServiceFacade.JNDI_NAME)
    private UsuariEntitatServiceFacade usuariEntitatService;

    @EJB(mappedName = FitxerServiceFacade.JNDI_NAME)
    private FitxerServiceFacade fitxerService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getUserPrincipal() == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuari no autenticat");
            return;
        }

        Long referenciaId = parseReferenciaId(request.getParameter("referenciaId"));
        if (referenciaId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paràmetre referenciaId invàlid");
            return;
        }

        Optional<ReferenciaDTO> referenciaOpt = referenciaService.findById(referenciaId);
        if (referenciaOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No s'ha trobat la referència");
            return;
        }

        ReferenciaDTO referencia = referenciaOpt.get();
        if (!isAuthorized(request, referencia)) {
            LOG.warn("Accés no autoritzat a la descàrrega. Usuari={}, referenciaId={}, entitatId={}",
                    request.getUserPrincipal().getName(), referencia.getId(), referencia.getEntitatId());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tens permisos per descarregar aquest document");
            return;
        }

        if (referencia.getInfoArxiuId() == null || referencia.getEntitatId() == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "La referència no està associada a un document custodiat a Arxiu");
            return;
        }

        Optional<InfoArxiuDTO> infoArxiuOpt = infoArxiuService.findById(referencia.getInfoArxiuId());
        if (infoArxiuOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No s'ha trobat la informació d'Arxiu");
            return;
        }

        InfoArxiuDTO infoArxiu = infoArxiuOpt.get();
        if (Utils.isEmpty(infoArxiu.getArxiuDocumentId())) {
            LOG.warn("La referència {} no té arxiuDocumentId. Es provaran fonts locals per descarregar.", referenciaId);
        }

        try {
            ArxiuPluginImpl plugin = pluginArxiuService.getInstanceOfPlugin(referencia.getEntitatId());

            if (isGenerarEniDocumentRequest(request)) {
                String eniDoc = generarReferenciaQueryDocumentService(referenciaId, infoArxiu, plugin,
                    infoArxiu.getArxiuDocumentId(), false);
                if (Utils.isEmpty(eniDoc)) {
                    writePlainErrorResponse(response, HttpServletResponse.SC_BAD_GATEWAY,
                            "No s'ha pogut generar l'ENI Document: no hi ha dades locals i Arxiu extern no és accessible.");
                    return;
                }

                writeEniDocumentResponse(response, referenciaId, eniDoc);
                return;
            }

            if (isEniDownloadRequest(request)) {
                String eniDoc = generarReferenciaQueryDocumentService(referenciaId, infoArxiu, plugin,
                        infoArxiu.getArxiuDocumentId(), true);
                if (Utils.isEmpty(eniDoc)) {
                    writePlainErrorResponse(response, HttpServletResponse.SC_BAD_GATEWAY,
                            "No s'ha pogut descarregar l'ENI Document: no hi ha dades locals i Arxiu extern no és accessible.");
                    return;
                }

                writeEniDocumentResponse(response, referenciaId, eniDoc);
                return;
            }

            DownloadedDocument downloadedDocument = recuperarDocument(referenciaId, referencia, infoArxiu, plugin);
            if (downloadedDocument == null || downloadedDocument.getContingut() == null
                    || downloadedDocument.getContingut().length == 0) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No s'ha recuperat cap contingut del document");
                return;
            }

            byte[] contingut = downloadedDocument.getContingut();
            String mimeType = downloadedDocument.getMimeType();
            String fileName = downloadedDocument.getFileName();

            response.setContentType(Utils.isNotEmpty(mimeType) ? mimeType : "application/octet-stream");
            response.setHeader("X-Content-Type-Options", "nosniff");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + sanitizeFilename(fileName) + "\"");
            response.setContentLengthLong(contingut.length);
            response.getOutputStream().write(contingut);
            response.getOutputStream().flush();

        } catch (Exception e) {
            LOG.error("Error descarregant document de la referència {}", referenciaId, e);
            if (!response.isCommitted()) {
                String detail = Utils.isNotEmpty(e.getMessage()) ? e.getMessage() : e.getClass().getSimpleName();
                writePlainErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Error intern descarregant el document: " + detail);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private Long parseReferenciaId(String referenciaIdParam) {
        if (Utils.isEmpty(referenciaIdParam)) {
            return null;
        }
        try {
            return Long.parseLong(referenciaIdParam);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean isAuthorized(HttpServletRequest request, ReferenciaDTO referencia) {
        if (request.isUserInRole(Constants.ITD_ADMIN)) {
            return true;
        }

        if (!request.isUserInRole(Constants.ITD_USER) || referencia.getEntitatId() == null) {
            return false;
        }

        String username = request.getUserPrincipal().getName();
        Optional<UsuariDTO> usuariOpt = usuariService.findByUsername(username);
        if (usuariOpt.isEmpty() || usuariOpt.get().getUsuariId() == null) {
            return false;
        }

        Long usuariId = usuariOpt.get().getUsuariId();
        Map<UsuariEntitatAtribut, Object> filter = new HashMap<>();
        filter.put(UsuariEntitatAtribut.usuariId, usuariId);
        filter.put(UsuariEntitatAtribut.entitatId, referencia.getEntitatId());

        Pagina<UsuariEntitatDTO> pagina = usuariEntitatService.findFiltered(0, 1, filter, Collections.emptyList());
        return pagina != null && pagina.getTotal() > 0;
    }

    private boolean isEniDownloadRequest(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return requestUri != null && requestUri.endsWith(DOWNLOAD_ENI_PATH);
    }

    private boolean isGenerarEniDocumentRequest(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return requestUri != null && requestUri.endsWith(GENERAR_ENI_DOCUMENT_PATH);
    }

    private void writeEniDocumentResponse(HttpServletResponse response, Long referenciaId, String eniDoc)
            throws IOException {
        byte[] eniBytes = eniDoc.getBytes(StandardCharsets.UTF_8);
        String eniFileName = "enidoc_" + referenciaId + ".xml";

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/xml;charset=UTF-8");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + sanitizeFilename(eniFileName) + "\"");
        response.setContentLengthLong(eniBytes.length);
        response.getOutputStream().write(eniBytes);
        response.getOutputStream().flush();
    }

    private void writePlainErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write(message);
        response.getWriter().flush();
    }

    private DownloadedDocument recuperarDocument(Long referenciaId, ReferenciaDTO referencia, InfoArxiuDTO infoArxiu,
            ArxiuPluginImpl plugin) throws Exception {
        List<String> errors = new ArrayList<>();

        // Fallback local-first: evita dependència del DNS extern d'Arxiu.
        DownloadedDocument localDocument = tryGetDocumentFromFitxerLocal(referencia, errors);
        if (localDocument != null) {
            return localDocument;
        }

        DownloadedDocument originalUrlDocument = tryGetDocumentFromInfoArxiuUrl(
                (infoArxiu != null) ? infoArxiu.getOriginalFileUrl() : null,
                "originalFileUrl", referenciaId, errors);
        if (originalUrlDocument != null) {
            return originalUrlDocument;
        }

        DownloadedDocument printableUrlDocument = tryGetDocumentFromInfoArxiuUrl(
                (infoArxiu != null) ? infoArxiu.getPrintableUrl() : null,
                "printableUrl", referenciaId, errors);
        if (printableUrlDocument != null) {
            return printableUrlDocument;
        }

        DownloadedDocument pluginDocument = tryGetDocumentFromPlugin(plugin,
                (infoArxiu != null) ? infoArxiu.getArxiuDocumentId() : null, errors);
        if (pluginDocument != null) {
            return pluginDocument;
        }

        throw new Exception("No s'ha pogut descarregar el document per referenciaId=" + referenciaId
                + ". Causes: " + String.join(" | ", errors));
    }

    private DownloadedDocument tryGetDocumentFromFitxerLocal(ReferenciaDTO referencia, List<String> errors) {
        if (referencia == null || referencia.getFitxerId() == null || referencia.getFitxerId() <= 0) {
            errors.add("fitxerId no disponible");
            return null;
        }

        try {
            Optional<FitxerDTO> fitxerOpt = fitxerService.findById(referencia.getFitxerId());
            if (fitxerOpt.isEmpty()) {
                errors.add("fitxer no trobat");
                return null;
            }

            FitxerDTO fitxer = fitxerOpt.get();
            if (Utils.isEmpty(fitxer.getRuta())) {
                errors.add("fitxer.ruta buit");
                return null;
            }

            Path filePath = Paths.get(fitxer.getRuta());
            if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
                errors.add("fitxer local inexistent");
                return null;
            }

            byte[] contingut = Files.readAllBytes(filePath);
            if (contingut.length == 0) {
                errors.add("fitxer local buit");
                return null;
            }

            String mimeType = Utils.isNotEmpty(fitxer.getMime()) ? fitxer.getMime() : Files.probeContentType(filePath);
            String fileName = Utils.isNotEmpty(fitxer.getNom())
                    ? fitxer.getNom()
                    : ((filePath.getFileName() != null) ? filePath.getFileName().toString()
                            : "document-" + referencia.getId());

            return new DownloadedDocument(contingut, mimeType, fileName);
        } catch (Exception e) {
            LOG.warn("No s'ha pogut recuperar el fitxer local per referenciaId={} fitxerId={}",
                    referencia.getId(), referencia.getFitxerId(), e);
            errors.add("fitxer local: " + buildErrorDetail(e));
            return null;
        }
    }

    private DownloadedDocument tryGetDocumentFromInfoArxiuUrl(String urlText, String sourceLabel, Long referenciaId,
            List<String> errors) {
        if (Utils.isEmpty(urlText)) {
            errors.add(sourceLabel + " buit");
            return null;
        }

        try {
            UrlDownloadResult urlDownload = readBytesFromUrl(urlText);
            if (urlDownload.getContingut() == null || urlDownload.getContingut().length == 0) {
                errors.add(sourceLabel + " buit");
                return null;
            }

            String mimeType = Utils.isNotEmpty(urlDownload.getMimeType())
                    ? urlDownload.getMimeType()
                    : "application/octet-stream";
            String fileName = buildFileNameFromUrl(urlText, "document-" + referenciaId);

            return new DownloadedDocument(urlDownload.getContingut(), mimeType, fileName);
        } catch (Exception e) {
            LOG.warn("No s'ha pogut recuperar document des de {}={} per referenciaId={}",
                    sourceLabel, urlText, referenciaId, e);
            errors.add(sourceLabel + ": " + buildErrorDetail(e));
            return null;
        }
    }

    private DownloadedDocument tryGetDocumentFromPlugin(ArxiuPluginImpl plugin, String arxiuDocumentId,
            List<String> errors) {
        if (plugin == null) {
            errors.add("plugin no disponible");
            return null;
        }

        if (Utils.isEmpty(arxiuDocumentId)) {
            errors.add("arxiuDocumentId buit");
            return null;
        }

        try {
            Document document = plugin.descarregarDocument(arxiuDocumentId);
            if (document == null || document.getContingut() == null || document.getContingut().getContingut() == null) {
                errors.add("plugin.descarregarDocument sense contingut");
                return null;
            }

            byte[] contingut = document.getContingut().getContingut();
            if (contingut.length == 0) {
                errors.add("plugin.descarregarDocument buit");
                return null;
            }

            String mimeType = document.getContingut().getTipusMime();
            String fileName = Utils.isNotEmpty(document.getNom()) ? document.getNom() : "document-" + arxiuDocumentId;
            return new DownloadedDocument(contingut, mimeType, fileName);
        } catch (Exception e) {
            LOG.warn("No s'ha pogut recuperar document des del plugin per arxiuDocumentId={}", arxiuDocumentId, e);
            errors.add("plugin.descarregarDocument: " + buildErrorDetail(e));
            return null;
        }
    }

    private String generarReferenciaQueryDocumentService(Long referenciaId, InfoArxiuDTO infoArxiu,
            ArxiuPluginImpl plugin, String uuid, boolean preferRemotePlugin) throws Exception {
        List<String> errors = new ArrayList<>();

        if (preferRemotePlugin) {
            String eniFromPlugin = tryGetEniFromPlugin(plugin, uuid, errors);
            if (Utils.isNotEmpty(eniFromPlugin)) {
                return eniFromPlugin;
            }
        }

        // 1) Intentam usar l'ENI URL guardat a BD (no requereix generar res a Arxiu).
        if (infoArxiu != null && Utils.isNotEmpty(infoArxiu.getEniFileUrl())) {
            try {
                String eniFromUrl = readTextFromUrl(infoArxiu.getEniFileUrl());
                if (Utils.isNotEmpty(eniFromUrl)) {
                    return eniFromUrl;
                }
                errors.add("eniFileUrl buit");
            } catch (Exception e) {
                LOG.warn("No s'ha pogut recuperar ENI des de eniFileUrl={} per referenciaId={}",
                        infoArxiu.getEniFileUrl(), referenciaId, e);
                errors.add("eniFileUrl: " + buildErrorDetail(e));
            }
        }

        // 2) Fallback amb XML guardat a ITD_REFERENCIAXML.
        try {
            Optional<ReferenciaXMLDTO> referenciaXmlOpt = referenciaXMLService.findByReferenciaId(referenciaId);
            if (referenciaXmlOpt.isPresent() && Utils.isNotEmpty(referenciaXmlOpt.get().getResultat())) {
                return referenciaXmlOpt.get().getResultat();
            }
            errors.add("referenciaXML buit");
        } catch (Exception e) {
            LOG.warn("No s'ha pogut recuperar ReferenciaXML per referenciaId={}", referenciaId, e);
            errors.add("referenciaXML: " + buildErrorDetail(e));
        }

        if (!preferRemotePlugin) {
            String eniFromPlugin = tryGetEniFromPlugin(plugin, uuid, errors);
            if (Utils.isNotEmpty(eniFromPlugin)) {
                return eniFromPlugin;
            }
        }

        if (plugin == null) {
            errors.add("plugin no disponible");
            String msg = "No s'ha pogut generar l'ENI Document per referenciaId=" + referenciaId
                    + ". Causes: " + String.join(" | ", errors);
            throw new Exception(msg);
        }

        // Fallback equivalent a CSVQueryDocumentServiceImpl#generarEniDoc
        try {
            return generarReferencia(plugin, uuid);
        } catch (Exception e) {
            errors.add("generarEniDoc intern: " + buildErrorDetail(e));
            String msg = "No s'ha pogut generar l'ENI Document per referenciaId=" + referenciaId
                    + ". Causes: " + String.join(" | ", errors);
            throw new Exception(msg, e);
        }
    }

    private String tryGetEniFromPlugin(ArxiuPluginImpl plugin, String uuid, List<String> errors) {
        if (plugin == null) {
            errors.add("plugin no disponible");
            return null;
        }

        try {
            String eniDoc = plugin.generarEniDoc(uuid);
            if (Utils.isNotEmpty(eniDoc)) {
                return eniDoc;
            }
            errors.add("plugin.generarEniDoc buit");
            LOG.warn("No s'ha pogut recuperar l'ENI des del plugin per uuid={}", uuid);
            return null;
        } catch (Exception e) {
            LOG.warn("Error generació EniDoc des del plugin per uuid={}", uuid, e);
            errors.add("plugin.generarEniDoc: " + buildErrorDetail(e));
            return null;
        }
    }

    private String readTextFromUrl(String urlText) throws IOException {
        UrlDownloadResult urlDownload = readBytesFromUrl(urlText);
        return new String(urlDownload.getContingut(), StandardCharsets.UTF_8);
    }

    private UrlDownloadResult readBytesFromUrl(String urlText) throws IOException {
        URL url = new URL(urlText);
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(8000);

        try (InputStream stream = connection.getInputStream(); ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = stream.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
            return new UrlDownloadResult(output.toByteArray(), connection.getContentType());
        }
    }

    private String buildFileNameFromUrl(String urlText, String fallbackName) {
        try {
            String path = new URL(urlText).getPath();
            if (Utils.isNotEmpty(path)) {
                int slashIndex = path.lastIndexOf('/');
                String candidate = (slashIndex >= 0) ? path.substring(slashIndex + 1) : path;
                if (Utils.isNotEmpty(candidate)) {
                    return candidate;
                }
            }
        } catch (Exception e) {
            LOG.debug("No s'ha pogut deduir el nom de fitxer des de URL={}", urlText, e);
        }
        return fallbackName;
    }

    private String buildErrorDetail(Exception e) {
        Throwable root = e;
        while (root.getCause() != null) {
            root = root.getCause();
        }
        String message = (root.getMessage() != null) ? root.getMessage() : root.getClass().getSimpleName();
        return root.getClass().getSimpleName() + ": " + message;
    }

    // Replica la lògica de CSVQueryDocumentServiceImpl.generarEniDoc per forçar la generació local del XML.
    private String generarReferencia(ArxiuPluginImpl plugin, String uuid) throws Exception {
        es.caib.pluginsib.arxiu.api.Document doc = plugin.descarregarDocument(uuid);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document xmlDoc = builder.newDocument();

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter writer = new StringWriter();

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
        rootEnidoc.appendChild(metadatos);

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
        fechaCaptura.setTextContent(formatXmlDateTime(doc.getMetadades().getDataCaptura()));
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

        if (doc.getMetadades() != null && doc.getMetadades().getMetadadesAddicionals() != null
                && !doc.getMetadades().getMetadadesAddicionals().isEmpty()
                && doc.getMetadades().getMetadadesAddicionals().get("eni:tipoFirma") != null) {
            org.w3c.dom.Element tipoFirma = xmlDoc.createElement("enids:TipoFirma");
            tipoFirma.setTextContent(doc.getMetadades().getMetadadesAddicionals().get("eni:tipoFirma").toString());
            firma.appendChild(tipoFirma);
        }

        org.w3c.dom.Element contenidoFirma = xmlDoc.createElement("enids:ContenidoFirma");
        firma.appendChild(contenidoFirma);

        org.w3c.dom.Element firmaConCertificado = xmlDoc.createElement("enids:FirmaConCertificado");
        contenidoFirma.appendChild(firmaConCertificado);

        org.w3c.dom.Element referenciaFirma = xmlDoc.createElement("enids:ReferenciaFirma");
        referenciaFirma.setTextContent("#CONTENT_ID_1");
        firmaConCertificado.appendChild(referenciaFirma);

        transformer.transform(new DOMSource(xmlDoc), new StreamResult(writer));
        return writer.toString();
    }

    private String formatXmlDateTime(java.util.Date date) {
        if (date == null) {
            return "";
        }
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(tz);
        return sdf.format(date);
    }

    private String sanitizeFilename(String fileName) {
        return fileName.replaceAll("[\\r\\n\"]", "_");
    }

    private static final class DownloadedDocument {
        private final byte[] contingut;
        private final String mimeType;
        private final String fileName;

        private DownloadedDocument(byte[] contingut, String mimeType, String fileName) {
            this.contingut = contingut;
            this.mimeType = mimeType;
            this.fileName = fileName;
        }

        private byte[] getContingut() {
            return contingut;
        }

        private String getMimeType() {
            return mimeType;
        }

        private String getFileName() {
            return fileName;
        }
    }

    private static final class UrlDownloadResult {
        private final byte[] contingut;
        private final String mimeType;

        private UrlDownloadResult(byte[] contingut, String mimeType) {
            this.contingut = contingut;
            this.mimeType = mimeType;
        }

        private byte[] getContingut() {
            return contingut;
        }

        private String getMimeType() {
            return mimeType;
        }
    }
}
