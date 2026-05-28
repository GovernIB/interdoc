package es.caib.interdoc.back.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.back.model.UsuariEntitatModel;
import es.caib.interdoc.service.facade.EntitatServiceFacade;
import es.caib.interdoc.service.facade.UsuariEntitatServiceFacade;
import es.caib.interdoc.service.facade.UsuariServiceFacade;
import es.caib.interdoc.service.model.EntitatDTO;
import es.caib.interdoc.service.model.UsuariDTO;
import es.caib.interdoc.service.model.UsuariEntitatDTO;

import javax.annotation.security.RolesAllowed;
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
@RolesAllowed("ITD_ADMIN")
public class EditUsuariEntitat extends AbstractController implements Serializable {

    private static final long serialVersionUID = -4092311228270716321L;

    private static final Logger LOG = LoggerFactory.getLogger(EditUsuariEntitat.class);

    @EJB
    UsuariEntitatServiceFacade usuariEntitatService;

    @EJB
    private UsuariServiceFacade usuariService;
    @EJB
    private EntitatServiceFacade entitatService;

    @Inject
    private UsuariEntitatModel usuariEntitat;

    // ACCIONS

    /**
     * Actualitza la unitat orgànica que s'està editant. Afegeix un missatge si s'ha fet
     * amb èxit i redirecciona cap a la pàgina de llistat.
     *
     * @return navegació cap al llistat d'unitats orgàniques.
     */
    public String update() {
        LOG.debug("update");

        UsuariEntitatDTO dto = new UsuariEntitatDTO();
        dto.setUsuariEntitatId(usuariEntitat.getUsuariEntitatId());
        dto.setActiu(usuariEntitat.isActiu());
        dto.setUsuariId(usuariEntitat.getUsuariId());
        dto.setEntitatId(usuariEntitat.getEntitatId());
        
        usuariEntitatService.update(dto);
        
        ResourceBundle labelsBundle = getBundle("labels");
        addGlobalMessage(labelsBundle.getString("msg.actualitzaciocorrecta"));

        // Els missatges no aguanten una redirecció ja que no es la mateixa petició
        // Així asseguram que es guardin fins la visualització
        keepMessages();

        // Redireccionam cap al llistat d'aplicacions'
        return "/listUsuariEntitat?faces-redirect=true";
    }

    public UsuariEntitatDTO findByUsuariId(Long usuariId) {
        UsuariEntitatDTO dto = usuariEntitatService.findById(usuariId).orElseThrow();
        return dto;
    }

    // Converteix el model a DTO
    public UsuariEntitatDTO modelToDTO(UsuariEntitatModel model) {
        UsuariEntitatDTO dto = new UsuariEntitatDTO();

        if (model.getUsuariEntitatId() != null) {
            dto = new UsuariEntitatDTO();
            dto.setUsuariEntitatId(model.getUsuariEntitatId());
            dto.setActiu(model.isActiu());
            dto.setUsuariId(model.getUsuariId());
            dto.setEntitatId(model.getEntitatId());
        }else {
            UsuariDTO usuariDTO = usuariService.findByUsername(model.getUsername()).orElseThrow();
            dto.setUsuariId(usuariDTO.getUsuariId());
            
            EntitatDTO entitatDTO = entitatService.findByNom(model.getEntitatNom()).orElseThrow();
            dto.setEntitatId(entitatDTO.getId());
        }
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
}
