package es.caib.interdoc.plugins.arxiu;

import es.caib.plugins.arxiu.api.*;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

@Named
@SessionScoped
public class ArxiuController implements Serializable {

	private static final long serialVersionUID = 79021423959104732L;

	private static final Logger LOG = LoggerFactory.getLogger(ArxiuController.class);

	private String serieDocumental;
	private String classificacio;

	@Inject
	private IArxiuPlugin plugin;

	public ArxiuController() {
		super();

		Config config = ConfigProvider.getConfig();
		setSerieDocumental(config.getValue("es.caib.interdoc.plugins.arxiu.serieDocumental", String.class));
		setClassificacio(config.getValue("es.caib.interdoc.plugins.arxiu.classificacio", String.class));
		LOG.info("inicialitzan properties");
		LOG.info("serieDocumental => " + getSerieDocumental());
		LOG.info("Classificacio => " + getClassificacio());

	}

	public String getSerieDocumental() {
		return serieDocumental;
	}

	public void setSerieDocumental(String serieDocumental) {
		this.serieDocumental = serieDocumental;
	}

	public String getClassificacio() {
		return classificacio;
	}

	public void setClassificacio(String classificacio) {
		this.classificacio = classificacio;
	}

	public String crearExpedient(DocumentInfo documentInfo) throws DocumentNotValidException {

		LOG.info("createExpedient");

		if (documentInfo == null) {
			LOG.info("documentInfo null");
			throw new DocumentNotValidException();
		}

		Expedient expedient = new Expedient();
		expedient.setNom(documentInfo.getNom());

		ExpedientMetadades metadades = new ExpedientMetadades();
		metadades.setOrgans(documentInfo.getOrgans());
		metadades.setDataObertura(new Date());
		metadades.setClassificacio(classificacio);
		metadades.setEstat(ExpedientEstat.OBERT);
		metadades.setInteressats(documentInfo.getInteressats());
		metadades.setSerieDocumental(serieDocumental);
		expedient.setMetadades(metadades);

		if (plugin == null) {
			ArxiuPluginProducer producer = new ArxiuPluginProducer();
			plugin = producer.getArxiuPlugin();
		}

		ContingutArxiu expedientCreat = plugin.expedientCrear(expedient);

		LOG.info(" ==============  EXPEDIENT CREAT ================== ");

		LOG.info("expedientCreat.identificador => " + expedientCreat.getIdentificador());
		LOG.info("expedientCreat.nom => " + expedientCreat.getNom());
		LOG.info("expedientCreat.descripcio => " + expedientCreat.getDescripcio());
		LOG.info("expedientCreat.versio => " + expedientCreat.getVersio());

		ContingutTipus contingutTipus = expedientCreat.getTipus();
		LOG.info("Contingut Tipus => " + contingutTipus.toString());

		LOG.info(" ================================================== ");

		if (documentInfo.getFitxer().getTamany() > 0) {

			Fitxer fitxerOriginal = documentInfo.getFitxer();
			DocumentContingut fitxer = new DocumentContingut();

			es.caib.plugins.arxiu.api.Document documentPerCrear = new es.caib.plugins.arxiu.api.Document();
			documentPerCrear.setNom(fitxerOriginal.getArxiuNom());
			documentPerCrear.setEstat(DocumentEstat.ESBORRANY);

			DocumentMetadades documentMetadades = new DocumentMetadades();
			documentMetadades.setOrigen(Origen.toContingutOrigen(documentInfo.getOrigen()));
			documentMetadades.setOrgans(documentInfo.getOrgans());
			documentMetadades.setDataCaptura(new Date());
			documentMetadades.setEstatElaboracio(DocumentEstatElaboracio.ORIGINAL);
			documentMetadades.setTipusDocumental(DocumentTipus.ALTRES);
			documentMetadades.setFormat(DocumentFormat.PDF);
			documentMetadades.setExtensio(DocumentExtensio.PDF);
			documentPerCrear.setMetadades(documentMetadades);

			// Cream el contingut del document a partir del fitxer rebut
			DocumentContingut documentContingut = new DocumentContingut();
			documentContingut.setTipusMime(fitxerOriginal.getTipusMime());
			documentContingut.setArxiuNom(fitxerOriginal.getArxiuNom());
			documentContingut.setTamany(fitxerOriginal.getTamany());
			documentContingut.setContingut(fitxerOriginal.getContingut());
			documentPerCrear.setContingut(fitxer);

			// Cream el document dins l'expedient creat.
			// Capturam l'excepció per evitar que afecti la consulta de l'expedient
			try {
				plugin.documentCrear(documentPerCrear, expedientCreat.getIdentificador());
			} catch (ArxiuException ae) {
				LOG.error("Error creant document", ae);
			}
		}

		return expedientCreat.getIdentificador();
	}

