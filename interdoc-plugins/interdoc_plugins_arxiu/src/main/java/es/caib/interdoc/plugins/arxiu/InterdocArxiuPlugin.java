package es.caib.interdoc.plugins.arxiu;

import java.util.Properties;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.service.model.InfoArxiuDTO;
import es.caib.plugins.arxiu.api.Document;
import es.caib.plugins.arxiu.api.Expedient;


/**
 * Interficie de les operacions que podem fer amb el plugin d'arxiu
 * @author jagarcia
 *
 */

public interface InterdocArxiuPlugin extends org.fundaciobit.pluginsib.core.IPlugin {
	
	public static final String INTERDOC_ARXIU_PLUGIN_PROPERTY = Constants.INTERDOC_PROPERTY_BASE + IPLUGIN_BASE_PROPERTIES + "arxiu.";
	
	public void carregarProperties(Properties props);
	
	public void carregarPropertiesFile();

	public String crearExpedient(DocumentInfo documentInfo) throws DocumentNotValidException;
	
	public Expedient getExpediente(String identificador, String version);
	
	public boolean borrarExpedient(String identificador);
	
	public String crearDocument(DocumentInfo documentInfo, String expedientId) throws DocumentNotValidException, Exception;
	
	public Document getDocument(String identificador, String version, Boolean contenido, Boolean original);
	
	public InfoArxiuDTO consultarDocument(String documentId, String expedientId) throws Exception;
	
	public String generarEniDoc(String identificador);
	
	public Document descarregarDocument(String identificador);
	
	public String tancarExpedient(String identificador);

	boolean tancarExpedientIfProperty(String identificador);

	void tancarExpedient(String identificador, Long entitatId) throws Exception;
}
