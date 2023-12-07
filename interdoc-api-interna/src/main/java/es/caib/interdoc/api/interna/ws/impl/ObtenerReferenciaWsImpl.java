package es.caib.interdoc.api.interna.ws.impl;

import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.commons.utils.MarshallUtil;
import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.commons.utils.Version;
import es.caib.interdoc.plugins.apifirmasimple.FirmaSimpleController;
import es.caib.interdoc.plugins.arxiu.ArxiuController;
import es.caib.interdoc.plugins.arxiu.DocumentInfo;
import es.caib.interdoc.plugins.arxiu.Extensio;
import es.caib.interdoc.plugins.arxiu.Origen;
import es.caib.interdoc.service.exception.PluginNoTrobatException;
import es.caib.interdoc.service.facade.EntitatServiceFacade;
import es.caib.interdoc.service.facade.FitxerServiceFacade;
import es.caib.interdoc.service.facade.InfoArxiuServiceFacade;
import es.caib.interdoc.service.facade.InfoSignaturaServiceFacade;
import es.caib.interdoc.service.facade.ReferenciaServiceFacade;
import es.caib.interdoc.service.facade.ReferenciaXMLServiceFacade;
import es.caib.interdoc.service.model.EntitatDTO;
import es.caib.interdoc.service.model.FitxerDTO;
import es.caib.interdoc.service.model.InfoArxiuDTO;
import es.caib.interdoc.service.model.InfoSignaturaDTO;
import es.caib.interdoc.service.model.ReferenciaDTO;
import es.caib.interdoc.service.model.ReferenciaXMLDTO;
import es.caib.interdoc.plugins.arxiu.Fitxer;
import es.caib.interdoc.plugins.arxiu.Format;
import es.caib.interdoc.api.interna.ws.model.ObtenerReferenciaRequestInfo;
import es.caib.interdoc.api.interna.ws.resposta.EmisorBean;
import es.caib.interdoc.api.interna.ws.resposta.ErrorResponse;
import es.caib.interdoc.api.interna.ws.resposta.IdentificadorBean;
import es.caib.interdoc.api.interna.ws.resposta.MetadatosBean;
import es.caib.interdoc.api.interna.ws.resposta.PermisoBean;
import es.caib.interdoc.api.interna.ws.resposta.ReceptorBean;
import es.caib.interdoc.api.interna.ws.resposta.MetadatoBean;
import es.caib.interdoc.api.interna.ws.resposta.ReferenciaDocumentoBean;
import es.caib.interdoc.api.interna.ws.utilitats.HashCreator;
import es.caib.interdoc.api.interna.ws.utils.Metadada;

import org.apache.commons.io.FilenameUtils;
import org.jboss.ws.api.annotation.TransportGuarantee;
import org.jboss.ws.api.annotation.WebContext;
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

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.JAXBException;

/**
 * author: jagarcia
 */

@Stateless(name = ObtenerReferenciaWsImpl.NAME + "Ejb")
@RolesAllowed({ Constants.ITD_WS })
@SOAPBinding(style = SOAPBinding.Style.RPC)
@org.apache.cxf.interceptor.InInterceptors(interceptors = { "es.caib.interdoc.api.interna.ws.utilitats.WsInInterceptor" })
@org.apache.cxf.interceptor.InFaultInterceptors(interceptors = { "es.caib.interdoc.api.interna.ws.utilitats.WsOutInterceptor" })
@WebService(name = ObtenerReferenciaWsImpl.NAME_WS, portName = ObtenerReferenciaWsImpl.NAME_WS, serviceName = ObtenerReferenciaWsImpl.NAME_WS
		+ "Service")
@WebContext(urlPattern = "/protected/" + ObtenerReferenciaWsImpl.NAME_WS, transportGuarantee = TransportGuarantee.NONE, 
		secureWSDLAccess = false, authMethod = "KEYCLOAK") 
public class ObtenerReferenciaWsImpl implements ObtenerReferenciaWs {

	private static final Logger log = LoggerFactory.getLogger(ObtenerReferenciaWsImpl.class);

	public static final String NAME = "ObtenerReferencia";

	public static final String NAME_WS = NAME + "Ws";

	private String baseWsUrl = null;

	@EJB(mappedName = FitxerServiceFacade.JNDI_NAME)
	protected FitxerServiceFacade fitxerService;

	@EJB(mappedName = ReferenciaServiceFacade.JNDI_NAME)
	protected ReferenciaServiceFacade referenciaService;

