package es.caib.interdoc.back.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.ws.api.Exception_Exception;
import es.caib.interdoc.ws.api.Fitxer;
import es.caib.interdoc.ws.api.Metadada;
import es.caib.interdoc.ws.api.ObtenerReferenciaRequestInfo;
import es.caib.interdoc.ws.api.ObtenerReferenciaWs;
import es.caib.interdoc.ws.api.ObtenerReferenciaWsService;
import es.caib.interdoc.back.model.ReferenciaModel;
import es.caib.interdoc.commons.utils.Base64;
import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.commons.utils.Utils;
import es.caib.interdoc.service.facade.FitxerServiceFacade;
import es.caib.interdoc.service.model.FitxerDTO;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador per la creació d'Unitats Organiques. El definim a l'scope de view perquè a nivell
 * de request es reconstruiria per cada petició AJAX, com ara amb els errors de validació. Amb
 * view es manté mentre no es canvii de vista.
 *
 * @author areus
 */
@Named
@ViewScoped
public class NewReferencia extends AbstractController implements Serializable {

    private static final long serialVersionUID = -4092311228270716325L;

    private static final Logger LOG = LoggerFactory.getLogger(NewReferencia.class);

    @Inject
    private ReferenciaModel referencia;
    
    @EJB
    private FitxerServiceFacade fitxerService; 

    // ACCIONS

