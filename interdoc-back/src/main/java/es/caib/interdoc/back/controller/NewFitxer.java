package es.caib.interdoc.back.controller;

import es.caib.interdoc.service.facade.FitxerServiceFacade;
import es.caib.interdoc.service.model.FitxerDTO;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Controlador per la creació d'Unitats Organiques. El definim a l'scope de view perquè a nivell
 * de request es reconstruiria per cada petició AJAX, com ara amb els errors de validació. Amb
 * view es manté mentre no es canvii de vista.
 *
 * @author areus
 */
@Named
@ViewScoped
//  extends AbstractController 
public class NewFitxer implements Serializable {

    private static final long serialVersionUID = 419231122827071636L;

    private static final Logger LOG = LoggerFactory.getLogger(NewFitxer.class);
    
    private UploadedFile fichero;
    
    @EJB
    FitxerServiceFacade fitxerService;

    public UploadedFile getFichero() {
        return fichero;
    }
 
    public void setFichero(UploadedFile fichero) {
        this.fichero = fichero;
    }
 

    // ACCIONS
    
    public String upload() {
    	
    	LOG.info("NewFitxer.upload INICIO");    	
    	
        if (this.fichero != null) {
        	
        	LOG.info("file data uploaded: " + fichero.getContent().toString());
        	
            FacesMessage message = new FacesMessage("Successful", fichero.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
            FitxerDTO fitxer = new FitxerDTO();
            fitxer.setNom(fichero.getFileName());
            fitxer.setDescripcio(fichero.getFileName());
            fitxer.setData(fichero.getContent());
            fitxer.setTamany( Long.valueOf(fichero.getContent().length));
            fitxer.setDataCreacio(LocalDate.now());
        
            LOG.info("Nou fitxer: " + fitxer.toString());
            fitxerService.create(fitxer);
            
            return fitxer.toString();
        }else{
        	LOG.info("File is null");
        	
        	FacesMessage message = new FacesMessage("Error", "File is null.");
        	FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
        LOG.info("NewFitxer.upload FIN");
        return "AAA";
        
    	
    }
    
    public void handleFileUpload(FileUploadEvent event) {
    	LOG.info("NewFitxer.upload MULTIPLE INICIO ");
        FacesMessage msg = new FacesMessage("Successful 2", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        LOG.info("NewFitxer.upload Multiple FIN");
    }

}
