package es.caib.interdoc.back.security;

import es.caib.interdoc.commons.utils.Constants;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * Proveeix una forma centralitzada de comprovar els permisos de l'usuari dins l'aplicació web.
 * 
 * @author areus
 */
@Named
@ApplicationScoped
public class Security {

    @Inject
    private HttpServletRequest request;

    public boolean isAdmin() {
        return request.isUserInRole(Constants.ITD_ADMIN);
    }
    
    public boolean isUser() {
        return request.isUserInRole(Constants.ITD_USER);
    }
    
    public boolean isUserOrAdmin() {
        return isUser() || isAdmin();
    }

}