    /**
     * Crea o actualitza la unitat orgànica que s'està editant. Afegeix un missatge si s'ha fet
     * amb èxit i redirecciona cap a la pàgina de llistat.
     *
     * @return navegació cap al llistat d'unitats orgàniques.
     */
    public String save() {
        LOG.debug("save");
        
        if (Configuracio.isDesenvolupament()) {
	        LOG.info("SAVE");
	        LOG.info(referencia.getValue().toString());
	        LOG.info("Interessats1 => " + referencia.getInteressats1());
	        LOG.info("Interessats2 => " + referencia.getInteressats2());
	        LOG.info("Interessats3 => " + referencia.getInteressats3());
	        LOG.info("Interessats4 => " + referencia.getInteressats4());
	        LOG.info("Interessats5 => " + referencia.getInteressats5());
	        
	        LOG.info("metadades1 => " + referencia.getM1_clau() + " => " + referencia.getM1_valor());
	        LOG.info("metadades2 => " + referencia.getM2_clau() + " => " + referencia.getM2_valor());
	        LOG.info("metadades3 => " + referencia.getM3_clau() + " => " + referencia.getM3_valor());
	        LOG.info("metadades4 => " + referencia.getM4_clau() + " => " + referencia.getM4_valor());
	        LOG.info("metadades5 => " + referencia.getM5_clau() + " => " + referencia.getM5_valor());
	        LOG.info("metadades6 => " + referencia.getM6_clau() + " => " + referencia.getM6_valor());
	        LOG.info("metadades7 => " + referencia.getM7_clau() + " => " + referencia.getM7_valor());
	        LOG.info("metadades8 => " + referencia.getM8_clau() + " => " + referencia.getM8_valor());
	        LOG.info("metadades9 => " + referencia.getM9_clau() + " => " + referencia.getM9_valor());
	        LOG.info("metadades10 => " + referencia.getM10_clau() + " => " + referencia.getM10_valor());
        }
        
        ObtenerReferenciaRequestInfo infoRequest = new ObtenerReferenciaRequestInfo();
        infoRequest.setAplicacioId(String.valueOf(Constants.INTERDOC_APP_ID));
        
        if (Utils.isNotEmpty(referencia.getValue().getCsvId()))
        	infoRequest.setCsv(referencia.getValue().getCsvId());  
        
        if (Utils.isNotEmpty(referencia.getValue().getUuId()))
        	infoRequest.setUuid(referencia.getValue().getUuId());
        
        infoRequest.setEmisor(referencia.getValue().getEmisor());
        infoRequest.setReceptor(referencia.getValue().getReceptor());
        
        // TODO Entitat
        infoRequest.setEntitatId(referencia.getEntitatDir3());
        
        // Interessats
        if(Utils.isNotEmpty(referencia.getInteressats1()))
        	infoRequest.getInteressats().add(referencia.getInteressats1());
        
        if(Utils.isNotEmpty(referencia.getInteressats2()))
        	infoRequest.getInteressats().add(referencia.getInteressats2());
        
        if(Utils.isNotEmpty(referencia.getInteressats3()))
        	infoRequest.getInteressats().add(referencia.getInteressats3());
        
        if(Utils.isNotEmpty(referencia.getInteressats4()))
        	infoRequest.getInteressats().add(referencia.getInteressats4());
        
        if(Utils.isNotEmpty(referencia.getInteressats5()))
        	infoRequest.getInteressats().add(referencia.getInteressats5());
        
    
        if(referencia.getValue().getFitxerId() != null && referencia.getValue().getFitxerId()>0 ) {

        	final Long fitxerId = referencia.getValue().getFitxerId();
        	
        	// Recuperam el document i ho annexam a la petició
        	Optional<FitxerDTO> fitxerTemp = fitxerService.findById(fitxerId);
        	if (fitxerTemp.isPresent()) {
       
        		FitxerDTO fitxerDto = fitxerTemp.get();
       
        		if (Configuracio.isDesenvolupament()) {
        			LOG.info("FitxerDTO => " + fitxerDto.toString());
        			LOG.info("FitxerDTO-data => " + ( (fitxerDto.getData() != null) ? Base64.encode(fitxerDto.getData()) : "null" ));
        		}
        		if ( fitxerDto != null) {
        			byte[] tempFile = null;
        			// Recuperam el fitxer
        			if (fitxerDto.getRuta() != null) {
        				try {
        					Path p = Paths.get(fitxerDto.getRuta());
        					LOG.info("Path: " + p.toString());
        					tempFile = Files.readAllBytes(p);
        				}catch(IOException e) {
        					LOG.info("Error llegint fitxer");
        					e.printStackTrace();
        				}
        			}
        			
        			Fitxer f = new Fitxer();
        			f.setNom(fitxerDto.getNom());
        			f.setDescripcio(fitxerDto.getDescripcio());
        			f.setMime(fitxerDto.getMime());
        			f.setData(tempFile);
        			f.setTamany(fitxerDto.getTamany());
        			LOG.info("API-Fitxer => " + f.toString() + " tamany: " + f.getData().length + " : " + f.getTamany());
        			infoRequest.setDocument(f);
        			LOG.info("Document InfoRequest => " + Base64.encode(infoRequest.getDocument().getData()));
        		}	
        	}
        }
        
        // METADADES
        if (Utils.isNotEmpty(referencia.getM1_clau()) && Utils.isNotEmpty(referencia.getM1_valor())) {
        	Metadada m1 = new Metadada();
        	m1.setClau(referencia.getM1_clau());
        	m1.setValor(referencia.getM1_valor());
        	infoRequest.getMetadades().add(m1);
        }
        
        if (Utils.isNotEmpty(referencia.getM2_clau()) && Utils.isNotEmpty(referencia.getM2_valor())) {
        	Metadada m2 = new Metadada();
        	m2.setClau(referencia.getM2_clau());
        	m2.setValor(referencia.getM2_valor());
        	infoRequest.getMetadades().add(m2);
        }
        
        if (Utils.isNotEmpty(referencia.getM3_clau()) && Utils.isNotEmpty(referencia.getM3_valor())) {
        	Metadada m3 = new Metadada();
        	m3.setClau(referencia.getM3_clau());
        	m3.setValor(referencia.getM3_valor());
        	infoRequest.getMetadades().add(m3);
        }
        
        if (Utils.isNotEmpty(referencia.getM4_clau()) && Utils.isNotEmpty(referencia.getM4_valor())) {
        	Metadada m4 = new Metadada();
        	m4.setClau(referencia.getM4_clau());
        	m4.setValor(referencia.getM4_valor());
        	infoRequest.getMetadades().add(m4);
        }
        
        if (Utils.isNotEmpty(referencia.getM5_clau()) && Utils.isNotEmpty(referencia.getM5_valor())) {
        	Metadada m5 = new Metadada();
        	m5.setClau(referencia.getM5_clau());
        	m5.setValor(referencia.getM5_valor());
        	infoRequest.getMetadades().add(m5);
        }
        
        if (Utils.isNotEmpty(referencia.getM6_clau()) && Utils.isNotEmpty(referencia.getM6_valor())) {
        	Metadada m6 = new Metadada();
        	m6.setClau(referencia.getM6_clau());
        	m6.setValor(referencia.getM6_valor());
        	infoRequest.getMetadades().add(m6);
        }
        
        if (Utils.isNotEmpty(referencia.getM7_clau()) && Utils.isNotEmpty(referencia.getM7_valor())) {
        	Metadada m7 = new Metadada();
        	m7.setClau(referencia.getM7_clau());
        	m7.setValor(referencia.getM7_valor());
        	infoRequest.getMetadades().add(m7);
        }
        
        if (Utils.isNotEmpty(referencia.getM8_clau()) && Utils.isNotEmpty(referencia.getM8_valor())) {
        	Metadada m8 = new Metadada();
        	m8.setClau(referencia.getM8_clau());
        	m8.setValor(referencia.getM8_valor());
        	infoRequest.getMetadades().add(m8);
        }
        
        if (Utils.isNotEmpty(referencia.getM9_clau()) && Utils.isNotEmpty(referencia.getM9_valor())) {
        	Metadada m9 = new Metadada();
        	m9.setClau(referencia.getM9_clau());
        	m9.setValor(referencia.getM9_valor());
        	infoRequest.getMetadades().add(m9);
        }
        
        if (Utils.isNotEmpty(referencia.getM10_clau()) && Utils.isNotEmpty(referencia.getM10_valor())) {
        	Metadada m10 = new Metadada();
        	m10.setClau(referencia.getM10_clau());
        	m10.setValor(referencia.getM10_valor());
        	infoRequest.getMetadades().add(m10);
        }
        
        String xmlResponse = "";
        try {
        	
        	final String obtenerReferenciaWsBaseUrl = Configuracio.getObtenerReferenciaWsdl()+"?wsdl";
        	final URL obtenerReferenciaUrl = new URL(obtenerReferenciaWsBaseUrl);
        	final QName service = new QName("http://impl.ws.interna.api.interdoc.caib.es/", "ObtenerReferenciaWsService");
        	
        	if (Configuracio.isDesenvolupament()) {
        		LOG.info("ObtenerReferencia Client Base URL => " + obtenerReferenciaWsBaseUrl);
            	LOG.info("ObtenerReferencia Client URLhost => " + obtenerReferenciaUrl.getHost());
            	LOG.info("Request: " + infoRequest.toString());
        	}
        	
			ObtenerReferenciaWsService servei = new ObtenerReferenciaWsService(obtenerReferenciaUrl);
			ObtenerReferenciaWs api = servei.getObtenerReferenciaWs();
			
			Map<String, Object> reqContext = ((BindingProvider) api).getRequestContext();
	        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Configuracio.getObtenerReferenciaWsdl());
	        reqContext.put(BindingProvider.USERNAME_PROPERTY, Configuracio.getObtenerReferenciaUsuari());
	        reqContext.put(BindingProvider.PASSWORD_PROPERTY, Configuracio.getObtenerReferenciaClau());

	        reqContext.put("javax.xml.ws.client.connectionTimeout", 500000L);
	        reqContext.put("javax.xml.ws.client.receiveTimeout", 500000L);
			
			xmlResponse = api.creaReferencia(infoRequest);
			
			if (Configuracio.isDesenvolupament())
				LOG.info(xmlResponse);
			
		} catch (MalformedURLException | Exception_Exception e) {
			e.printStackTrace();
		}

		// Cridam al servei web 
        ResourceBundle labelsBundle = getBundle("labels");
        addGlobalMessage(labelsBundle.getString("msg.creaciocorrecta"));
        addGlobalMessage(xmlResponse);
        
        // Els missatges no aguanten una redirecció ja que no es la mateixa petició
        // Així asseguram que es guardin fins la visualització
        keepMessages();

        // Redireccionam cap al llistat d'unitats orgàniques
        return "/listReferencia?faces-redirect=true";
        // return referencia.getValue().toString();
    }
}
