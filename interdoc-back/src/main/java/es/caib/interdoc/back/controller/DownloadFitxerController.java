package es.caib.interdoc.back.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.ejb.PluginArxiuLogicaService;
import es.caib.interdoc.plugins.arxiu.ArxiuPluginImpl;
import es.caib.interdoc.service.facade.InfoArxiuServiceFacade;
import es.caib.interdoc.service.facade.ReferenciaServiceFacade;
import es.caib.interdoc.service.facade.UsuariEntitatServiceFacade;
import es.caib.interdoc.service.facade.UsuariServiceFacade;
import es.caib.interdoc.service.model.InfoArxiuDTO;
import es.caib.interdoc.service.model.Pagina;
import es.caib.interdoc.service.model.ReferenciaDTO;
import es.caib.interdoc.service.model.UsuariDTO;
import es.caib.interdoc.service.model.UsuariEntitatAtribut;
import es.caib.interdoc.service.model.UsuariEntitatDTO;
import es.caib.pluginsib.arxiu.api.Document;

public class DownloadFitxerController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(DownloadFitxerController.class);

    @EJB(mappedName = ReferenciaServiceFacade.JNDI_NAME)
    private ReferenciaServiceFacade referenciaService;

    @EJB(mappedName = InfoArxiuServiceFacade.JNDI_NAME)
    private InfoArxiuServiceFacade infoArxiuService;

    @EJB(mappedName = PluginArxiuLogicaService.JNDI_NAME)
    private PluginArxiuLogicaService pluginArxiuService;

    @EJB(mappedName = UsuariServiceFacade.JNDI_NAME)
    private UsuariServiceFacade usuariService;

    @EJB(mappedName = UsuariEntitatServiceFacade.JNDI_NAME)
    private UsuariEntitatServiceFacade usuariEntitatService;

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
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "La referència no té identificador de document a Arxiu");
            return;
        }

        try {
            ArxiuPluginImpl plugin = pluginArxiuService.getInstanceOfPlugin(referencia.getEntitatId());
            if (plugin == null) {
                LOG.error("No s'ha pogut instanciar el plugin d'Arxiu per l'entitat {}", referencia.getEntitatId());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "No s'ha pogut inicialitzar el plugin d'Arxiu");
                return;
            }

            Document document = plugin.descarregarDocument(infoArxiu.getArxiuDocumentId());
            if (document == null || document.getContingut() == null || document.getContingut().getContingut() == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No s'ha recuperat cap contingut del document");
                return;
            }

            byte[] contingut = document.getContingut().getContingut();
            String mimeType = document.getContingut().getTipusMime();
            String fileName = Utils.isNotEmpty(document.getNom()) ? document.getNom() : "document-" + referenciaId;

            response.setContentType(Utils.isNotEmpty(mimeType) ? mimeType : "application/octet-stream");
            response.setHeader("X-Content-Type-Options", "nosniff");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + sanitizeFilename(fileName) + "\"");
            response.setContentLengthLong(contingut.length);
            response.getOutputStream().write(contingut);
            response.getOutputStream().flush();

        } catch (Exception e) {
            LOG.error("Error descarregant document de la referència {}", referenciaId, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error intern descarregant el document");
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

    private String sanitizeFilename(String fileName) {
        return fileName.replaceAll("[\\r\\n\"]", "_");
    }
}
