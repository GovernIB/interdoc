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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.caib.interdoc.service.model.Ordre;
import es.caib.interdoc.service.model.Pagina;
import es.caib.interdoc.service.model.UsuariEntitatAtribut;

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
    
    private String nom;
    private String llinatge1;
    private String llinatge2;
    private String currentUserFullName;
    
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
        
        // COMPROVACIÓ: Si l'usuari no existeix a la BBDD
        if (usuari == null) {
            LOG.warn("L'usuari {} no existeix a la base de dades", username);
            
            // Comprovar si és administrador
            if (security.isAdmin()) {
                LOG.info("L'usuari {} és administrador (ITD_ADMIN). Redirigint a registrarUsuari", username);
                try {
                    context.getExternalContext().redirect(
                        context.getExternalContext().getRequestContextPath() + "/registrarUsuari.xhtml"
                    );
                } catch (Exception e) {
                    LOG.error("Error redirigint a registrarUsuari", e);
                }
            } else {
                LOG.info("L'usuari {} no és administrador. Redirigint a error d'usuari no registrat", username);
                try {
                    context.getExternalContext().redirect(
                        context.getExternalContext().getRequestContextPath() + "/error/usuariNoRegistrat.xhtml"
                    );
                } catch (Exception e) {
                    LOG.error("Error redirigint a pàgina d'error", e);
                }
            }
            return;
        }
        
        this.usuariId  = usuari.getUsuariId();
        this.username  = usuari.getUsername();
        this.entitatId = usuari.getDarreraEntitat();
        this.nom = usuari.getNom();
        this.llinatge1 = usuari.getLlinatge1();
        this.llinatge2 = usuari.getLlinatge2();
        
        // Construir nom complet
        StringBuilder fullName = new StringBuilder();
        if (this.nom != null && !this.nom.trim().isEmpty()) {
            fullName.append(this.nom.trim());
        }
        if (this.llinatge1 != null && !this.llinatge1.trim().isEmpty()) {
            if (fullName.length() > 0) fullName.append(" ");
            fullName.append(this.llinatge1.trim());
        }
        if (this.llinatge2 != null && !this.llinatge2.trim().isEmpty()) {
            if (fullName.length() > 0) fullName.append(" ");
            fullName.append(this.llinatge2.trim());
        }
        this.currentUserFullName = fullName.length() > 0 ? fullName.toString() : this.username;
        
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
                    LOG.warn("L'usuari {} no té entitat assignada. Contacti amb l'administrador perque l'hi assigni una entitat.", this.username);
                    // No llançar expeció, per evitar bucle de redireccions a JBoss.
                    return;
                }
            }
            return;
            
        }else{
            //Si l'usuari te darreraEntitat assignada, verificam que realment està assignada a l'usuari.
            
            // Verificar si aquesta entitat està assignada a l'usuari
            boolean entitatAssignada = verificarEntitatAssignada(usuariId, entitatId);
            
            if (entitatAssignada) {
                // L'entitat està assignada, l'utilitzam
                EntitatDTO entitat = entitatService.findById(entitatId).orElse(null);
                if (entitat != null) {
                    this.entitatNom = entitat.getNom();
                    LOG.debug("Entitat assignada trobada: {} (ID: {})", this.entitatNom, this.entitatId);
                } else {
                    LOG.warn("No s'ha trobat l'entitat amb id {}", this.entitatId);
                    this.entitatId = null;
                    this.entitatNom = null;
                }
            } else {
                // L'entitat NO està assignada a l'usuari, la descartam
                LOG.warn("La darreraEntitat (ID: {}) no està assignada a l'usuari {}. Descartant-la.", this.entitatId, this.username);
                this.entitatId = null;
                this.entitatNom = null;
                
                // Intentar assignar una entitat per defecte de les assignades
                UsuariEntitatDTO usuariEntitat = usuariEntitatService.findByUsuariId(usuariId).orElse(null);
                if(usuariEntitat != null) {
                    EntitatDTO entitat2 = entitatService.findById(usuariEntitat.getEntitatId()).orElse(null);
                    if (entitat2 != null) {
                        this.entitatNom = entitat2.getNom();
                        this.entitatId = entitat2.getId();
                        LOG.debug("Assignada entitat per defecte: {} (ID: {})", this.entitatNom, this.entitatId);
                    }
                }
            }
        }
        
        // Inicialitzar rol per defecte
        initializeDefaultRole();
    }
    
    /**
     * Verifica si una entitat està assignada a un usuari.
     * 
     * @param usuariId ID de l'usuari
     * @param entitatId ID de l'entitat a verificar
     * @return true si l'entitat està assignada, false en cas contrari
     */
    private boolean verificarEntitatAssignada(Long usuariId, Long entitatId) {
        if (usuariId == null || entitatId == null) {
            return false;
        }
        
        try {
            // Crear filtre per usuariId i entitatId
            Map<UsuariEntitatAtribut, Object> filter = new HashMap<>();
            filter.put(UsuariEntitatAtribut.usuariId, usuariId);
            filter.put(UsuariEntitatAtribut.entitatId, entitatId);
            
            // Buscar si existeix aquesta relació
            Pagina<UsuariEntitatDTO> pagina = usuariEntitatService.findFiltered(
                0, 
                1, 
                filter, 
                java.util.Collections.emptyList()
            );
            
            return pagina.getTotal() > 0;
        } catch (Exception e) {
            LOG.error("Error verificant si l'entitat {} està assignada a l'usuari {}", entitatId, usuariId, e);
            return false;
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
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getLlinatge1() {
        return llinatge1;
    }
    
    public void setLlinatge1(String llinatge1) {
        this.llinatge1 = llinatge1;
    }
    
    public String getLlinatge2() {
        return llinatge2;
    }
    
    public void setLlinatge2(String llinatge2) {
        this.llinatge2 = llinatge2;
    }
    
    public String getCurrentUserFullName() {
        // Si el nom complet està buit però tenim username, recarreguem les dades
        if ((currentUserFullName == null || currentUserFullName.trim().isEmpty() || currentUserFullName.equals(username)) 
            && username != null && !username.trim().isEmpty()) {
            LOG.info("Nom complet buit per l'usuari {}. Recarregant informació...", username);
            reloadUserInfo();
        }
        return currentUserFullName;
    }
    
    public void setCurrentUserFullName(String currentUserFullName) {
        this.currentUserFullName = currentUserFullName;
    }
    
    /**
     * Recarrega la informació de l'usuari des de la base de dades.
     * S'utilitza quan el nom complet està buit però l'usuari ja està autenticat.
     */
    private void reloadUserInfo() {
        if (username == null || username.trim().isEmpty()) {
            LOG.warn("No es pot recarregar la informació: username és null o buit");
            return;
        }
        
        try {
            UsuariDTO usuari = usuariService.findByUsername(username).orElse(null);
            if (usuari != null) {
                this.usuariId = usuari.getUsuariId();
                this.nom = usuari.getNom();
                this.llinatge1 = usuari.getLlinatge1();
                this.llinatge2 = usuari.getLlinatge2();
                this.entitatId = usuari.getDarreraEntitat();
                
                // Reconstruir nom complet
                StringBuilder fullName = new StringBuilder();
                if (this.nom != null && !this.nom.trim().isEmpty()) {
                    fullName.append(this.nom.trim());
                }
                if (this.llinatge1 != null && !this.llinatge1.trim().isEmpty()) {
                    if (fullName.length() > 0) fullName.append(" ");
                    fullName.append(this.llinatge1.trim());
                }
                if (this.llinatge2 != null && !this.llinatge2.trim().isEmpty()) {
                    if (fullName.length() > 0) fullName.append(" ");
                    fullName.append(this.llinatge2.trim());
                }
                this.currentUserFullName = fullName.length() > 0 ? fullName.toString() : this.username;
                
                LOG.info("Informació de l'usuari {} recarregada correctament. Nom complet: {}", username, currentUserFullName);
                
                // Recarregar també informació de l'entitat si cal
                if (this.entitatId != null) {
                    EntitatDTO entitat = entitatService.findById(entitatId).orElse(null);
                    if (entitat != null) {
                        this.entitatNom = entitat.getNom();
                    }
                }
            } else {
                LOG.warn("No s'ha pogut trobar l'usuari {} per recarregar la informació", username);
            }
        } catch (Exception e) {
            LOG.error("Error recarregant la informació de l'usuari {}", username, e);
        }
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