	/*
	 * public String crearExpedient(String nom, Part document) {
	 * 
	 * LOG.info("createExpedient");
	 * 
	 * Expedient expedient = new Expedient(); expedient.setNom(nom);
	 * 
	 * ExpedientMetadades metadades = new ExpedientMetadades();
	 * metadades.setOrgans(organs); metadades.setDataObertura(new Date());
	 * metadades.setClassificacio(classificacio);
	 * metadades.setEstat(ExpedientEstat.OBERT);
	 * metadades.setInteressats(interessats);
	 * metadades.setSerieDocumental(serieDocumental);
	 * expedient.setMetadades(metadades);
	 * 
	 * if (plugin == null) { ArxiuPluginProducer producer = new
	 * ArxiuPluginProducer(); plugin = producer.getArxiuPlugin(); }
	 * 
	 * ContingutArxiu expedientCreat = plugin.expedientCrear(expedient);
	 * 
	 * if(document != null){
	 * 
	 * Document documentPerCrear = new Document();
	 * documentPerCrear.setNom(document.getSubmittedFileName());
	 * documentPerCrear.setEstat(DocumentEstat.ESBORRANY);
	 * 
	 * DocumentMetadades documentMetadades = new DocumentMetadades();
	 * documentMetadades.setOrigen(ContingutOrigen.CIUTADA);
	 * documentMetadades.setOrgans(organs); documentMetadades.setDataCaptura(new
	 * Date());
	 * documentMetadades.setEstatElaboracio(DocumentEstatElaboracio.ORIGINAL);
	 * documentMetadades.setTipusDocumental(DocumentTipus.ALTRES);
	 * documentMetadades.setFormat(DocumentFormat.PDF);
	 * documentMetadades.setExtensio(DocumentExtensio.PDF);
	 * documentPerCrear.setMetadades(documentMetadades);
	 * 
	 * // Cream el contingut del document a partir del fitxer rebut
	 * DocumentContingut documentContingut = new DocumentContingut();
	 * documentContingut.setTipusMime(document.getContentType());
	 * 
	 * // TODO L'arxiuNom no es guarda // Veure:
	 * https://github.com/GovernIB/pluginsib-arxiu/issues/20 //
	 * documentContingut.setArxiuNom(file.getSubmittedFileName());
	 * 
	 * documentContingut.setTamany(document.getSize()); try (InputStream inputStream
	 * = document.getInputStream()) { ByteArrayOutputStream baos = new
	 * ByteArrayOutputStream((int) document.getSize());
	 * inputStream.transferTo(baos);
	 * documentContingut.setContingut(baos.toByteArray()); } catch (IOException ioe)
	 * { throw new RuntimeException(ioe); }
	 * documentPerCrear.setContingut(documentContingut);
	 * 
	 * // Cream el document dins l'expedient creat. // Capturam l'excepció per
	 * evitar que afecti la consulta de l'expedient try {
	 * plugin.documentCrear(documentPerCrear, expedientCreat.getIdentificador()); }
	 * catch (ArxiuException ae) { LOG.error("Error creant document", ae); } }
	 * 
	 * return expedientCreat.getIdentificador(); }
	 */

	public boolean borrarExpedient(String identificador) {
		LOG.info("borrarExpedient({})", identificador);
		if (identificador != null) {
			plugin.expedientEsborrar(identificador);
			return true;
		}
		return false;
	}

