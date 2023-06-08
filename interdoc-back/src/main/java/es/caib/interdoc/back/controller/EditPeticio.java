package es.caib.interdoc.back.controller;

import es.caib.interdoc.back.model.AplicacioModel;
import es.caib.interdoc.back.model.PeticioModel;
import es.caib.interdoc.service.facade.PeticioATercerServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
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
public class EditPeticio extends AbstractController implements Serializable {

    private static final long serialVersionUID = -4092311228270716321L;

    private static final Logger LOG = LoggerFactory.getLogger(EditPeticio.class);

    @EJB
    PeticioATercerServiceFacade peticioService;

    @Inject
    private PeticioModel peticio;

    // ACCIONS
    
    /**
     * Actualitza la unitat orgànica que s'està editant. Afegeix un missatge si s'ha fet
     * amb èxit i redirecciona cap a la pàgina de llistat.
     *
     * @return navegació cap al llistat d'unitats orgàniques.
     */
    public String update() {
        LOG.debug("update");

        peticioService.update(peticio.getValue());
            
        ResourceBundle labelsBundle = getBundle("labels");
        addGlobalMessage(labelsBundle.getString("msg.actualitzaciocorrecta"));

        // Els missatges no aguanten una redirecció ja que no es la mateixa petició
        // Així asseguram que es guardin fins la visualització
        keepMessages();

        // Redireccionam cap al llistat d'aplicacions'
        return "/listPeticio?faces-redirect=true";
    }
}
