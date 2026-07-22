package es.caib.interdoc.api.interna.ws.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.jboss.ws.api.annotation.TransportGuarantee;
import org.jboss.ws.api.annotation.WebContext;

import es.caib.interdoc.api.interna.ws.exception.InterdocException;
import es.caib.interdoc.api.interna.ws.model.ObtenerReferenciaRequestInfo;
import es.caib.interdoc.api.interna.ws.resposta.EmisorBean;
import es.caib.interdoc.api.interna.ws.resposta.ErrorResponse;
import es.caib.interdoc.api.interna.ws.resposta.IdentificadorBean;
import es.caib.interdoc.api.interna.ws.resposta.MetadatoBean;
import es.caib.interdoc.api.interna.ws.resposta.MetadatosBean;
import es.caib.interdoc.api.interna.ws.resposta.PermisoBean;
import es.caib.interdoc.api.interna.ws.resposta.ReceptorBean;
import es.caib.interdoc.api.interna.ws.resposta.ReferenciaDocumentoBean;
import es.caib.interdoc.api.interna.ws.utilitats.HashCreator;
import es.caib.interdoc.api.interna.ws.utils.Metadada;
import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.commons.utils.MarshallUtil;
import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.commons.utils.Version;
import es.caib.interdoc.ejb.PluginArxiuLogicaService;
import es.caib.interdoc.ejb.PluginFirmaLogicaService;
import es.caib.interdoc.plugins.arxiu.ArxiuPluginImpl;
import es.caib.interdoc.plugins.arxiu.DocumentInfo;
import es.caib.interdoc.plugins.arxiu.Extensio;
import es.caib.interdoc.plugins.arxiu.Fitxer;
import es.caib.interdoc.plugins.arxiu.SignaturaArxiu;
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


/**
 * author: jagarcia
 */

@Stateless(name = ObtenerReferenciaWsImpl.NAME + "Ejb")
@RolesAllowed({ Constants.ITD_WS })
@SOAPBinding(style = SOAPBinding.Style.RPC)
@org.apache.cxf.interceptor.InInterceptors(interceptors = {
		"es.caib.interdoc.api.interna.ws.utilitats.WsInInterceptor" })
@org.apache.cxf.interceptor.InFaultInterceptors(interceptors = {
		"es.caib.interdoc.api.interna.ws.utilitats.WsOutInterceptor" })
@WebService(name = ObtenerReferenciaWsImpl.NAME_WS, portName = ObtenerReferenciaWsImpl.NAME_WS, serviceName = ObtenerReferenciaWsImpl.NAME_WS
		+ "Service")
@WebContext(urlPattern = "/protected/"
		+ ObtenerReferenciaWsImpl.NAME_WS, transportGuarantee = TransportGuarantee.NONE, secureWSDLAccess = false, authMethod = "KEYCLOAK")
public class ObtenerReferenciaWsImpl implements ObtenerReferenciaWs {

	protected static final Logger log = Logger.getLogger(ObtenerReferenciaWsImpl.class);

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
	
	@EJB(mappedName = PluginArxiuLogicaService.JNDI_NAME)
    private PluginArxiuLogicaService pluginArxiuService;
	
	@EJB(mappedName = PluginFirmaLogicaService.JNDI_NAME)
    private PluginFirmaLogicaService pluginFirmaService;

	@Inject
	protected Version version;

	// @Inject
	// protected FirmaSimpleController firma;

