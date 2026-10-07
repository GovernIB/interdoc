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

@Stateless(name = CSVQueryDocumentServiceImpl2.NAME2 + "Ejb")
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
        name = CSVQueryDocumentServiceImpl2.NAME_WS2,
        portName = CSVQueryDocumentServiceImpl2.NAME_WS2,
        serviceName = CSVQueryDocumentServiceImpl2.NAME_WS2 + "Service")
@WebContext(
        urlPattern = "/CSVQueryDocumentWsService/" + CSVQueryDocumentServiceImpl.NAME_WS,
        transportGuarantee = TransportGuarantee.NONE,
        secureWSDLAccess = false)
        
public class CSVQueryDocumentServiceImpl2 extends CSVQueryDocumentServiceImpl implements CSVQueryDocumentService2 {

    public static final String NAME2 = "CSVQueryDocument2";

    public static final String NAME_WS2 = NAME2 + "Ws";

}
