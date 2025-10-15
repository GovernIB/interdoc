package es.caib.interdoc.back.controller;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.back.model.UsuariEntitatModel;
import es.caib.interdoc.service.facade.UsuariEntitatServiceFacade;

@Named
@ViewScoped
@RolesAllowed("ITD_ADMIN")
public class NewUsuariEntitat extends AbstractController implements Serializable{

    private static final long serialVersionUID = -4092311228270716322L;
    
    private static final Logger LOG = LoggerFactory.getLogger(NewUsuariEntitat.class);

    
    @EJB
    UsuariEntitatServiceFacade usuariEntitatService;
    
    @Inject
    private UsuariEntitatModel usuariEntitat;
    
    
    /**
     * Crea o actualitza la unitat orgànica que s'està editant. Afegeix un missatge si s'ha fet
     * amb èxit i redirecciona cap a la pàgina de llistat.
     *
     * @return navegació cap al llistat d'unitats orgàniques.
     */
    public String save() {
        LOG.debug("save");
        
        // Feim una creació 
        usuariEntitatService.create(usuariEntitat.getValue());

        ResourceBundle labelsBundle = getBundle("labels");
        addGlobalMessage(labelsBundle.getString("msg.creaciocorrecta"));
        
        // Els missatges no aguanten una redirecció ja que no es la mateixa petició
        // Així asseguram que es guardin fins la visualització
        keepMessages();

        // Redireccionam cap al llistat d'unitats orgàniques
        return "/listUsuariEntitat?faces-redirect=true";
    }
    
    
}
