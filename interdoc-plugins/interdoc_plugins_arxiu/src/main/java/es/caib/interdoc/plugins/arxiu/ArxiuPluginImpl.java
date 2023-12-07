package es.caib.interdoc.plugins.arxiu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.arxiudigital.apirest.facade.resultados.Resultado;
import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.service.model.InfoArxiuDTO;
import es.caib.plugins.arxiu.api.ContingutArxiu;
import es.caib.plugins.arxiu.api.ContingutTipus;
import es.caib.plugins.arxiu.api.Document;
import es.caib.plugins.arxiu.api.DocumentEstat;
import es.caib.plugins.arxiu.api.DocumentEstatElaboracio;
import es.caib.plugins.arxiu.api.DocumentExtensio;
import es.caib.plugins.arxiu.api.DocumentFormat;
import es.caib.plugins.arxiu.api.DocumentMetadades;
import es.caib.plugins.arxiu.api.DocumentTipus;
import es.caib.plugins.arxiu.api.Expedient;
import es.caib.plugins.arxiu.api.ExpedientEstat;
import es.caib.plugins.arxiu.api.ExpedientMetadades;
import es.caib.plugins.arxiu.api.Firma;
import es.caib.plugins.arxiu.api.FirmaPerfil;
import es.caib.plugins.arxiu.api.FirmaTipus;
import es.caib.plugins.arxiu.api.IArxiuPlugin;
import es.caib.plugins.arxiu.caib.ArxiuPluginCaib;

/**
 * Implementació del plugin d'arxiu
 * @author jagarcia
 *
 */
public class ArxiuPluginImpl extends AbstractPluginProperties implements InterdocArxiuPlugin {

	protected static final Logger LOG = LoggerFactory.getLogger(ArxiuPluginImpl.class);

	private static final String PROPERTY_SERIE_DOCUMENTAL = INTERDOC_ARXIU_PLUGIN_PROPERTY + "serieDocumental";
	private static final String PROPERTY_CLASIFICACIO = INTERDOC_ARXIU_PLUGIN_PROPERTY + "classificacio";
	private static final String PROPERTY_CODI_APLICACIO = INTERDOC_ARXIU_PLUGIN_PROPERTY + "aplicacio";
	private static final String PROPERTY_TANCAR_EXPEDIENT = INTERDOC_ARXIU_PLUGIN_PROPERTY + "tancarExpedient";
	private static final String PROPERTY_CSV_URL = INTERDOC_ARXIU_PLUGIN_PROPERTY + "csv.url";
	private static final String PROPERTY_CSV_VALIDATION = INTERDOC_ARXIU_PLUGIN_PROPERTY + "csv.validation.url";

	private static final String PROPERTY_ENDPOINT = INTERDOC_ARXIU_PLUGIN_PROPERTY + "endpoint";
	private static final String PROPERTY_USERNAME = INTERDOC_ARXIU_PLUGIN_PROPERTY + "usuari";
	private static final String PROPERTY_PASS = INTERDOC_ARXIU_PLUGIN_PROPERTY + "contrasenya";

	private Properties propietats;

	@Inject
	private es.caib.plugins.arxiu.api.IArxiuPlugin plugin;

	public Properties getPropietats() {
		return propietats;
	}

	public void setPropietats(Properties propietats) {
		this.propietats = propietats;
	}

	public IArxiuPlugin getPlugin() {
		return plugin;
	}

	public void setPlugin(IArxiuPlugin plugin) {
		this.plugin = plugin;
	}

	public ArxiuPluginImpl() {
		super();
		LOG.info("Inici del plugin d'arxiu Impl");
	}

	public ArxiuPluginImpl(String classe, Properties props) {
		carregarProperties(props);
	}

	@Override
	public void carregarPropertiesFile() {
		try {
			Config config = ConfigProvider.getConfig();
			propietats.put(PROPERTY_SERIE_DOCUMENTAL, config.getValue(PROPERTY_SERIE_DOCUMENTAL, String.class));
			propietats.put(PROPERTY_CLASIFICACIO, config.getValue(PROPERTY_CLASIFICACIO, String.class));
			propietats.put(PROPERTY_CODI_APLICACIO, config.getValue(PROPERTY_CODI_APLICACIO, String.class));

			if (Configuracio.isDesenvolupament()) {
				LOG.info("------------ PROPIEDADES ARXIU FILE -------------------");
				propietats.stringPropertyNames().forEach(x -> LOG.info(x + " => " + propietats.getProperty(x)));
				LOG.info("---------------------------------------------------");
			}
		} catch (Exception e) {
			LOG.error("S'ha produit un error alhora de carregar les propietats. ");
			e.printStackTrace();
		}
	}

