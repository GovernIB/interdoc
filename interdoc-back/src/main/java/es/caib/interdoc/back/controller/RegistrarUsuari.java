package es.caib.interdoc.back.controller;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.back.model.UsuariModel;
import es.caib.interdoc.back.security.UserInfo;
import es.caib.interdoc.service.facade.UsuariServiceFacade;

@Named
@ViewScoped
@RolesAllowed("ITD_ADMIN")
public class RegistrarUsuari extends AbstractController implements Serializable{

    private static final long serialVersionUID = -4092311228270716323L;
    
    private static final Logger LOG = LoggerFactory.getLogger(RegistrarUsuari.class);

    
    @EJB
    UsuariServiceFacade usuariService;
    
    @Inject
    private UsuariModel usuariModel;
    
    @Inject
    private UserInfo userInfo;
    
    
    /**
     * Inicialitza el formulari amb les dades de l'usuari autenticat
     */
    @PostConstruct
    public void init() {
        LOG.debug("init - Inicialitzant formulari de registre d'usuari");
        
        if (userInfo != null && userInfo.getUsername() != null) {
            // Establim el username des de la informació de l'usuari autenticat
            usuariModel.getValue().setUsername(userInfo.getUsername());
            
            // Si hi ha informació adicional de Keycloak, la utilitzem per pre-emplenar el formulari
            if (userInfo.getNom() != null) {
                usuariModel.getValue().setNom(userInfo.getNom());
            }
            if (userInfo.getEmail() != null) {
                usuariModel.getValue().setEmail(userInfo.getEmail());
            }
            if (userInfo.getNif() != null) {
                usuariModel.getValue().setNif(userInfo.getNif());
            }
            if (userInfo.getLlinatges() != null) {
                // Si hi ha llinatges, intentem separar-los
                String[] parts = userInfo.getLlinatges().split(" ", 2);
                if (parts.length > 0) {
                    usuariModel.getValue().setLlinatge1(parts[0]);
                }
                if (parts.length > 1) {
                    usuariModel.getValue().setLlinatge2(parts[1]);
                }
            }
            
            LOG.debug("Formulari inicialitzat amb username: {}", userInfo.getUsername());
        } else {
            LOG.warn("No s'ha pogut obtenir la informació de l'usuari autenticat");
        }
    }
    
    
    /**
     * Crea un nou usuari. Afegeix un missatge si s'ha fet
     * amb èxit i redirecciona cap a la pàgina principal.
     *
     * @return navegació cap a la pàgina principal.
     */
    public String save() {
        LOG.debug("save");
        
        // Assegurar que el username està establert (ja que el camp és disabled i no s'envia)
        if (userInfo != null && userInfo.getUsername() != null) {
            usuariModel.getValue().setUsername(userInfo.getUsername());
        }
        
        // Feim una creació 
        usuariService.create(usuariModel.getValue());

        ResourceBundle labelsBundle = getBundle("labels");
        addGlobalMessage(labelsBundle.getString("msg.creaciocorrecta"));
        
        // Els missatges no aguanten una redirecció ja que no es la mateixa petició
        // Així asseguram que es guardin fins la visualització
        keepMessages();

        // Redireccionam cap a la pàgina principal
        return "/listReferencia?faces-redirect=true";
    }
    
    
}
