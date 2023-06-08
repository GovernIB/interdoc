package es.caib.interdoc.ws.impl;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.commons.utils.MarshallUtil;
import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.plugins.arxiu.ArxiuController;
import es.caib.interdoc.plugins.arxiu.DocumentInfo;
import es.caib.interdoc.plugins.arxiu.Origen;
import es.caib.interdoc.service.facade.FitxerServiceFacade;
import es.caib.interdoc.service.facade.ReferenciaServiceFacade;
import es.caib.interdoc.service.facade.ReferenciaXMLServiceFacade;
import es.caib.interdoc.service.model.FitxerDTO;
import es.caib.interdoc.service.model.ReferenciaDTO;
import es.caib.interdoc.service.model.ReferenciaXMLDTO;
import es.caib.interdoc.plugins.arxiu.Fitxer;
import es.caib.interdoc.ws.model.ObtenerReferenciaRequestInfo;
import es.caib.interdoc.ws.resposta.EmisorBean;
import es.caib.interdoc.ws.resposta.IdentificadorBean;
import es.caib.interdoc.ws.resposta.MetadatosBean;
import es.caib.interdoc.ws.resposta.PermisoBean;
import es.caib.interdoc.ws.resposta.ReceptorBean;
import es.caib.interdoc.ws.resposta.MetadatoBean;
import es.caib.interdoc.ws.resposta.ReferenciaDocumentoBean;
import es.caib.interdoc.ws.utils.Metadada;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.WebServiceContext;

// @RunAs(Constants.ITD_ADMIN)
@Stateless(name = ObtenerReferenciaWsImpl.NAME + "Ejb")
@RolesAllowed({ Constants.ITD_WS, Constants.ITD_ADMIN })
@SOAPBinding(style = SOAPBinding.Style.RPC)
@org.apache.cxf.interceptor.InInterceptors(interceptors = { "es.caib.interdoc.ws.utilitats.WsInInterceptor" })
@org.apache.cxf.interceptor.InFaultInterceptors(interceptors = { "es.caib.interdoc.ws.utilitats.WsOutInterceptor" })
@WebService(name = ObtenerReferenciaWsImpl.NAME_WS, portName = ObtenerReferenciaWsImpl.NAME_WS, serviceName = ObtenerReferenciaWsImpl.NAME_WS
		+ "Service")
public class ObtenerReferenciaWsImpl implements ObtenerReferenciaWs {

	private static final Logger log = LoggerFactory.getLogger(ObtenerReferenciaWsImpl.class);

	public static final String NAME = "ObtenerReferencia";

	public static final String NAME_WS = NAME + "Ws";

	public static final String URL_DIRECCION = "http://192.168.35.90:8080/interdoc-ws-server/CSVQueryDocumentServiceWsService/CSVQueryDocumentServiceWs";

	public static final String URL_DESCARGA = URL_DIRECCION + "?wsdl";

	private static boolean isTest = true;
	
	@EJB
	protected FitxerServiceFacade fitxerService; 

	@EJB
	protected ReferenciaServiceFacade referenciaService;

	@EJB
	protected ReferenciaXMLServiceFacade referenciaXMLService;

	@Resource
	private WebServiceContext wsContext;

	@WebMethod
	@Override
	@PermitAll
	public String getVersionWs() {
		log.info("ObtenerReferenciaWsImpl:getVersionWs");
		return "1.0";
	}