	@Override
	public void carregarProperties(Properties props) {
		setPropietats(props);
		if (Configuracio.isDesenvolupament())
			try {
				LOG.info("------------ PROPIETATS ARXIU DB -------------------");
				propietats.stringPropertyNames().forEach(x -> LOG.info(x + " => " + propietats.getProperty(x)));
				LOG.info("---------------------------------------------------");
			} catch (Exception e) {
				LOG.error("S'ha produit un error alhora de carregar les propietats");
				e.printStackTrace();
			}
	}

	@Override
	public String crearExpedient(DocumentInfo documentInfo) throws DocumentNotValidException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		if (documentInfo == null)
			throw new DocumentNotValidException();

		Expedient expedient = null;

		try {
			expedient = new Expedient();
			expedient.setIdentificador(null);
			expedient.setNom(documentInfo.getNom());

			ExpedientMetadades metadades = new ExpedientMetadades();
			metadades.setIdentificador(null);
			metadades.setDataObertura(new Date());
			metadades.setClassificacio(propietats.getProperty(PROPERTY_CLASIFICACIO));
			metadades.setEstat(ExpedientEstat.OBERT);
			if (documentInfo.getOrgans() != null && documentInfo.getOrgans().size() > 0)
				metadades.setOrgans(documentInfo.getOrgans());
			if (documentInfo.getInteressats() != null && documentInfo.getInteressats().size() > 0)
				metadades.setInteressats(documentInfo.getInteressats());
			metadades.setSerieDocumental(propietats.getProperty(PROPERTY_SERIE_DOCUMENTAL));
			expedient.setMetadades(metadades);

			if (plugin == null) {
				plugin = getArxiuPlugin();
			}

			ContingutArxiu expedientCreat = plugin.expedientCrear(expedient);

			ContingutTipus contingutTipus = expedientCreat.getTipus();

			ExpedientMetadades expMetadades = null;
			if (expedientCreat.getExpedientMetadades() != null)
				expMetadades = expedientCreat.getExpedientMetadades();

			DocumentMetadades expMetadadades = expedientCreat.getDocumentMetadades();

			List<Firma> expFirmes = expedientCreat.getFirmes();

			if (Configuracio.isDesenvolupament()) {
				LOG.info(" ==============  EXPEDIENT CREAT ================== ");
				LOG.info("expedientCreat.identificador => " + expedientCreat.getIdentificador());
				LOG.info("expedientCreat.nom => " + expedientCreat.getNom());
				LOG.info("expedientCreat.descripcio => "
						+ (Utils.isNotEmpty(expedientCreat.getDescripcio()) ? expedientCreat.getDescripcio() : "null"));
				LOG.info("expedientCreat.versio => " + expedientCreat.getVersio());
				LOG.info("Contingut Tipus => " + contingutTipus.toString());
				if (expMetadades != null) {
					LOG.info("expMetadades.getIdentificador =>" + expMetadades.getIdentificador());
					LOG.info("expMetadades.getSerieDocumental =>" + expMetadades.getSerieDocumental());
					LOG.info("expMetadades.getVersioNti => " + expMetadades.getVersioNti());
					LOG.info("expMetadades.getClassificacio =>" + expMetadades.getClassificacio());
					LOG.info("expMetadades.getDataObertura =>" + sdf.format(expMetadades.getDataObertura()));
					LOG.info("expMetadades.getEstat =>" + expMetadades.getEstat().name());
					if (expMetadades.getOrgans() != null && expMetadades.getOrgans().size() > 0)
						expMetadades.getOrgans().forEach(x -> LOG.info("expMetadades.getOrgans => " + x));
				}
				if (expMetadadades != null) {
					LOG.info("expMetadadades.getCsv =>"
							+ (Utils.isNotEmpty(expMetadadades.getCsv()) ? expMetadadades.getCsv() : "null"));
					LOG.info("expMetadadades.getDataCaptura =>" + sdf.format(expMetadadades.getDataCaptura()));
					LOG.info("expMetadadades.getExtensio =>" + expMetadadades.getExtensio().name());
					LOG.info("expMetadadades.getFormat =>" + expMetadadades.getFormat().name());
					LOG.info("expMetadadades.getIdentificadorOrigen =>" + expMetadadades.getIdentificadorOrigen());
					LOG.info("expMetadadades.getOrigen =>" + expMetadadades.getOrigen().name());
					LOG.info("expMetadadades.getSerieDocumental =>" + expMetadadades.getSerieDocumental());
					LOG.info("expMetadadades.getTipusDocumental =>" + expMetadadades.getTipusDocumental().name());
				}
				if (expedientCreat.getFirmes() != null && expedientCreat.getFirmes().size() > 0)
					expFirmes.forEach(
							x -> LOG.info("Firma => " + (Utils.isNotEmpty(x.getFitxerNom()) ? x.getFitxerNom() : "")));
			}

			return expedientCreat.getIdentificador();

		} catch (Exception e) {
			LOG.error("Error alhora de carregar la informació per crear l'expedient");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String crearDocument(DocumentInfo documentInfo, String expedientId)
			throws DocumentNotValidException, Exception {

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			if (documentInfo.getFitxer() == null || documentInfo.getFitxer().getTamany() < 1L) {
				LOG.error("No existeix document adjunt");
				throw new DocumentNotValidException();
			}

			Fitxer fitxerOriginal = documentInfo.getFitxer();

			Document documentPerCrear = new Document();
			documentPerCrear.setIdentificador(null);
			documentPerCrear.setNom(fitxerOriginal.getArxiuNom());
			documentPerCrear.setEstat(DocumentEstat.DEFINITIU);
			documentPerCrear.setContingut(null);
			documentPerCrear.setFirmes(new ArrayList<Firma>());

			Firma firma = null;

			try {
				firma = getFirma(documentInfo);
			} catch (Exception e) {
				LOG.error("No es pot obtenir la firma!");
				e.printStackTrace();
			}

			if (firma != null)
				documentPerCrear.getFirmes().add(firma);

			DocumentMetadades documentMetadades = new DocumentMetadades();
			documentMetadades.setSerieDocumental(propietats.getProperty(PROPERTY_SERIE_DOCUMENTAL));
			documentMetadades.setDataCaptura(new Date());
			documentMetadades.setOrigen(Origen.toContingutOrigen(Origen.ADMINISTRACIO));
			documentMetadades.setEstatElaboracio(DocumentEstatElaboracio.ORIGINAL);
			documentMetadades.setTipusDocumental(DocumentTipus.ALTRES);
			documentMetadades.setFormat(getDocumentFormat(DocumentExtensio.PDF));
			documentMetadades.setExtensio(DocumentExtensio.PDF);

			if (documentInfo.getOrgans() != null && documentInfo.getOrgans().size() > 0)
				documentMetadades.setOrgans(documentInfo.getOrgans());
			documentPerCrear.setMetadades(documentMetadades);

			ContingutArxiu subido = this.plugin.documentCrear(documentPerCrear, expedientId);

			ContingutTipus subidoTipus = subido.getTipus();

			ExpedientMetadades subMetadades = subido.getExpedientMetadades();

			DocumentMetadades subDocMetadadades = subido.getDocumentMetadades();

			List<Firma> subFirmes = subido.getFirmes();

			if (Configuracio.isDesenvolupament()) {
				LOG.info("========== FITXERORIGINAL ENTRADA ===================");
				LOG.info("fitxerOriginal.getArxiuNom => " + fitxerOriginal.getArxiuNom());
				LOG.info("fitxerOriginal.getExtensio => " + fitxerOriginal.getExtensio().name());
				LOG.info("fitxerOriginal.getFormat => " + fitxerOriginal.getFormat().name());
				LOG.info("fitxerOriginal.getTamany => " + String.valueOf(fitxerOriginal.getTamany()));
				LOG.info("fitxerOriginal.getTipusMime => " + fitxerOriginal.getTipusMime());
				LOG.info("fitxerOriginal.getContingut.length => " + (fitxerOriginal.getContingut()).length);
				LOG.info("_____________________________________________");
				LOG.info("documentMetadades.getSerieDocumental => " + documentMetadades.getSerieDocumental());
				LOG.info("documentMetadades.getDataCaptura => " + sdf.format(documentMetadades.getDataCaptura()));
				LOG.info("documentMetadades.getOrigen => " + documentMetadades.getOrigen().name());
				if (documentInfo.getOrgans() != null && documentInfo.getOrgans().size() > 0)
					documentMetadades.getOrgans().forEach(x -> LOG.info("Organ => " + x));
				LOG.info(" ==============  DOCUMENT CREAT ================== ");
				LOG.info("subido.identificador => " + subido.getIdentificador());
				LOG.info("subido.nom => " + subido.getNom());
				LOG.info("subido.descripcio => " + subido.getDescripcio());
				LOG.info("subido.versio => " + subido.getVersio());
				LOG.info("subido Tipus => " + ((subidoTipus != null) ? subidoTipus.toString() : "null"));
				if (subMetadades != null) {
					LOG.info("subido docMetadades.getIdentificador =>" + subMetadades.getIdentificador());
					LOG.info("subido docMetadades.getSerieDocumental =>" + subMetadades.getSerieDocumental());
					LOG.info("subido docMetadades.getVersioNti => " + subMetadades.getVersioNti());
					LOG.info("subido docMetadades.getClassificacio =>" + subMetadades.getClassificacio());
					LOG.info("subido expMetadades.getDataObertura =>" + sdf.format(subMetadades.getDataObertura()));
					if (subMetadades.getOrgans() != null && subMetadades.getOrgans().size() > 0)
						subMetadades.getOrgans().forEach(x -> LOG.info("subMetadades.getOrgans => " + x));
					LOG.info("subido expMetadades.getEstat =>" + subMetadades.getEstat().name());
				}
				if (subDocMetadadades != null) {
					LOG.info("docMetadadades.getCsv =>"
							+ (Utils.isNotEmpty(subDocMetadadades.getCsv()) ? subDocMetadadades.getCsv() : "null"));
					LOG.info("docMetadadades.getDataCaptura =>" + sdf.format(subDocMetadadades.getDataCaptura()));
					LOG.info("docMetadadades.getExtensio =>" + subDocMetadadades.getExtensio().name());
					LOG.info("docMetadadades.getFormat =>" + subDocMetadadades.getFormat().name());
					LOG.info("docMetadadades.getIdentificadorOrigen =>"
							+ (Utils.isNotEmpty(subDocMetadadades.getIdentificadorOrigen())
									? subDocMetadadades.getIdentificadorOrigen()
									: "null"));
					LOG.info("docMetadadades.getOrigen =>" + subDocMetadadades.getOrigen().name());
					LOG.info("docMetadadades.getSerieDocumental =>"
							+ (Utils.isNotEmpty(subDocMetadadades.getSerieDocumental())
									? subDocMetadadades.getSerieDocumental()
									: "null"));
					LOG.info("docMetadadades.getTipusDocumental =>" + subDocMetadadades.getTipusDocumental().name());
				}
				if (subido.getFirmes() != null && subido.getFirmes().size() > 0)
					subFirmes.forEach(x -> LOG.info("Firma => " + x.getFitxerNom()));
			}

			return subido.getIdentificador();

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public InfoArxiuDTO consultarDocument(String documentId, String expedientId) throws Exception {

		if (Configuracio.isDesenvolupament()) {
			LOG.info("Arxiu.consultarDocument => " + documentId + " - expedientID => " + expedientId);
		}

		if (Utils.isEmpty(documentId))
			return null;

		InfoArxiuDTO info = null;

		try {

			if (plugin == null) {
				plugin = getArxiuPlugin();
			}

			Document detalls = this.plugin.documentDetalls(documentId, null, true);

			if (Utils.isNotEmpty(detalls.getIdentificador()))
				LOG.info("detalls => " + detalls.getIdentificador() + "  - validació:  " + documentId);

			if (detalls.getMetadades() != null && Utils.isNotEmpty(detalls.getMetadades().getCsv()))
				LOG.info("detalls csv => " + detalls.getMetadades().getCsv());

			if (detalls.getExpedientMetadades() != null
					&& Utils.isNotEmpty(detalls.getExpedientMetadades().getIdentificador()))
				LOG.info("detalls expedientId => " + detalls.getMetadades().getCsv() + " - validació: " + expedientId);

			if (detalls != null) {

				// final String csvValidation = plugin.getCsvValidationWeb(docId);
				// final String eniFileUrl = plugin.getEniFileUrl(docId);
				// final String originalFileUrl = plugin.getOriginalFileUrl(docId);
				// final String printableUrl = plugin.getPrintableFileUrl(docId);
				// final String validationFileUrl = plugin.getValidationFileUrl(docId);

				info = new InfoArxiuDTO();
				info.setArxiuDocumentID(documentId);
				info.setArxiuExpedientID(expedientId);
				info.setCsv(detalls.getMetadades().getCsv());

				// info.setCsvGenerationDefinition(detalls.getMetadades().getCsvDef());
				// info.setCsvValidationWeb(csvValidation);
				// info.setEniFileUrl(eniFileUrl);
				// info.setOriginalFileUrl(originalFileUrl);
				// info.setPrintableUrl(printableUrl);
				// info.setValidationFileUrl(validationFileUrl);

				if (Configuracio.isDesenvolupament()) {

					LOG.info("============= INFOARXIUDTO ===========");

					LOG.info(("true".equals(propietats.getProperty(PROPERTY_TANCAR_EXPEDIENT)))
							? "Tancar expedientes => true"
							: "Tancar expedientes => false");
					LOG.info("csv.validation.url >= " + propietats.getProperty(PROPERTY_CSV_VALIDATION));
					LOG.info("csv.url >= " + propietats.getProperty(PROPERTY_CSV_URL) + detalls.getIdentificador());
					LOG.info("csv => " + detalls.getExpedientMetadades().getIdentificador());

					LOG.info("estat document => " + detalls.getEstat().name() + " -  " + detalls.getEstat().ordinal());
					LOG.info("estat expedient => " + detalls.getExpedientMetadades().getEstat().name() + " -  "
							+ detalls.getExpedientMetadades().getEstat().ordinal());

					LOG.info("InfoArxiuDto => " + info.toString());
					LOG.info("______________________________________");
				}
			}

		} catch (Exception e) {
			LOG.error("Error alhora de consultar el document " + documentId + " de l'expedientId " + expedientId);
			e.printStackTrace();
		}

		return info;
	}

	private Firma getFirma(DocumentInfo documentInfo) throws Exception {

		Firma firma = null;

		if (documentInfo.getSignatura() != null) {
			firma = new Firma();

			firma.setFitxerNom(documentInfo.getSignatura().getFileName());
			firma.setContingut(documentInfo.getSignatura().getFileData());
			firma.setTamany((documentInfo.getSignatura().getFileData()).length);
			firma.setPerfil(FirmaPerfil.BES);
			firma.setTipus(getFirmaTipus(documentInfo.getSignatura().getSignType(),
					String.valueOf(documentInfo.getSignatura().getSignMode())));
			firma.setTipusMime(documentInfo.getSignatura().getFileMime());

			if (Configuracio.isDesenvolupament()) {
				LOG.info("========== INFO FIRMA DOCUMENT ARXIU =======================");
				LOG.info("firma.getFitxerNom => " + firma.getFitxerNom());
				LOG.info("firma.getContingut().length => " + (firma.getContingut()).length);
				LOG.info("firma.getTamany() => " + firma.getTamany());
				LOG.info("firma.getPerfil() => " + ((firma.getPerfil() != null) ? firma.getPerfil().name() : "null"));
				LOG.info("firma.getTipusMime => " + firma.getTipusMime());
				LOG.info("firma.getTipus => " + firma.getTipus().name());
				LOG.info("_______________________________________________");
			}
		}

		return firma;
	}

	@Override
	public Expedient getExpediente(String identificador, String version) {

		LOG.info("getExpediente({},}{})", identificador, version);

		Expedient expediente = null;

		try {
			expediente = this.plugin.expedientDetalls(identificador, version);
		} catch (Exception e) {
			LOG.error("Error obteniendo el Expediente: " + identificador);
			e.printStackTrace();
		}

		return expediente;
	}

	@Override
	public Document getDocument(String identificador, String version, Boolean contenido, Boolean original) {

		LOG.info("getDocument({},}{},{},{})", new Object[] { identificador, version, contenido, original });

		Document documento = null;

		try {
			if (!contenido.booleanValue())
				return this.plugin.documentDetalls(identificador, version, false);
			if (original.booleanValue())
				return this.plugin.documentDetalls(identificador, version, true);
		} catch (Exception e) {
			LOG.error("Error obteniendo el Documento: " + identificador);
			e.printStackTrace();
		}

		return documento;
	}
	

	
	@Override 
	public void tancarExpedient(String identificador, Long entitatId) throws Exception {
		
		Date inici = new Date();
        Resultado res = null;
        int reintents = 10;
        
        do {
            --reintents;
            
            try {
            	
                String resultat = tancarExpedient(identificador);
                LOG.info("resultat tancarExpedient amb id " + identificador + " => " + resultat);
                
            } catch (Exception var23) {
                LOG.error("Error no controlat al tancarExpedient()[Miram si podem reintentar...]: " + var23.getMessage(), var23);
                String error = var23.getMessage();
                if (reintents <= 0 || error == null || !error.contains("Proxy Error") || !error.contains("/services/closeFile")) {
                	LOG.error("Error no controlat al tancarExpedient()");
                }
	
	                res = new Resultado();
	                res.setCodigoResultado("COD_020");
	                res.setMsjResultado("Send timeout");
            }

            String errorCodi = res.getCodigoResultado();
            if (!this.hiHaError(errorCodi)) {
                LOG.info("Se ha cerrado correctamente el Expediente: " + identificador);
                break;
            }

            String msg = res.getMsjResultado();

            if (!"COD_020".equals(errorCodi) || !msg.startsWith("Send timeout")) {
                if ("COD_021".equals(errorCodi) && msg.startsWith("Could not have the permission of Delete to perfom the operation")) {
                    LOG.info("S'ha intentat cerrarExpediente() però aquest ja esta a RM. Es dóna per bó.");
                    break;
                }
                LOG.error("No s'ha pogut tancar l'expedient amb uuid " + identificador + ": " + res.getCodigoResultado() + " - " + res.getMsjResultado());
            }

            LOG.warn("Gestió de reintents de apiArxiu.cerrarExpediente(): reintent compte enrera " + reintents + ". Esperam " + 5000L + " ms");
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (reintents <= 0) {
                LOG.error("S'han esgotat els reintents i no s'ha pogut tancar l'expedient amb uuid " + identificador + ": " + res.getCodigoResultado() + " - " + res.getMsjResultado());
            }

        } while(reintents > 0);
        
	}
	
	private boolean hiHaError(String code) {
        return !"COD_000".equals(code);
    }

	@Override
	public boolean borrarExpedient(String identificador) {
		LOG.info("borrarExpedient({})", identificador);
		if (identificador != null) {
			this.plugin.expedientEsborrar(identificador);
			return true;
		}
		return false;
	}

	@Override
	public String generarEniDoc(String identificador) {
		LOG.info("generarEniDoc::" + identificador);
		if (Utils.isNotEmpty(identificador))
			return this.plugin.documentExportarEni(identificador);
		return null;
	}

	@Override
	public Document descarregarDocument(String identificador) {
		LOG.info("descarregarDocument({})", identificador);
		Document document = this.plugin.documentDetalls(identificador, null, true);
		return document;
	}

	@Override
	public boolean tancarExpedientIfProperty(String identificador) {
		LOG.info("tancarExpedientIfProperty::" + identificador);
		if ("true".equals(propietats.getProperty(PROPERTY_TANCAR_EXPEDIENT))) {
			tancarExpedient(identificador);
			return true;
		}
		return false;
	}

	@Override
	public String tancarExpedient(String identificador) {
		LOG.info("tancarExpedient::" + identificador);
		return this.plugin.expedientTancar(identificador);
	}

	private DocumentFormat getDocumentFormat(DocumentExtensio extension) {
		switch (extension) {
		case AVI:
			return DocumentFormat.AVI;
		case CSS:
			return DocumentFormat.CSS;
		case CSV:
			return DocumentFormat.CSV;
		case DOCX:
			return DocumentFormat.SOXML;
		case GML:
			return DocumentFormat.GML;
		case GZ:
			return DocumentFormat.GZIP;
		case HTM:
			return DocumentFormat.XHTML;
		case HTML:
			return DocumentFormat.XHTML;
		case JPEG:
			return DocumentFormat.JPEG;
		case JPG:
			return DocumentFormat.JPEG;
		case MHT:
			return DocumentFormat.MHTML;
		case MHTML:
			return DocumentFormat.MHTML;
		case MP3:
			return DocumentFormat.MP3;
		case MP4:
			return DocumentFormat.MP4V;
		case MPEG:
			return DocumentFormat.MP4V;
		case ODG:
			return DocumentFormat.OASIS12;
		case ODP:
			return DocumentFormat.OASIS12;
		case ODS:
			return DocumentFormat.OASIS12;
		case ODT:
			return DocumentFormat.OASIS12;
		case OGA:
			return DocumentFormat.OGG;
		case OGG:
			return DocumentFormat.OGG;
		case PDF:
			return DocumentFormat.PDF;
		case PNG:
			return DocumentFormat.PNG;
		case PPTX:
			return DocumentFormat.SOXML;
		case RTF:
			return DocumentFormat.RTF;
		case SVG:
			return DocumentFormat.SVG;
		case TIFF:
			return DocumentFormat.TIFF;
		case TXT:
			return DocumentFormat.TXT;
		case WEBM:
			return DocumentFormat.WEBM;
		case XLSX:
			return DocumentFormat.SOXML;
		case ZIP:
			return DocumentFormat.ZIP;
		case CSIG:
			return DocumentFormat.CSIG;
		case XSIG:
			return DocumentFormat.XSIG;
		case XML:
			return DocumentFormat.XML;
		}
		return null;
	}

	private FirmaPerfil getFirmaPerfil(String signProfile) throws Exception {
		if ("AdES-BES".equals(signProfile) || "AdES-X1".equals(signProfile) || "AdES-X2".equals(signProfile)
				|| "AdES-XL1".equals(signProfile) || "AdES-XL2".equals(signProfile)
				|| "PAdES-Basic".equals(signProfile))
			return FirmaPerfil.BES;
		if ("AdES-EPES".equals(signProfile))
			return FirmaPerfil.EPES;
		if ("PAdES-LTV".equals(signProfile))
			return FirmaPerfil.LTV;
		if ("AdES-T".equals(signProfile))
			return FirmaPerfil.T;
		if ("AdES-C".equals(signProfile))
			return FirmaPerfil.C;
		if ("AdES-X".equals(signProfile))
			return FirmaPerfil.X;
		if ("AdES-XL".equals(signProfile))
			return FirmaPerfil.XL;
		if ("AdES-A".equals(signProfile))
			return FirmaPerfil.A;
		return null;
	}

	private FirmaTipus getFirmaTipus(String signType, String signFormat) throws Exception {
		if ("XAdES".equals(signType)
				&& ("explicit/detached".equals(signFormat) || "explicit/externally_detached".equals(signFormat)))
			return FirmaTipus.XADES_DET;
		if ("XAdES".equals(signType) && "implicit_enveloping/attached".equals(signFormat))
			return FirmaTipus.XADES_ENV;
		if ("CAdES".equals(signType)
				&& ("explicit/detached".equals(signFormat) || "explicit/externally_detached".equals(signFormat)))
			return FirmaTipus.CADES_DET;
		if ("CAdES".equals(signType) && "implicit_enveloping/attached".equals(signFormat))
			return FirmaTipus.CADES_ATT;
		if ("PAdES".equals(signType))
			return FirmaTipus.PADES;
		if ("ODF".equals(signType))
			return FirmaTipus.ODT;
		if ("OOXML".equals(signType))
			return FirmaTipus.OOXML;
		return null;
	}

	private IArxiuPlugin getArxiuPlugin() {
		LOG.info("Instanciant plugin arxiu...");

		Config config = ConfigProvider.getConfig();

		// per instanciar el plugin necessitam adaptar les propietats
		Properties properties = new Properties();
		properties.setProperty("plugin.arxiu.caib.base.url", config.getValue(PROPERTY_ENDPOINT, String.class));
		properties.setProperty("plugin.arxiu.caib.usuari", config.getValue(PROPERTY_USERNAME, String.class));
		properties.setProperty("plugin.arxiu.caib.contrasenya", config.getValue(PROPERTY_PASS, String.class));
		properties.setProperty("plugin.arxiu.caib.aplicacio.codi", propietats.getProperty(PROPERTY_CODI_APLICACIO));

		return new ArxiuPluginCaib("", properties);
	}

}