	//@Inject
	//protected ArxiuController arxiu;

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
			throws InterdocException, Exception {

		try {

			boolean isEnidoc = Utils.isNotEmpty(obtenerReferenciaRequestInfo.getUuid());

			boolean isSigned = false;
			boolean isInArxiu = false;

			InfoSignaturaDTO infoSignatura = null;
			SignaturaArxiu infoFirma = null;
			String identificadorExpedient = null;
			String identificadorDocument = null;

			Long infoSignaturaId = null;
			Long infoArxiuId = null;
			Long fitxerId = null;

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
				log.info("FIRMA: " + ((obtenerReferenciaRequestInfo.getFirma() != null) ? "firmat" : "No firmat"));
				log.info("FORMAT FIRMA: " + ((obtenerReferenciaRequestInfo.getFirma() != null
						&& obtenerReferenciaRequestInfo.getFirma().getFormat() != null)
								? obtenerReferenciaRequestInfo.getFirma().getFormat()
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
				log.info("ORIGEN: "
						+ ((obtenerReferenciaRequestInfo.getOrigen() != null) ? obtenerReferenciaRequestInfo.getOrigen()
								: "null"));
				log.info("TIPUS DOCUMENTAL: " + ((obtenerReferenciaRequestInfo.getTipusDocumental() != null)
						? obtenerReferenciaRequestInfo.getTipusDocumental()
						: "null"));
				log.info("ESTAT ELABORACIO: " + ((obtenerReferenciaRequestInfo.getEstatElaboracio() != null)
						? obtenerReferenciaRequestInfo.getEstatElaboracio()
						: "null"));
				log.info("INTERESSATS: " + ((obtenerReferenciaRequestInfo.getInteressats() != null)
						? obtenerReferenciaRequestInfo.getInteressats().size()
						: 0));
				log.info("NUMERO REGISTRE: " + ((obtenerReferenciaRequestInfo.getNumeroRegistre() != null)
                        ? obtenerReferenciaRequestInfo.getNumeroRegistre()
                        : "null"));
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
				
				throw new InterdocException(
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
				throw new InterdocException("El codi d'entitat és obligatori");
			}

			// Si ens arriba un UUID es tracta d'un document ENI. En cas contrari, s'ha de
			// processar.

			if (!isEnidoc) {
				// Si no és un fitxer ENIDOC, el guardam a carpeta de temporals, s'ha de firmar
				// i pujar a l'arxiu.

				/* INICI GUARDAR TEMPORAL A FILESYSTEM */

				FitxerDTO fitxerDto = null;
				File file = null;
				
				log.info("NO ES ENIDOC");

				if (obtenerReferenciaRequestInfo.getDocument() != null
						&& obtenerReferenciaRequestInfo.getDocument().getData().length > 0) {
				    log.info("DOCUMENT NOT NULL");
					// String filePath = Configuracio.getFileTempPath();
					final String extension = FilenameUtils
							.getExtension(((obtenerReferenciaRequestInfo.getDocument().getNom() != null)
									? obtenerReferenciaRequestInfo.getDocument().getNom()
									: ""));
					final String fileName = String.valueOf(System.currentTimeMillis()) + "." + extension;

					file = File.createTempFile(fileName, null);
					OutputStream out = new FileOutputStream(file);
					out.write(obtenerReferenciaRequestInfo.getDocument().getData());
					out.flush();
					out.close();
					
                    


					// Guardam copia a la ruta de files
					String filePath = Configuracio.getFileTempPath();
					File file2 = new File(filePath + fileName);
					OutputStream out2 = new FileOutputStream(file2);
					out2.write(obtenerReferenciaRequestInfo.getDocument().getData());
					out2.flush();
					out2.close();
					log.info("Save copy of file: " + filePath + fileName);

					fitxerDto = new FitxerDTO();
					fitxerDto.setNom(fileName);
					fitxerDto.setDescripcio(obtenerReferenciaRequestInfo.getDocument().getDescripcio());

					Long tamanyFitxer = (obtenerReferenciaRequestInfo.getDocument().getTamany() < 1L)
							? Long.valueOf(obtenerReferenciaRequestInfo.getDocument().getData().length)
							: obtenerReferenciaRequestInfo.getDocument().getTamany();

					fitxerDto.setTamany(tamanyFitxer);
					fitxerDto.setMime(obtenerReferenciaRequestInfo.getDocument().getMime());
					fitxerDto.setData(obtenerReferenciaRequestInfo.getDocument().getData());
					fitxerDto.setRuta(file.getAbsolutePath());
					fitxerId = fitxerService.create(fitxerDto);

					if (Configuracio.isDesenvolupament()) {
						log.info("Save file in: " + file.getAbsolutePath());
						log.info("FitxerId => " + fitxerId);
					}

				} else {
					throw new InterdocException(
							"no ens arriba cap UUID ni fitxer. Al manco, ens ha d'arribar un d'ells.");
				}

				/* FI GUARDAR TEMPORAL A FILESYSTEM */

				/* INICI FIRMA EN SERVIDOR */

				// Si el fitxer ja està signat, no el signam.
				isSigned = (obtenerReferenciaRequestInfo.getFirma() != null
						&& obtenerReferenciaRequestInfo.getFirma().getFormat() != null
						&& obtenerReferenciaRequestInfo.getFirma().getFormat().length() > 0) ? true : false;
				
				log.info("-- isSigned => " + isSigned);

				if (!isSigned && fitxerDto != null) {

					try {

						//FirmaSimpleController firmaPlugin = new FirmaSimpleController(entitatId);
						
					    //InterdocFirmaPlugin firmaPlugin = pluginFirmaService.getPlugin(entitatId);
//						if (firmaPlugin != null) 
					    {
					        String languageUI = "es";
							infoSignatura = pluginFirmaService.firmarDocument(entitatId, fitxerDto, languageUI);
							if (infoSignatura != null) {
								infoSignaturaId = infoSignaturaService.create(infoSignatura);
								infoSignatura.setId(infoSignaturaId);
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
								} 
							}

						}

					} catch (Exception e) {
						e.printStackTrace();
						throw new InterdocException(e);
					}

				}
				/* FI FIRMA EN SERVIDOR */

				/* INICI PUJADA ARXIU */

				if (isSigned) {
				    log.info("-- ENTRA A IS SIGNED TRUE --");
					if (Configuracio.isDesenvolupament())
						log.info("Inici pujada arxiu");

					// Metadades
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
					
					// Fitxer
					Fitxer fitxerInfo = new Fitxer();
					fitxerInfo.setArxiuNom(fitxerDto.getNom());
					fitxerInfo.setContingut(fitxerDto.getData());
					fitxerInfo.setTamany(fitxerDto.getTamany());
                    log.info("----Probing content type = " + obtenerReferenciaRequestInfo.getDocument().getMime());
                    fitxerInfo.setTipusMime(obtenerReferenciaRequestInfo.getDocument().getMime());
					
                    


					String extensionFichero = "." + FilenameUtils.getExtension(fitxerDto.getNom()).toLowerCase();
					final Extensio extensio = Extensio.toEnum(extensionFichero);
					fitxerInfo.setExtensio((extensio != null) ? extensio : Extensio.PDF);
					// log.info("FITXERDTO: => " + fitxerInfo.toString());

					if (obtenerReferenciaRequestInfo.getFirma() != null
							&& obtenerReferenciaRequestInfo.getFirma().getFormat() != null
							&& obtenerReferenciaRequestInfo.getFirma().getFormat().length() > 0) {
					    
						log.info("-- Document firmat previament");

						infoFirma = new SignaturaArxiu();
						infoFirma.setFormatFirma(obtenerReferenciaRequestInfo.getFirma().getFormat());
						infoFirma.setPerfilFirma(obtenerReferenciaRequestInfo.getFirma().getPerfil());

						if (obtenerReferenciaRequestInfo.getFirma().getFitxer() != null
								&& obtenerReferenciaRequestInfo.getFirma().getFitxer().getNom() != null) {
							// DOCUMENT AMB FIRMA DETACHED

							es.caib.interdoc.api.interna.ws.utils.Fitxer temp = obtenerReferenciaRequestInfo.getFirma()
									.getFitxer();

							log.info("Fitxer obtenerReferenciaRequestInfo.getFirma.getFitxer: " + temp.toString());

							Fitxer fitxerFirma = new Fitxer();
							fitxerFirma.setArxiuNom(temp.getNom());
							fitxerFirma.setContingut(temp.getData());
							fitxerFirma.setTamany(temp.getTamany());
							fitxerFirma.setTipusMime(obtenerReferenciaRequestInfo.getDocument().getMime());

							log.info("infoFirma.setFirma: " + fitxerFirma.toString());
							infoFirma.setFirma(fitxerFirma);
						}

						// log.info("Infofirma => " + infoFirma.toString());
					}

					DocumentInfo documentInfo = new DocumentInfo();
					documentInfo.setNom(fitxerDto.getNom());
					documentInfo.setOrgans(Arrays.asList(obtenerReferenciaRequestInfo.getEmisor()));
					documentInfo.setInteressats(obtenerReferenciaRequestInfo.getInteressats());
					documentInfo.setMetadades(metadadesInfoObj);

					if (obtenerReferenciaRequestInfo.getOrigen() != null)
						documentInfo.setOrigen(obtenerReferenciaRequestInfo.getOrigen());

					if (obtenerReferenciaRequestInfo.getTipusDocumental() != null)
						documentInfo.setTipusDocumental(obtenerReferenciaRequestInfo.getTipusDocumental());

					if (obtenerReferenciaRequestInfo.getEstatElaboracio() != null)
						documentInfo.setEstatElaboracio(obtenerReferenciaRequestInfo.getEstatElaboracio());
					
					documentInfo.setFitxer(fitxerInfo);
					documentInfo.setSignatura(infoSignatura);
					documentInfo.setFirma(infoFirma);
					
					//Prints per tots els camps de documentInfo
					
					

					String numeroExpedient = null;
					if (obtenerReferenciaRequestInfo.getNumeroRegistre() != null) {
						numeroExpedient = referenciaService.findExpedientByNumeroRegistre(obtenerReferenciaRequestInfo.getNumeroRegistre(), entitatId);
						log.info("Numero Expedient => " + numeroExpedient);
					}
					
					// log.info( " ------------------------ INFO PRE ARXIU ------------------------");
					// log.info("DocumentInfo => " + documentInfo.toString());
					// log.info( " ----------------------------------------------------------------");

					try {
						//arxiu = new ArxiuController(entitatId);
						ArxiuPluginImpl plugin = pluginArxiuService.getInstanceOfPlugin(entitatId);

						if (plugin != null) {
							
							identificadorExpedient = (Utils.isEmpty(numeroExpedient)) ? plugin.crearExpedient(documentInfo) : numeroExpedient;
							log.info("Identificador expedient => " + identificadorExpedient);
							
							log.info("DocumentInfo nom => " + documentInfo.getNom());
		                    log.info("DocumentInfo organs => " + documentInfo.getOrgans());
		                    log.info("DocumentInfo interessats => " + documentInfo.getInteressats());
		                    log.info("DocumentInfo metadades => " + documentInfo.getMetadades());
		                    log.info("DocumentInfo origen => " + documentInfo.getOrigen());
		                    log.info("DocumentInfo estatElaboracio => " + documentInfo.getEstatElaboracio());
		                    log.info("DocumentInfo tipusDocumental => " + documentInfo.getTipusDocumental());
		                    log.info("DocumentInfo fitxer => " + documentInfo.getFitxer());
		                    log.info("DocumentInfo signatura => " + documentInfo.getSignatura());
		                    log.info("DocumentInfo firma => " + documentInfo.getFirma());
		                    log.info("DocumentInfo numeroRegistre => " + documentInfo.getNumeroRegistre());
		                    log.info("Identificador expedient => " + identificadorExpedient);
							
							identificadorDocument = plugin.crearDocument(documentInfo, identificadorExpedient);
							String eniDoc = plugin.generarEniDoc(identificadorDocument);
							log.info("ENI DOC generat => " + eniDoc);

							if (Configuracio.isDesenvolupament())
								log.info("numeroExpedient == null ??? " + ((numeroExpedient == null) ? "true" : "false") );
								log.info("Identificadors expedient => " + identificadorExpedient + " - document => "
										+ identificadorDocument);

							// Recollim les dades d'arxiu per guardar-les al nostre sistema
							InfoArxiuDTO resultat = plugin.consultarDocument(identificadorDocument,
									identificadorExpedient);

							if (resultat != null)
								infoArxiuId = infoArxiuService.create(resultat);

							if (infoArxiuId > 0) {
								// Tancam el expedient dins Arxiu
								boolean updated = plugin.tancarExpedientIfProperty(identificadorExpedient);
								
								if (updated) {
									// Actualitzam l'estatExpedient
									resultat.setId(infoArxiuId);
									resultat.setEstatExpedient(InfoArxiuDTO.EXPEDIENT_TANCAT);
									infoArxiuService.update(resultat);
								}
							}

						} 

					} catch (Exception e) {
						log.error("Error pujada arxiu", e);
						throw new InterdocException("Error pujada arxiu", e);
					}

					isInArxiu = true;
					/* FI PUJADA ARXIU */

				} else {
					// return generateXMLErrorResponse("500", "El fitxer no es puja a arxiu perqué
					// no està signat");
					throw new InterdocException("El fitxer no es pot pujar a arxiu perqué no està signat");
				}
			}

			if (isEnidoc || (isSigned && isInArxiu)) {

				// Generate Referencia
				String referencia = (!isEnidoc) ? createReferencia() : obtenerReferenciaRequestInfo.getUuid();
				// log.info("Referencia generada: " + referencia);
                
				// Generate Hash
				String hash = "";
				if (obtenerReferenciaRequestInfo.getDocument() != null) {
					es.caib.interdoc.api.interna.ws.utils.Fitxer fitxer = obtenerReferenciaRequestInfo.getDocument();
					if (fitxer != null) {
						MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
						hash = getFileChecksum(shaDigest, fitxer.getData());
					} else {
						// return generateXMLErrorResponse("500", "No es pot generar el hash
						// correctament del fitxer.");
						throw new InterdocException("No es pot generar el hash correctament del fitxer.");
					}
				}

				// Guardar els resultats a DB
				ReferenciaDTO nuevaReferenciaDto = new ReferenciaDTO();
				if (!isEnidoc)
					nuevaReferenciaDto.setCsvId(referencia);
				else
					nuevaReferenciaDto.setUuId(referencia);

				nuevaReferenciaDto.setDataCreacio(LocalDateTime.now());

				if (getBaseWsUrl() == null) {
					setBaseWsUrl(Configuracio.getBaseWsUrl());
				}

				nuevaReferenciaDto.setDireccio(getBaseWsUrl() + getCsvQueryDocumentWebserviceURL() + "?wsdl");
				nuevaReferenciaDto.setEmisor(Utils.isNotEmpty(obtenerReferenciaRequestInfo.getEmisor())
						? obtenerReferenciaRequestInfo.getEmisor()
						: "");

				if (infoFirma != null) {
					nuevaReferenciaDto.setFormatFirma(infoFirma.getFormatFirma());
				} else if (infoSignatura != null)
					nuevaReferenciaDto.setFormatFirma(infoSignatura.getEniTipoFirma());

				nuevaReferenciaDto.setHash(Utils.isNotEmpty(hash) ? hash : "");
				nuevaReferenciaDto.setReceptor(Utils.isNotEmpty(obtenerReferenciaRequestInfo.getReceptor())
						? obtenerReferenciaRequestInfo.getReceptor()
						: "");
				nuevaReferenciaDto.setUrlVisible(getBaseWsUrl() + getCsvQueryDocumentWebserviceURL());
				nuevaReferenciaDto.setReferencia(referencia);
				nuevaReferenciaDto.setEntitatId(entitatId);
				nuevaReferenciaDto.setInfoSignaturaId(infoSignaturaId);
				nuevaReferenciaDto.setInfoArxiuId(infoArxiuId);
				nuevaReferenciaDto.setFitxerId(fitxerId);
				
				if (obtenerReferenciaRequestInfo.getNumeroRegistre() != null) {
					nuevaReferenciaDto.setNumeroRegistre(obtenerReferenciaRequestInfo.getNumeroRegistre());
				}
				
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
				referenciaXMLService.create(referenciaXMLDto);

				if (Configuracio.isDesenvolupament()) {
					log.info("-------------------  INICI RESPOSTA XML ------------------------");
					log.info(respostaXML);
					log.info("-------------------  FI RESPOSTA XML ------------------------");
				}

				return respostaXML;

			}

		} catch (Exception e) {
		    log.error("Error creant la referencia", e);
		    throw new InterdocException(e);
		}
        return null;
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
		HashCreator nouHash = new HashCreator();
		return nouHash.createPasswordHashWithSalt(String.valueOf(System.currentTimeMillis()));
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
		
		if (Utils.isNotEmpty(obtenerReferenciaRequestInfo.getUuid())) {
			identificadorBean.setSecuenciaIdentificador(referencia);
		} else {
			identificadorBean.setValorCSV(referencia);	
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
		referenciaDocumentoBean.setFormatoFirma(referenciaDocumentoBean.getFormatoFirma());

		// TODO trazabilidad

		return MarshallUtil.generateXML(ReferenciaDocumentoBean.class, referenciaDocumentoBean);

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
