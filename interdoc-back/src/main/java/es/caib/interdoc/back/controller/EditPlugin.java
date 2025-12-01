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
import java.util.ResourceBundle;

/**
 * Controlador per l'edició d'Unitats Organiques. El definim a l'scope de view perquè a nivell
 * de request es reconstruiria per cada petició AJAX, com ara amb els errors de validació. Amb
 * view es manté mentre no es canvii de vista.
 *
 * @author areus
 */
@Named
@ViewScoped
public class EditPlugin extends AbstractController implements Serializable {

    private static final long serialVersionUID = -4092311228270716321L;

    private static final Logger LOG = LoggerFactory.getLogger(EditPlugin.class);

    @EJB
    PluginServiceFacade pluginService;
    
    @EJB
    private EntitatServiceFacade entitatService;

    @Inject
    private PluginModel plugin;
    
    private String entitatNom;

    // ACCIONS
    
    /**
     * Actualitza la unitat orgànica que s'està editant. Afegeix un missatge si s'ha fet
     * amb èxit i redirecciona cap a la pàgina de llistat.
     *
     * @return navegació cap al llistat d'unitats orgàniques.
     */
    public String update() {
        LOG.debug("update");

        try {
            pluginService.update(plugin.getValue());
                
            ResourceBundle labelsBundle = getBundle("labels");
            addGlobalMessage(labelsBundle.getString("msg.actualitzaciocorrecta"));

            // Els missatges no aguanten una redirecció ja que no es la mateixa petició
            // Així asseguram que es guardin fins la visualització
            keepMessages();

            // Redireccionam cap al llistat d'aplicacions'
            return "/listPlugin?faces-redirect=true";
        } catch (Exception e) {
            LOG.error("Error actualitzant el plugin", e);
            ResourceBundle labelsBundle = getBundle("labels");
            getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                e.getMessage() != null ? e.getMessage() : labelsBundle.getString("error.desconegut"), null));
            return null;
        }
    }
    
    @PostConstruct
    public void init() {
        // Obtenir el nom de l'entitat per mostrar-lo
        Long entitatId = plugin.getValue().getEntitatId();
        if (entitatId != null) {
            EntitatDTO entitat = entitatService.findById(entitatId).orElse(null);
            if (entitat != null) {
                entitatNom = entitat.getNom();
            }
        }
    }
    
    public String getEntitatNom() {
        return entitatNom;
    }
}
