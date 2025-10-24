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
import es.caib.interdoc.service.facade.EntitatServiceFacade;
import es.caib.interdoc.service.facade.UsuariEntitatServiceFacade;
import es.caib.interdoc.service.facade.UsuariServiceFacade;
import es.caib.interdoc.service.model.UsuariDTO;
import es.caib.interdoc.service.model.UsuariEntitatDTO;

@Named
@ViewScoped
@RolesAllowed("ITD_ADMIN")
public class NewUsuariEntitat extends AbstractController implements Serializable{

    private static final long serialVersionUID = -4092311228270716322L;
    
    private static final Logger LOG = LoggerFactory.getLogger(NewUsuariEntitat.class);

    
    @EJB
    UsuariEntitatServiceFacade usuariEntitatService;
    
    @EJB
    private UsuariServiceFacade usuariService;
    @EJB
    private EntitatServiceFacade entitatService;    
    
    @Inject
    private UsuariEntitatModel usuariEntitat;
    
    
    /**
     * Crea o actualitza la unitat orgànica que s'està editant. Afegeix un missatge si s'ha fet
     * amb èxit i redirecciona cap a la pàgina de llistat.
     *
     * @return navegació cap al llistat d'unitats orgàniques.
     */
    
    
 // Converteix el model a DTO
    public UsuariEntitatDTO modelToDTO(UsuariEntitatModel model) {
        UsuariEntitatDTO dto = new UsuariEntitatDTO();

        dto.setActiu(model.isActiu());
        String username = model.getUsername();

        UsuariDTO usuariDTO = usuariService.findByUsername(username).orElseThrow();
        dto.setUsuariId(usuariDTO.getUsuariId());

        dto.setEntitatId(entitatService.findByNom(model.getEntitatNom()).get().getId());
        return dto;
    }
    
    
    public UsuariEntitatModel dtoToModel(UsuariEntitatDTO dto) {
        UsuariEntitatModel model = new UsuariEntitatModel();
        
        model.setUsuariId(dto.getUsuariId());
        model.setEntitatId(dto.getEntitatId());
        model.setActiu(dto.isActiu());
        
        model.setUsername(usuariService.findById(dto.getUsuariId()).get().getUsername());
        model.setEntitatNom(entitatService.findById(dto.getEntitatId()).get().getNom());
        
        return model;
    }
    
    
    public String save() {
        
        UsuariEntitatDTO usuariEntitatDTO =modelToDTO(usuariEntitat);
        
        // Feim una creació a BBDD
        usuariEntitatService.create(usuariEntitatDTO);

        ResourceBundle labelsBundle = getBundle("labels");
        addGlobalMessage(labelsBundle.getString("msg.creaciocorrecta"));
        
        // Els missatges no aguanten una redirecció ja que no es la mateixa petició
        // Així asseguram que es guardin fins la visualització
        keepMessages();

        // Redireccionam cap al llistat d'unitats orgàniques
        return "/listUsuariEntitat?faces-redirect=true";
    }
    
    
}
