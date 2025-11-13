package es.caib.interdoc.back.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.service.facade.EntitatServiceFacade;
import es.caib.interdoc.service.facade.UsuariEntitatServiceFacade;
import es.caib.interdoc.service.facade.UsuariServiceFacade;
import es.caib.interdoc.service.model.EntitatDTO;
import es.caib.interdoc.service.model.UsuariDTO;
import es.caib.interdoc.service.model.UsuariEntitatDTO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;

/**
 * Bean per mantenir el locale de l'usuari.
 *
 * @author areus
 */
@Named
@SessionScoped
public class UserLocale implements Serializable {

    private static final long serialVersionUID = -3709390221710580769L;

    private static final Logger LOG = LoggerFactory.getLogger(UserLocale.class);

    /** Locale actual de l'usuari */
    private Locale current;
    
    private String username;
    private Long usuariId;
    
    private Long entitatId;
    private String entitatNom;
    
    @EJB
    UsuariServiceFacade usuariService;
    
    @EJB
    EntitatServiceFacade entitatService;
    
    @EJB
    UsuariEntitatServiceFacade usuariEntitatService;
    
    
 // Mètodes

    /**
     * Inicialització del locale de l'usuari.
     */
    @PostConstruct
    private void init() {
        
        LOG.info("Inicialitzant locale de l'usuari");
        FacesContext context = FacesContext.getCurrentInstance();
        if (context == null) {
            LOG.warn("FacesContext és null a @PostConstruct; s'usarà locale per defecte");
            this.current = Locale.getDefault();
            return;
        }
        
        Application app = context.getApplication();
        current = app.getViewHandler().calculateLocale(context);
        
        
        if (context.getExternalContext().getUserPrincipal() == null) {
            LOG.debug("Sense usuari autenticat");
            return;
        }
        
        // Obtenim l'usuari autenticat del context
        this.username = context.getExternalContext().getUserPrincipal().getName();
        
        // Obtenim les dades de l'usuari autenticat.
        UsuariDTO usuari = usuariService.findByUsername(username).orElse(null);
        if (usuari == null) {
            LOG.warn("No s'ha trobat UsuariDTO per username {}", username);
            return;
        }
        
        this.usuariId  = usuari.getUsuariId();
        this.username  = usuari.getUsername();
        this.entitatId = usuari.getDarreraEntitat();
        
        //Si no hi ha entitat seleccionada, s'assigna una entitat qualsevol de les de l'usuari.
        if (this.entitatId == null) {
            UsuariEntitatDTO defaultUsuariEntitat = usuariEntitatService.findByUsuariId(usuariId).orElse(null);
            
            
            if(defaultUsuariEntitat!=null) {
                   this.entitatId = defaultUsuariEntitat.getEntitatId();
                   this.entitatNom = entitatService.findById(this.entitatId).map(EntitatDTO::getNom).orElse(null);
            }else {
                //Si no hi ha entitat seleccionada, miram si el rol es superadmin.
                //Si es superadmin, entra sense entitat.
                if (context.getExternalContext().isUserInRole("ITD_ADMIN")) {
                    LOG.info("L'usuari {} és superadmin. Entrant sense entitat.", this.username);
                    this.entitatNom = null;
                    return;
                }else {
                    this.entitatNom = null;
                    LOG.warn("L'usuari {} no té entitat assignada.", this.username);
                    throw new IllegalStateException("L'usuari no té entitat assignada.");
                }
            }
            return;
            
        }else{
            //Si l'usuari te darreraEntitat assignada, l'utilitzam.
            EntitatDTO entitat = entitatService.findById(entitatId).orElse(null);
           
            if (entitat != null) {
                
                this.entitatNom = entitat.getNom();

            } else {

                UsuariEntitatDTO usuariEntitat = usuariEntitatService.findByUsuariId(usuariId).orElse(null);
                if(usuariEntitat!= null) {
                    EntitatDTO entitat2 = entitatService.findById(usuariEntitat.getEntitatId()).orElse(null);
                    if (entitat2 != null) {
                        this.entitatNom = entitat2.getNom();
                        this.entitatId = entitat2.getId();
                        return;
                    }
                }
                
                LOG.warn("No s'ha trobat cap entitat amb id {}", this.entitatId);
                this.entitatNom = null; // o un valor per defecte
            }
            }
        }
        
    

    public void reload() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getPartialViewContext().getEvalScripts()
                .add("location.replace(location)");
    }
    
    public void clear() {
        username = null;
        usuariId = null;
        entitatId = null;
        entitatNom = null;
    }
    
    public Locale getCurrent() {
        return current;
    }

    public void setCurrent(Locale current) {
        this.current = current;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
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
    
    public String getEntitatNom() {
        return entitatNom;
    }
    
    public void setEntitatNom(String entitatNom) {
        this.entitatNom = entitatNom;
    }

    
}
