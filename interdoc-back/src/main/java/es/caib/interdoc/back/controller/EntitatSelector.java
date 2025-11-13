package es.caib.interdoc.back.controller;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.service.facade.EntitatServiceFacade;
import es.caib.interdoc.service.facade.UsuariServiceFacade;
import es.caib.interdoc.service.model.EntitatDTO;
import es.caib.interdoc.service.model.UsuariDTO;

@Named
@RequestScoped
public class EntitatSelector implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static final Logger LOG = LoggerFactory.getLogger(EntitatSelector.class);


    @EJB
    private UsuariServiceFacade usuariService;

    @EJB
    private EntitatServiceFacade entitatService;

    @Inject
    private UserLocale userLocale;

    private Long selectedEntitatId;
    public Long getSelectedEntitatId(){ return selectedEntitatId; }
    public void setSelectedEntitatId(Long id){ this.selectedEntitatId = id; }

    
    
    public String applySelection() {
        
        Long entitatId = this.getSelectedEntitatId();
        Long usuariId = userLocale.getUsuariId();
        
        if (entitatId != null && usuariId != null) {
            EntitatDTO entitat = entitatService.findById(entitatId).orElse(null);
            if (entitat != null) {
                UsuariDTO usuari = usuariService.findById(usuariId).orElse(null);
                
                usuari.setDarreraEntitat(entitatId);
                usuariService.update(usuari);
                
                userLocale.setEntitatId(entitat.getId());
                userLocale.setEntitatNom(entitat.getNom());
            }
        }
        return "/listReferencia?faces-redirect=true";
    }
}