	@EJB(mappedName = ReferenciaXMLServiceFacade.JNDI_NAME)
	protected ReferenciaXMLServiceFacade referenciaXMLService;

	@EJB(mappedName = InfoSignaturaServiceFacade.JNDI_NAME)
	protected InfoSignaturaServiceFacade infoSignaturaService;

	@EJB(mappedName = InfoArxiuServiceFacade.JNDI_NAME)
	protected InfoArxiuServiceFacade infoArxiuService;

	@EJB(mappedName = EntitatServiceFacade.JNDI_NAME)
	protected EntitatServiceFacade entitatService;

	@Inject
	protected Version version;

	@Inject
	protected ArxiuController arxiu;

	public void setBaseWsUrl(String url) {
		this.baseWsUrl = url;
	}

	public String getBaseWsUrl() {
		return baseWsUrl;
	}

	public String getCsvQueryDocumentWebserviceURL() {
		return Configuracio.getCsvQueryDocumentWebserviceURL();
	}

	/**
	 * Mètode que retorna la versió de interdoc que es troba desplegada al servidor
	 * d'aplicacions.
	 * 
	 * @return String
	 */

	@WebMethod
	@Override
	@PermitAll
	public String getVersionWs() {

		if (Configuracio.isDesenvolupament())
			log.info("ObtenerReferenciaWsImpl:getVersionWs");

		return version.getVersion();
	}

	/**
	 * Mètode que genera el XML de Referencia Única
	 * 
	 * @param {{@link es.caib.ìnterdoc.ws.model.ObtenerReferenciaRequestInfo}
	 *                obtenerReferenciaRequest}
	 * @return String que representa el XML de Referencia Única
	 * @throws Exception
	 */

