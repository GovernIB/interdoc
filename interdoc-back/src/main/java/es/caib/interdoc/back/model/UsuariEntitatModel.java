package es.caib.interdoc.back.model;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.back.controller.NewUsuariEntitat;
import es.caib.interdoc.service.facade.EntitatServiceFacade;
import es.caib.interdoc.service.facade.UsuariEntitatServiceFacade;
import es.caib.interdoc.service.facade.UsuariServiceFacade;
import es.caib.interdoc.service.model.UsuariDTO;
import es.caib.interdoc.service.model.UsuariEntitatDTO;

import java.io.Serializable;

@Named
@ViewScoped
public class UsuariEntitatModel implements Serializable {

    private static final long serialVersionUID = 98L;
    
    private static final Logger LOG = LoggerFactory.getLogger(UsuariEntitatModel.class);

    
    private Long usuariEntitatId;
    
    private Long usuariId;
    
    private String username;
    
    private Long entitatId;
    
    private String entitatNom;
    
    private boolean actiu;
    

    @EJB
    private UsuariEntitatServiceFacade usuariEntitatService;
    
    @EJB
    private UsuariServiceFacade usuariService;
    @EJB
    private EntitatServiceFacade entitatService;
    
    public UsuariEntitatModel() {}

    public UsuariEntitatModel(Long usuariEntitatId, boolean actiu,
                              String username, String entitatNom) {
        this.usuariEntitatId = usuariEntitatId;
        this.actiu = actiu;
        this.username = username;
        this.entitatNom = entitatNom;
    }

    public Long getUsuariEntitatId() {
        return usuariEntitatId;
    }
    public void setUsuariEntitatId(Long usuariEntitatId) {
        this.usuariEntitatId = usuariEntitatId;
    }

    public boolean isActiu() {
        return actiu;
    }
    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEntitatNom() {
        return entitatNom;
    }
    public void setEntitatNom(String entitatNom) {
        this.entitatNom = entitatNom;
    }
    
    public Long getUsuariId() {
        return usuariId;
    }
    
    public void setUsuariId(Long usuariId) {
        this.usuariId = usuariId;
    }
    
    public Long getEntitatId() {
        return entitatId;
    }
    
    public void setEntitatId(Long entitatId) {
        this.entitatId = entitatId;
    }
    
    
    public void load() {
        
        if (usuariEntitatId == null) {
            throw new IllegalArgumentException("UsuariEntitatId is null");
        }
        UsuariEntitatDTO dto = usuariEntitatService.findById(usuariEntitatId).orElseThrow();

        // Obtenir username i nom entitat a partir dels ids.
        this.username = usuariService.findById(dto.getUsuariId()).get().getUsername();
        this.entitatNom = entitatService.findById(dto.getEntitatId()).get().getNom();
        this.actiu = dto.isActiu();
        
        this.entitatId = dto.getEntitatId();
        this.usuariId = dto.getUsuariId();
        
    }
    
    @PostConstruct
    public void postConstruct() {
        this.setActiu(true);
    }

    
  
}
