package es.caib.interdoc.plugins.firma.api;


import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleSignatureResult;


import es.caib.interdoc.service.model.FitxerDTO;
import es.caib.interdoc.service.model.InfoSignaturaDTO;

public interface InterdocFirmaPlugin extends org.fundaciobit.pluginsib.core.v3.IPluginIB {
	
	public static final String BASE_FIRMA_PLUGIN_PROPERTY = IPLUGINSIB_BASE_PROPERTIES + "firma.";
	
	public InfoSignaturaDTO firmarDocument(FitxerDTO fitxer, String languageUI) throws Exception;
	
	public void printSignatureInfo(FirmaSimpleSignatureResult fssr);

}
