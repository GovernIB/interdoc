package es.caib.interdoc.back.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.back.security.Security;
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
import javax.faces.context.ExternalContext;
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
    
    /**
     * Rol seleccionat actualment per l'usuari.
     * Pot ser "SUPER_ADMIN" o "ADMIN_ENTITAT".
     */
    private String selectedRole;
    
    @EJB
    UsuariServiceFacade usuariService;
    
    @EJB
    EntitatServiceFacade entitatService;
    
    @EJB
    UsuariEntitatServiceFacade usuariEntitatService;
    
    @Inject
    Security security;
    
    
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
                if (security.isAdmin()) {
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
        
        // Inicialitzar rol per defecte
        initializeDefaultRole();
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
    
    // Mètodes de gestió de rols
    
    /**
     * Inicialitza el rol per defecte segons els rols de l'usuari.
     */
    private void initializeDefaultRole() {
        if (security.isAdmin()) {
            selectedRole = "SUPER_ADMIN";
        } else if (security.isUser()) {
            selectedRole = "ADMIN_ENTITAT";
        } else {
            LOG.warn("L'usuari no té cap rol vàlid assignat");
            selectedRole = "ADMIN_ENTITAT"; // Per defecte
        }
    }
    
    /**
     * Retorna el rol seleccionat actualment.
     * Si no s'ha seleccionat cap, determina el rol per defecte.
     */
    public String getSelectedRole() {
        if (selectedRole == null) {
            initializeDefaultRole();
        }
        return selectedRole;
    }
    
    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }
    
    /**
     * Retorna l'etiqueta a mostrar per al rol seleccionat.
     */
    public String getCurrentRoleLabel() {
        String role = getSelectedRole();
        
        FacesContext context = FacesContext.getCurrentInstance();
        if (context == null) {
            return "";
        }
        
        if ("SUPER_ADMIN".equals(role)) {
            return context.getApplication()
                    .evaluateExpressionGet(context, "#{labels.usuari_administrador}", String.class);
        } else if ("ADMIN_ENTITAT".equals(role)) {
            return context.getApplication()
                    .evaluateExpressionGet(context, "#{labels.usuari_administrador_entitat}", String.class);
        }
        
        return "";
    }
    
    /**
     * Aplica la selecció de rol i actualitza la darrera entitat de l'usuari si cal.
     */
    public String applyRoleSelection() {
        LOG.info("Aplicant selecció de rol: {}", selectedRole);

        // Si s'ha seleccionat un rol específic, actualitzar la darrera entitat de l'usuari
        if (usuariId != null && entitatId != null) {
            try {
                UsuariDTO usuari = usuariService.findById(usuariId).orElse(null);
                if (usuari != null) {
                    usuari.setDarreraEntitat(entitatId);
                    usuariService.update(usuari);
                }
            } catch (Exception e) {
                LOG.error("Error actualitzant darrera entitat de l'usuari", e);
            }
        }

        // Redirigeix a la pàgina principal
        return "/listReferencia?faces-redirect=true";
    }
    
    /**
     * Verifica si l'usuari té permisos de super administrador.
     * Cal tenir el rol ITD_ADMIN i tenir seleccionat SUPER_ADMIN.
     */
    public boolean isSuperAdmin() {
        return "SUPER_ADMIN".equals(getSelectedRole()) && security.isAdmin();
    }
    
    /**
     * Verifica si l'usuari té permisos d'administrador d'entitat.
     */
    public boolean isAdminEntitat() {
        return "ADMIN_ENTITAT".equals(getSelectedRole());
    }
    
    /**
     * Verifica si l'usuari pot veure l'opció de Super Admin al menú.
     * Només visible si té el rol ITD_ADMIN de Keycloak.
     */
    public boolean canSelectSuperAdmin() {
        return security.isAdmin();
    }
    
    /**
     * Verifica si l'usuari pot veure l'opció de Admin Entitat al menú.
     * Visible per qualsevol usuari amb rol ITD_USER.
     */
    public boolean canSelectAdminEntitat() {
        return security.isUser();
    }
    
    /**
     * Verifica l'accés a pàgines restringides a super administradors.
     * Redirigeix a la pàgina principal si no té permisos.
     */
    public void requireSuperAdmin() {
        if (!isSuperAdmin()) {
            LOG.warn("Intent d'accés no autoritzat a pàgina de super admin per l'usuari: {}", this.username);
            
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null) {
                ExternalContext externalContext = context.getExternalContext();
                
                try {
                    externalContext.redirect(externalContext.getRequestContextPath() + "/listReferencia.xhtml");
                    context.responseComplete();
                } catch (Exception e) {
                    LOG.error("Error redirigint després de denegar accés", e);
                }
            }
        }
    }

    
}