	public es.caib.plugins.arxiu.api.Document descarregarDocument(String identificador) {
		LOG.info("descarregarDocument({})", identificador);
		es.caib.plugins.arxiu.api.Document document = plugin.documentDetalls(identificador, null, true);
		return document;
	}

	/*
	 * public String testCrearExpedient() throws Exception{
	 * 
	 * LOG.info("TestCreateExpedient");
	 * 
	 * String expedientIdentificador = null;
	 * 
	 * try{
	 * 
	 * Expedient expedient = new Expedient(); expedient.setNom("prova_exp_" +
	 * System.currentTimeMillis());
	 * 
	 * ExpedientMetadades metadades = new ExpedientMetadades();
	 * metadades.setOrgans(Arrays.asList("A04013511"));
	 * metadades.setDataObertura(new Date());
	 * metadades.setClassificacio("organo1_PRO_123456789");
	 * metadades.setEstat(ExpedientEstat.OBERT);
	 * metadades.setInteressats(Arrays.asList("12345678Z","00000000T"));
	 * metadades.setSerieDocumental("S0001"); expedient.setMetadades(metadades);
	 * 
	 * if (plugin == null) { LOG.info("inicialitzam plugin arxiu");
	 * ArxiuPluginProducer producer = new ArxiuPluginProducer(); plugin =
	 * producer.getArxiuPlugin(); }
	 * 
	 * ContingutArxiu expedientCreat = plugin.expedientCrear(expedient);
	 * 
	 * LOG.info("expedientCreat: " + expedientCreat );
	 * LOG.info("expedientIdentificador: " + expedientCreat.getIdentificador());
	 * 
	 * Fitxer documentPerCrear = new Fitxer(); documentPerCrear.setNom("prova_doc_"
	 * + System.currentTimeMillis());
	 * documentPerCrear.setEstat(DocumentEstat.ESBORRANY);
	 * 
	 * DocumentMetadades documentMetadades = new DocumentMetadades();
	 * documentMetadades.setOrigen(ContingutOrigen.CIUTADA);
	 * documentMetadades.setOrgans(Arrays.asList("A04013511"));
	 * documentMetadades.setDataCaptura(new Date());
	 * documentMetadades.setEstatElaboracio(DocumentEstatElaboracio.ORIGINAL);
	 * documentMetadades.setTipusDocumental(DocumentTipus.ALTRES);
	 * documentMetadades.setFormat(DocumentFormat.PDF);
	 * documentMetadades.setExtensio(DocumentExtensio.PDF);
	 * documentPerCrear.setMetadades(documentMetadades);
	 * 
	 * // Cream el contingut del document a partir del fitxer rebut
	 * DocumentContingut documentContingut = new DocumentContingut();
	 * documentContingut.setTipusMime("application/pdf");
	 * documentContingut.setArxiuNom("document_test.pdf");
	 * 
	 * try (InputStream inputStream =
	 * ArxiuController.class.getResourceAsStream("document_test.pdf")) {
	 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 * inputStream.transferTo(baos);
	 * documentContingut.setContingut(baos.toByteArray());
	 * documentContingut.setTamany(baos.size()); } catch (IOException ioe) { throw
	 * new RuntimeException(ioe); }
	 * documentPerCrear.setContingut(documentContingut);
	 * 
	 * try { plugin.documentCrear(documentPerCrear,
	 * expedientCreat.getIdentificador());
	 * 
	 * LOG.info("DocumentCreat: " + documentPerCrear);
	 * LOG.info("Id Document Creat: " + documentPerCrear.getIdentificador());
	 * 
	 * } catch (ArxiuException ae) { LOG.error("Error creant document", ae); }
	 * 
	 * expedientIdentificador = expedientCreat.getIdentificador();
	 * 
	 * } finally{ if(expedientIdentificador != null){
	 * plugin.expedientEsborrar(expedientIdentificador); } } return
	 * expedientIdentificador; }
	 */
}
