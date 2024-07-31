package es.caib.interdoc.plugins.apifirmasimple;

import java.util.Properties;

import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleSignatureResult;

import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.service.model.FitxerDTO;
import es.caib.interdoc.service.model.InfoSignaturaDTO;

public interface InterdocFirmaPlugin extends org.fundaciobit.pluginsib.core.v3.IPluginIB {
	
	public static final String INTERDOC_FIRMA_PLUGIN_PROPERTY = Constants.INTERDOC_PROPERTY_BASE + IPLUGINSIB_BASE_PROPERTIES + "firma.";
	
	public InfoSignaturaDTO firmarDocument(FitxerDTO fitxer) throws Exception;
	
	public void carregarProperties(Properties props);
	
	public void getAvailableProfiles() throws Exception;
	
	public void printSignatureInfo(FirmaSimpleSignatureResult fssr);

}