	@WebMethod
	@PermitAll
	@Override
	public String creaReferencia(
			@WebParam(name = "obtenerReferenciaRequest") ObtenerReferenciaRequestInfo obtenerReferenciaRequestInfo)
			throws Exception {

		try {

			boolean isEnidoc = Utils.isNotEmpty(obtenerReferenciaRequestInfo.getUuid());

			boolean isSigned = false;
			boolean isInArxiu = false;

			InfoSignaturaDTO infoSignatura = null;
			String identificadorExpedient = null;
			String identificadorDocument = null;

			Long infoSignaturaId = 0L;
			Long infoArxiuId = 0L;
			Long fitxerId = 0L;

			if (Configuracio.isDesenvolupament()) {

				log.info("ObtenerReferenciaWsImpl:creaReferencia INICI");
				log.info("------ Paràmetres entrada -------");
				log.info("CSV: "
						+ ((obtenerReferenciaRequestInfo.getCsv() != null) ? obtenerReferenciaRequestInfo.getCsv()
								: "null"));
				log.info("UUID: "
						+ ((obtenerReferenciaRequestInfo.getUuid() != null) ? obtenerReferenciaRequestInfo.getUuid()
								: "null"));
				log.info("DOC.: " + ((obtenerReferenciaRequestInfo.getDocument() != null)
						? obtenerReferenciaRequestInfo.getDocument().getNom() + ", SIZE: "
								+ obtenerReferenciaRequestInfo.getDocument().getData().length
						: "null"));
				log.info("METADADES: " + ((obtenerReferenciaRequestInfo.getMetadades() != null)
						? obtenerReferenciaRequestInfo.getMetadades().size()
						: "null"));
				log.info("APPLICACIÓ_ID: " + ((obtenerReferenciaRequestInfo.getAplicacioId() != null)
						? obtenerReferenciaRequestInfo.getAplicacioId()
						: "null"));
				log.info("EMISOR: "
						+ ((obtenerReferenciaRequestInfo.getEmisor() != null) ? obtenerReferenciaRequestInfo.getEmisor()
								: "null"));
				log.info("RECEPTOR: " + ((obtenerReferenciaRequestInfo.getReceptor() != null)
						? obtenerReferenciaRequestInfo.getReceptor()
						: "null"));
				log.info("INTERESSATS: " + ((obtenerReferenciaRequestInfo.getInteressats() != null)
						? obtenerReferenciaRequestInfo.getInteressats().size()
						: 0));
				log.info("ENTITATID: " + ((obtenerReferenciaRequestInfo.getEntitatId() != null)
						? String.valueOf(obtenerReferenciaRequestInfo.getEntitatId())
						: 0));

				log.info("----------------------------------");

				log.info("isEnidoc:" + ((isEnidoc) ? "true" : "false"));

			}

			// Validació: ens ha d'arribar un identificador
			if (Utils.isEmpty(obtenerReferenciaRequestInfo.getUuid())
					&& (obtenerReferenciaRequestInfo.getDocument() == null
							|| obtenerReferenciaRequestInfo.getDocument().getData().length < 1)) {
				return generateXMLErrorResponse("400",
						"Error: no ens arriba cap UUID ni fitxer. Al manco, ens ha d'arribar un d'ells.");
			}

			Long entitatId = 0L;
			if (Utils.isNotEmpty(obtenerReferenciaRequestInfo.getEntitatId())) {
				EntitatDTO entitatDto = entitatService.findByCodiDir3(obtenerReferenciaRequestInfo.getEntitatId())
						.orElseThrow();
				if (entitatDto != null)
					entitatId = entitatDto.getId();

				log.info("CodiDir3 => " + obtenerReferenciaRequestInfo.getEntitatId() + " - id => " + entitatId);
			} else {
				return generateXMLErrorResponse("400", "Error: el codi d'entitat és obligatori.");
			}

			// Si ens arriba un UUID es tracta d'un document ENI. En cas contrari, s'ha de
			// processar.

			if (!isEnidoc) {
				// Si no és un fitxer ENIDOC, el guardam a carpeta de temporals, s'ha de firmar
				// i pujar a l'arxiu.

				/* INICI GUARDAR TEMPORAL A FILESYSTEM */

				FitxerDTO fitxerDto = null;

				if (obtenerReferenciaRequestInfo.getDocument() != null
						&& obtenerReferenciaRequestInfo.getDocument().getData().length > 0) {

					// String filePath = Configuracio.getFileTempPath();
					final String fileName = String.valueOf(System.currentTimeMillis()) + "_"
							+ ((obtenerReferenciaRequestInfo.getDocument().getNom() != null)
									? obtenerReferenciaRequestInfo.getDocument().getNom()
									: "");
					final String extension = FilenameUtils.getExtension(fileName);

					File file = File.createTempFile(fileName, "." + extension, null);
					OutputStream out = new FileOutputStream(file);
					out.write(obtenerReferenciaRequestInfo.getDocument().getData());
					out.flush();
					out.close();

					fitxerDto = new FitxerDTO();
					fitxerDto.setNom(obtenerReferenciaRequestInfo.getDocument().getNom());
					fitxerDto.setDescripcio(obtenerReferenciaRequestInfo.getDocument().getDescripcio());
					fitxerDto.setTamany(obtenerReferenciaRequestInfo.getDocument().getTamany());
					fitxerDto.setMime(obtenerReferenciaRequestInfo.getDocument().getMime());
					fitxerDto.setData(obtenerReferenciaRequestInfo.getDocument().getData());
					fitxerDto.setRuta(file.getAbsolutePath());
					fitxerId = fitxerService.create(fitxerDto);

					if (Configuracio.isDesenvolupament()) {
						log.info("Save file in: " + file.getAbsolutePath());
						log.info("FitxerId => " + fitxerId);
					}

				} else {
					log.error("obtenerReferenciaRequestInfo.getDocument() is null");
					return generateXMLErrorResponse("500",
							"Error: no ens arriba cap UUID ni fitxer. Al manco, ens ha d'arribar un d'ells.");
				}

				/* FI GUARDAR TEMPORAL A FILESYSTEM */

				/* INICI FIRMA EN SERVIDOR */
				if (fitxerDto != null) {

					try {

						FirmaSimpleController firmaPlugin = new FirmaSimpleController(entitatId);
						if (firmaPlugin.getPlugin() != null) {

							infoSignatura = firmaPlugin.getPlugin().firmarDocument(fitxerDto);
							if (infoSignatura != null) {
								infoSignaturaId = infoSignaturaService.create(infoSignatura);
								isSigned = (Utils.isNotEmpty(infoSignatura.getSignId())) ? true : false;
							}

							if (Configuracio.isDesenvolupament()) {
								if (infoSignatura != null) {
									log.info("=================== INFO SIGNATURA ==================");
									log.info("infoSignatura.getId => " + infoSignatura.getId());
									log.info("infoSignatura.getEniPerfilFirma => "
											+ ((infoSignatura.getEniPerfilFirma() != null)
													? infoSignatura.getEniPerfilFirma()
													: "null"));
									log.info("infoSignatura.getEniRolFirma => "
											+ ((infoSignatura.getEniRolFirma() != null) ? infoSignatura.getEniRolFirma()
													: "null"));
									log.info("infoSignatura.getEniTipoFirma => "
											+ ((infoSignatura.getEniTipoFirma() != null)
													? infoSignatura.getEniTipoFirma()
													: "null"));
									log.info("Is Signed ? " + String.valueOf(isSigned));
								} else {
									log.error("infosignatura is null");
								}
							}

						} else {
							log.debug("No s'ha pogut carregar el plugin de firma.");
						}

					} catch (PluginNoTrobatException pe) { 
						isSigned = true;
					
					}	catch (IOException io) {
						log.error("***********************************");
						log.error("Error stream close");
						log.error("***********************************");
						isSigned = true;
					
					} catch (Exception e) {
						e.printStackTrace();
						// TODO return generateXMLErrorResponse("500", e.getMessage());
					}

				}
				/* FI FIRMA EN SERVIDOR */

				/* INICI PUJADA ARXIU */

				if (isSigned && obtenerReferenciaRequestInfo.getDocument() != null) {

					if (Configuracio.isDesenvolupament())
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
					fitxerInfo.setExtensio(Extensio.PDF);
					fitxerInfo.setFormat(Format.PDF);

					DocumentInfo documentInfo = new DocumentInfo();
					documentInfo.setNom(
							obtenerReferenciaRequestInfo.getDocument().getNom() + "_" + System.currentTimeMillis());
					documentInfo.setOrgans(Arrays.asList(obtenerReferenciaRequestInfo.getEmisor()));
					documentInfo.setInteressats(obtenerReferenciaRequestInfo.getInteressats());
					documentInfo.setMetadades(metadadesInfoObj);
					documentInfo.setOrigen(Origen.ADMINISTRACIO);
					documentInfo.setSignatura(infoSignatura);
					documentInfo.setFitxer(fitxerInfo);

					try {
						arxiu = new ArxiuController(entitatId);

						if (arxiu.getPlugin() != null) {

							identificadorExpedient = arxiu.getPlugin().crearExpedient(documentInfo);

							identificadorDocument = arxiu.getPlugin().crearDocument(documentInfo,
									identificadorExpedient);

							if (Configuracio.isDesenvolupament())
								log.info("Identificadors expedient => " + identificadorExpedient + " - document => "
										+ identificadorDocument);

							// Recollim les dades d'arxiu per guardar-les al nostre sistema
							InfoArxiuDTO resultat = arxiu.getPlugin().consultarDocument(identificadorDocument,
									identificadorExpedient);

							if (resultat != null)
								infoArxiuId = infoArxiuService.create(resultat);

							if (infoArxiuId > 0) {
								arxiu.getPlugin().tancarExpedientIfProperty(identificadorExpedient);
							}

						} else {
							log.debug("Error alhora de carregar el plugin d'arxiu.");
						}

					} catch (Exception e) {
						log.error("Error pujada arxiu => " + e.getMessage());
						e.printStackTrace();
						return generateXMLErrorResponse("500", "Error pujada arxiu => " + e.getMessage());
					}

					isInArxiu = true;
					/* FI PUJADA ARXIU */

					// TANCAR EXPEDIENT

				} else {
					return generateXMLErrorResponse("500", "El fitxer no es puja a arxiu perqué no està signat");
				}
			}

			if (isEnidoc || (isSigned && isInArxiu)) {

				// Generate Referencia
				String referencia = createReferencia();
				// log.info("Referencia generada: " + referencia);

				// Generate Hash
				String hash = "";
				if (obtenerReferenciaRequestInfo.getDocument() != null) {
					es.caib.interdoc.api.interna.ws.utils.Fitxer fitxer = obtenerReferenciaRequestInfo.getDocument();
					if (fitxer != null) {
						MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
						hash = getFileChecksum(shaDigest, fitxer.getData());
					} else {
						return generateXMLErrorResponse("500", "No es pot generar el hash correctament del fitxer.");
					}
				}

				// Guardar els resultats a DB
				ReferenciaDTO nuevaReferenciaDto = new ReferenciaDTO();
				nuevaReferenciaDto.setCsvId(
						Utils.isNotEmpty(obtenerReferenciaRequestInfo.getCsv()) ? obtenerReferenciaRequestInfo.getCsv()
								: "");
				nuevaReferenciaDto.setDataCreacio(LocalDate.now());

				if (getBaseWsUrl() == null) {
					setBaseWsUrl(Configuracio.getBaseWsUrl());
				}

				nuevaReferenciaDto.setDireccio(getBaseWsUrl() + getCsvQueryDocumentWebserviceURL() + "?wsdl");
				nuevaReferenciaDto.setEmisor(Utils.isNotEmpty(obtenerReferenciaRequestInfo.getEmisor())
						? obtenerReferenciaRequestInfo.getEmisor()
						: "");
				if (infoSignatura != null)
					nuevaReferenciaDto.setFormatFirma(infoSignatura.getEniTipoFirma());

				nuevaReferenciaDto.setHash(Utils.isNotEmpty(hash) ? hash : "");
				nuevaReferenciaDto.setReceptor(Utils.isNotEmpty(obtenerReferenciaRequestInfo.getReceptor())
						? obtenerReferenciaRequestInfo.getReceptor()
						: "");
				nuevaReferenciaDto.setUrlVisible(getBaseWsUrl() + getCsvQueryDocumentWebserviceURL());
				nuevaReferenciaDto.setUuId(Utils.isNotEmpty(obtenerReferenciaRequestInfo.getUuid())
						? obtenerReferenciaRequestInfo.getUuid()
						: "");
				nuevaReferenciaDto.setReferencia(referencia);
				nuevaReferenciaDto.setEntitatId(entitatId);
				nuevaReferenciaDto.setInfoSignaturaId(infoSignaturaId);
				nuevaReferenciaDto.setInfoArxiuId(infoArxiuId);
				nuevaReferenciaDto.setFitxerId(fitxerId);
				nuevaReferenciaDto.setId(null);
				Long referenciaDB = referenciaService.create(nuevaReferenciaDto);

				if (Configuracio.isDesenvolupament())
					log.info("Nova Referencia creada amb id " + String.valueOf(referenciaDB));

				// Create resposta XML
				String respostaXML = generateXMLResponse(obtenerReferenciaRequestInfo, referencia, hash);

				// Guardar XML Resposta
				ReferenciaXMLDTO referenciaXMLDto = new ReferenciaXMLDTO();
				referenciaXMLDto.setReferenciaId(referenciaDB);
				referenciaXMLDto.setResultat(respostaXML);
				referenciaXMLDto.setDataCreacio(LocalDate.now());
				referenciaXMLDto.setId(null);
				referenciaXMLService.create(referenciaXMLDto);

				if (Configuracio.isDesenvolupament()) {
					log.info("-------------------  INICI RESPOSTA XML ------------------------");
					log.info(respostaXML);
					log.info("-------------------  FI RESPOSTA XML ------------------------");
				}

				return respostaXML;
			}

			return generateXMLErrorResponse("500", "Error desconegut");

		} catch (Exception e) {
			e.printStackTrace();
			return generateXMLErrorResponse("500", "Error: " + e.getMessage());
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
		return "ECHO: " + echo;
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

	private String generateXMLErrorResponse(String codigo, String descripcion) throws Exception {

		ErrorResponse error = new ErrorResponse(codigo, descripcion);
		String errorXml = null;

		try {
			errorXml = MarshallUtil.generateXML(ErrorResponse.class, error);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		if (Configuracio.isDesenvolupament())
			log.info(errorXml);

		return errorXml;
	}

	private String generateXMLResponse(ObtenerReferenciaRequestInfo obtenerReferenciaRequestInfo, String referencia,
			String hash) throws Exception {

		ReferenciaDocumentoBean referenciaDocumentoBean = new ReferenciaDocumentoBean();

		referenciaDocumentoBean.setDireccion(getBaseWsUrl() + getCsvQueryDocumentWebserviceURL() + "?wsdl");

		if (Utils.isNotEmpty(obtenerReferenciaRequestInfo.getEmisor())) {
			EmisorBean emisorBean = new EmisorBean();
			emisorBean.setOrganismo(obtenerReferenciaRequestInfo.getEmisor());
			referenciaDocumentoBean.setEmisorBean(emisorBean);
		}

		if (Utils.isNotEmpty(hash)) {
			referenciaDocumentoBean.setHash(hash);
		}

		IdentificadorBean identificadorBean = new IdentificadorBean();
		if (Utils.isNotEmpty(obtenerReferenciaRequestInfo.getCsv())) {
			identificadorBean.setValorCSV(obtenerReferenciaRequestInfo.getCsv());
		} else if (Utils.isNotEmpty(obtenerReferenciaRequestInfo.getUuid())) {
			identificadorBean.setSecuenciaIdentificador(obtenerReferenciaRequestInfo.getUuid());
		} else {
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

		if (Utils.isNotEmpty(obtenerReferenciaRequestInfo.getReceptor())) {
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

		if (Configuracio.isDesenvolupament())
			log.info(sw);

		return sw;

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
