package es.caib.interdoc.back.controller;

import es.caib.interdoc.back.model.PluginModel;
import es.caib.interdoc.service.facade.EntitatServiceFacade;
import es.caib.interdoc.service.facade.PluginServiceFacade;
import es.caib.interdoc.service.model.EntitatDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
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
public class NewPlugin extends AbstractController implements Serializable {

    private static final long serialVersionUID = -4092311228270716321L;

    private static final Logger LOG = LoggerFactory.getLogger(NewPlugin.class);

    @EJB
    PluginServiceFacade pluginService;
    
    @EJB
    private EntitatServiceFacade entitatService;

    @Inject
    private PluginModel plugin;
    
    @Inject
    private UserLocale userLocale;
    
    private String entitatNom;
   
    // ACCIONS

    /**
     * Crea o actualitza la unitat orgànica que s'està editant. Afegeix un missatge si s'ha fet
     * amb èxit i redirecciona cap a la pàgina de llistat.
     *
     * @return navegació cap al llistat d'unitats orgàniques.
     */
    public String save() {
        LOG.debug("save");
        
        try {
            // Feim una creació 
            pluginService.create(plugin.getValue());

            ResourceBundle labelsBundle = getBundle("labels");
            addGlobalMessage(labelsBundle.getString("msg.creaciocorrecta"));
            
            // Els missatges no aguanten una redirecció ja que no es la mateixa petició
            // Així asseguram que es guardin fins la visualització
            keepMessages();

            // Redireccionam cap al llistat d'unitats orgàniques
            return "/listPlugin?faces-redirect=true";
        } catch (Exception e) {
            LOG.error("Error creant el plugin", e);
            ResourceBundle labelsBundle = getBundle("labels");
            getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                e.getMessage() != null ? e.getMessage() : labelsBundle.getString("error.desconegut"), null));
            return null;
        }
    }
    
    @PostConstruct
    public void init() {
        Long entitatIdSeleccionada = userLocale.getEntitatId();
        
        if (entitatIdSeleccionada == null) {
            LOG.warn("No hi ha cap entitat seleccionada per l'usuari");
            ResourceBundle labelsBundle = getBundle("labels");
            getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                labelsBundle.getString("msg.plugin_entitat_requerida"), null));
            return;
        }
        
        // Assignar l'entitat seleccionada al plugin
        plugin.getValue().setEntitatId(entitatIdSeleccionada);
        
        // Assignar la data actual al plugin
        plugin.getValue().setDataCreacio(LocalDate.now());
        
        // Obtenir el nom de l'entitat per mostrar-lo
        EntitatDTO entitat = entitatService.findById(entitatIdSeleccionada).orElse(null);
        if (entitat != null) {
            entitatNom = entitat.getNom();
        }
    }
    
    public String getEntitatNom() {
        return entitatNom;
    }
}