	@WebMethod
	@PermitAll
	@Override
	public String creaReferencia(
			@WebParam(name = "obtenerReferenciaRequest") ObtenerReferenciaRequestInfo obtenerReferenciaRequestInfo) {

		try {

			log.info("ObtenerReferenciaWsImpl:creaReferencia INICI");

			log.info("------ Parametres entrada -------");
			log.info("csv: " + ((obtenerReferenciaRequestInfo.getCsv() != null) ? obtenerReferenciaRequestInfo.getCsv()
					: "null"));
			log.info("uuid: "
					+ ((obtenerReferenciaRequestInfo.getUuid() != null) ? obtenerReferenciaRequestInfo.getUuid()
							: "null"));
			log.info("document: " + ((obtenerReferenciaRequestInfo.getDocument() != null)
					? obtenerReferenciaRequestInfo.getDocument().getNom() + ", size: "
							+ obtenerReferenciaRequestInfo.getDocument().getTamany()
					: "null"));
			log.info("document tamany length: " + ((obtenerReferenciaRequestInfo.getDocument() != null)
					? obtenerReferenciaRequestInfo.getDocument().getData().length : "null"));
			log.info("metadades: " + ((obtenerReferenciaRequestInfo.getMetadades() != null)
					? obtenerReferenciaRequestInfo.getMetadades().size()
					: "null"));
			log.info("aplicacioId: " + ((obtenerReferenciaRequestInfo.getAplicacioId() != null)
					? obtenerReferenciaRequestInfo.getAplicacioId()
					: "null"));
			log.info("emisor: "
					+ ((obtenerReferenciaRequestInfo.getEmisor() != null) ? obtenerReferenciaRequestInfo.getEmisor()
							: "null"));
			log.info("receptor: "
					+ ((obtenerReferenciaRequestInfo.getReceptor() != null) ? obtenerReferenciaRequestInfo.getReceptor()
							: "null"));
			log.info("interessats Size: " + ((obtenerReferenciaRequestInfo.getInteressats() != null) ? obtenerReferenciaRequestInfo.getInteressats().size() : 0));
			log.info("----------------------------------");

			if (Utils.isEmpty(obtenerReferenciaRequestInfo.getCsv())
					&& Utils.isEmpty(obtenerReferenciaRequestInfo.getUuid())
					&& (obtenerReferenciaRequestInfo.getDocument() != null && obtenerReferenciaRequestInfo.getDocument().getData().length < 1)
				) {

				// TODO thrown exception
				return "Error: el camp CSV, UUID o fitxer binary és obligatori";
			}

			boolean isEnidoc = Utils.isNotEmpty(obtenerReferenciaRequestInfo.getUuid());
			boolean isSigned = false;
			boolean isInArxiu = false;

			log.info("isEnidoc:" + ((isEnidoc) ? "true" : "false"));

			if (!isEnidoc) {
				// Si no és un fitxer ENIDOC, s'ha de firmar i pujar a l'arxiu
				
				
				/* INICI GUARDAR TEMPORAL A FILESYSTEM */
				Long fitxerId = null;
				
				if (obtenerReferenciaRequestInfo.getDocument() != null) {
					// Guardar al filesystem
					
					Config config = ConfigProvider.getConfig();
					String filePath = config.getValue("es.caib.interdoc.filesdirectory", String.class);
					log.info("Filepath temporary files => " + filePath);
					
					String fileName = "";
					if (obtenerReferenciaRequestInfo.getDocument().getData().length > 0) {
						fileName = String.valueOf(System.currentTimeMillis()) + ((obtenerReferenciaRequestInfo.getDocument().getNom() != null) ? obtenerReferenciaRequestInfo.getDocument().getNom() : "");
						String tempName = filePath + "//" + fileName;
						File file = new File(tempName); 
						OutputStream out = new FileOutputStream(file); 
						out.write(obtenerReferenciaRequestInfo.getDocument().getData());
						out.close(); 
						log.info("Save file in: " + tempName);
					}else {
						log.info("file.getData().length = 0");
					}
					
					if (fileName != "") {
						FitxerDTO fitxerDto = new FitxerDTO();
						fitxerDto.setNom(obtenerReferenciaRequestInfo.getDocument().getNom());
						fitxerDto.setDescripcio(obtenerReferenciaRequestInfo.getDocument().getDescripcio());
						fitxerDto.setTamany(obtenerReferenciaRequestInfo.getDocument().getTamany());
						fitxerDto.setMime(obtenerReferenciaRequestInfo.getDocument().getMime());
						fitxerId = fitxerService.create(fitxerDto);
						log.info("FitxerId => " + fitxerId);
					}else {
						log.info("filename is empty");
					}
				}else {
					log.info("obtenerReferenciaRequestInfo.getDocument() is null");
				}
				
				/* FI GUARDAR TEMPORAL A FILESYSTEM */

				
				/* INICI FIRMA EN SERVIDOR */
				isSigned = true;
				/* FI FIRMA EN SERVIDOR */

				
				/* INICI PUJADA ARXIU */
				if (isSigned && obtenerReferenciaRequestInfo.getDocument() != null) {
					log.info("Inici pujada arxiu");

					Map<String, Object> metadadesInfoObj = null;
					if (obtenerReferenciaRequestInfo.getMetadades() != null) {
						final int numMetadades = obtenerReferenciaRequestInfo.getMetadades().size();
						List<Metadada> metadadesInfo = obtenerReferenciaRequestInfo.getMetadades();
						metadadesInfoObj = new HashMap<>(numMetadades);
						if (numMetadades > 0) {
							for (Metadada meta : metadadesInfo) {
								metadadesInfoObj.put(meta.getClau(), meta.getValor());
							}
						}
					}
					
					Fitxer fitxerInfo = new Fitxer();
					fitxerInfo.setArxiuNom(obtenerReferenciaRequestInfo.getDocument().getNom());
					fitxerInfo.setContingut(obtenerReferenciaRequestInfo.getDocument().getData());
					fitxerInfo.setTamany(obtenerReferenciaRequestInfo.getDocument().getTamany());
					fitxerInfo.setTipusMime(obtenerReferenciaRequestInfo.getDocument().getMime());
					fitxerInfo.setExtensio(null);

					log.info("Fitxer arxiu nom => " + fitxerInfo.getArxiuNom());
					log.info("Fitxer arxiu tamany => " + fitxerInfo.getTamany());
					log.info("Fitxer arxiu mime => " + fitxerInfo.getTipusMime());

					DocumentInfo documentInfo = new DocumentInfo();
					documentInfo.setNom("prova_exp_" + System.currentTimeMillis());
					documentInfo.setOrgans(Arrays.asList(obtenerReferenciaRequestInfo.getEmisor()));
					documentInfo.setInteressats(obtenerReferenciaRequestInfo.getInteressats());
					documentInfo.setMetadades(metadadesInfoObj);
					documentInfo.setOrigen(Origen.ADMINISTRACIO);
					documentInfo.setFitxer(fitxerInfo);

					log.info("documentInfo to Arxiu => " + documentInfo.toString());
					
					try {
						ArxiuController arxiu = new ArxiuController();
						String resultatArxiu = arxiu.crearExpedient(documentInfo);
						
						log.info("Resultat arxiu => " + resultatArxiu);
					}catch(Exception e) {
						log.error("Error pujada arxiu => " + e.getMessage());
						e.printStackTrace();
					}
					

					// GUARDAM expedientId i estatExpedientId -  InfoArxiu

					isInArxiu = true;

					/* FI PUJADA ARXIU */

				} else {
					log.info("Error: El fitxer no está firmat i no es pot pujar a Arxiu");
				}
			}

			if (isEnidoc || (isSigned && isInArxiu)) {

				// Generate Referencia
				String referencia = createReferencia();
				log.info("Referencia generada: " + referencia);

				// Generate Hash
				String hash = "";
				if (obtenerReferenciaRequestInfo.getDocument() != null) {
					es.caib.interdoc.ws.utils.Fitxer fitxer = obtenerReferenciaRequestInfo.getDocument();
					if (fitxer != null) {
						log.info("fitxer no és nul. Es genera el hash");
						MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
						hash = getFileChecksum(shaDigest, fitxer.getData());
					}else {
						log.info("No es pot general el hash perquè es NULL");
					}
				}

				// Guardar els resultats a DB
				ReferenciaDTO nuevaReferenciaDto = new ReferenciaDTO();
				nuevaReferenciaDto.setCsvId(Utils.isNotEmpty(obtenerReferenciaRequestInfo.getCsv()) ? obtenerReferenciaRequestInfo.getCsv() : "");
				nuevaReferenciaDto.setDataCreacio(LocalDate.now());
				nuevaReferenciaDto.setDireccio(URL_DESCARGA);
				nuevaReferenciaDto.setEmisor(Utils.isNotEmpty(obtenerReferenciaRequestInfo.getEmisor()) ? obtenerReferenciaRequestInfo.getEmisor() : "");
				nuevaReferenciaDto.setFormatFirma(1L); // TODO
				nuevaReferenciaDto.setHash(Utils.isNotEmpty(hash) ? hash : "");
				nuevaReferenciaDto.setReceptor(Utils.isNotEmpty(obtenerReferenciaRequestInfo.getReceptor()) ? obtenerReferenciaRequestInfo.getReceptor() : "");
				nuevaReferenciaDto.setUrlVisible(URL_DIRECCION); 
				nuevaReferenciaDto.setUuId(Utils.isNotEmpty(obtenerReferenciaRequestInfo.getUuid()) ? obtenerReferenciaRequestInfo.getUuid() : "");
				nuevaReferenciaDto.setReferencia(referencia);
				nuevaReferenciaDto.setId(null);
				Long referenciaDB = referenciaService.create(nuevaReferenciaDto);
				log.info("ReferenciaDB => " + String.valueOf(referenciaDB));

				// Create resposta XML
				String respostaXML = generateXMLResponse(obtenerReferenciaRequestInfo, referencia, hash);

				// Guardar XML Resposta
				ReferenciaXMLDTO referenciaXMLDto = new ReferenciaXMLDTO();
				referenciaXMLDto.setReferenciaId(referenciaDB);
				referenciaXMLDto.setResultat(respostaXML);
				referenciaXMLDto.setDataCreacio(LocalDate.now());
				referenciaXMLDto.setId(null);
				referenciaXMLService.create(referenciaXMLDto);

				log.info("-------------------  INICI RESPOSTA XML ------------------------");
				log.info(respostaXML);
				log.info("-------------------  FI RESPOSTA XML ------------------------");

				return respostaXML;
			}

			log.info("ObtenerReferenciaWsImpl FI");
			return null;

		} catch (Exception e) {
			log.error("Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	/*
	 * private expedientTancar( final String identificador) throws ArxiuException {
	 * String metode = Servicios.CLOSE_FILE; try {
	 * getArxiuClient().generarEnviarPeticio( metode, CloseFile.class, new
	 * GeneradorParam<ParamNodeId>() {
	 * 
	 * @Override public ParamNodeId generar() { ParamNodeId param = new
	 * ParamNodeId(); param.setNodeId(identificador); return param; } },
	 * ParamNodeId.class, CloseFileResult.class); } catch (ArxiuException aex) {
	 * throw aex; } catch (Exception ex) { throw new ArxiuException(
	 * "S'ha produit un error cridant el mètode " + metode, ex); } return null; }
	 */

	@WebMethod
	@Override
	@PermitAll
	public String echo(@WebParam(name = "echo") String echo) {
		log.info("ObtenerReferenciaWsImpl :: echo = " + echo);
		return "USER: " + wsContext.getUserPrincipal().getName() + " | ECHO: " + echo;
	}

	private String createReferencia() {
		try {
			HashCreator nouHash = new HashCreator();
			return nouHash.createPasswordHashWithSalt(String.valueOf(System.currentTimeMillis()));
		} catch (Exception e) {
			log.error("No s'ha pogut crear la referencia");
			return null;
		}
	}

	private String generateXMLResponse(ObtenerReferenciaRequestInfo obtenerReferenciaRequestInfo, String referencia,
			String hash) {

		try {

			ReferenciaDocumentoBean referenciaDocumentoBean = new ReferenciaDocumentoBean();

			referenciaDocumentoBean.setDireccion(URL_DESCARGA);

			if (Utils.isNotEmpty(obtenerReferenciaRequestInfo.getEmisor())) {
				EmisorBean emisorBean = new EmisorBean();
				emisorBean.setOrganismo(obtenerReferenciaRequestInfo.getEmisor());
				referenciaDocumentoBean.setEmisorBean(emisorBean);
			}
			
			if (Utils.isNotEmpty(hash)) {
				referenciaDocumentoBean.setHash(hash);
			}
			
			IdentificadorBean identificadorBean = new IdentificadorBean();
			if(Utils.isNotEmpty(obtenerReferenciaRequestInfo.getCsv())) {
				identificadorBean.setValorCSV(obtenerReferenciaRequestInfo.getCsv());
			}else if (Utils.isNotEmpty(obtenerReferenciaRequestInfo.getUuid())) {
				identificadorBean.setSecuenciaIdentificador(obtenerReferenciaRequestInfo.getUuid());
			}else {
				identificadorBean.setSecuenciaIdentificador(referencia);	
			}
			referenciaDocumentoBean.setIdentificadorBean(identificadorBean);

			if (obtenerReferenciaRequestInfo.getMetadades() != null) {
				final int numMetadades = obtenerReferenciaRequestInfo.getMetadades().size();
				if (numMetadades > 0) {

					MetadatosBean metadatosBean = new MetadatosBean();
					List<MetadatoBean> metadatos = new ArrayList<MetadatoBean>(numMetadades);

					List<Metadada> metadadesInfo = new ArrayList<Metadada>(numMetadades);
					metadadesInfo = obtenerReferenciaRequestInfo.getMetadades();

					for (Metadada item : metadadesInfo) {
						MetadatoBean metadatoBean = new MetadatoBean();
						metadatoBean.setNombre(item.getClau());
						metadatoBean.setValor(item.getValor());
						metadatos.add(metadatoBean);
					}
					metadatosBean.setMetadatoBeanList(metadatos);
					referenciaDocumentoBean.setMetadatosBean(metadatosBean);
				}
			}
				
			// TODO permisos restringidos por usuario/aplicacion
			PermisoBean permisoBean = new PermisoBean();
			permisoBean.setPublico(Constants.PUBLICO);
			referenciaDocumentoBean.setPermisoBean(permisoBean);

			if(Utils.isNotEmpty(obtenerReferenciaRequestInfo.getReceptor())){
				ReceptorBean receptorBean = new ReceptorBean();
				receptorBean.setOrganismo(obtenerReferenciaRequestInfo.getReceptor());
				referenciaDocumentoBean.setReceptorBean(receptorBean);
			}
			
			/*
			 * TODO eEMGDE.Firma.TipoFirma.FormatoFirma TF01 - CSV TF02 - XAdES internally
			 * detached signature TF03 - XAdES enveloped signature TF04 - CAdES
			 * detached/explicit signature TF05 - CAdES attached/implicit signature TF06 -
			 * PadES TF07 - XAdES manifest
			 * 
			 */
			// TODO referenciaDocumentoBean.setFormatoFirma("TF06");

			// TODO trazabilidad

			String sw = MarshallUtil.generateXML(ReferenciaDocumentoBean.class, referenciaDocumentoBean);
			log.info(sw);
			return sw;

		} catch (Exception e) {
			log.error("EXCEPTION" + e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	private static String getFileChecksum(MessageDigest digest, byte[] data) throws IOException {

		ByteArrayInputStream bis = new ByteArrayInputStream(data);

		// Create byte array to read data in chunks
		byte[] byteArray = new byte[1024];
		int bytesCount = 0;

		while ((bytesCount = bis.read(byteArray)) != -1) {
			digest.update(byteArray, 0, bytesCount);
		}

		// Get the hash's bytes
		byte[] bytes = digest.digest();

		// This bytes[] has bytes in decimal format;
		// Convert it to hexadecimal format
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}

}
