package es.caib.interdoc.api.interna.ws.utilitats;

import es.caib.interdoc.commons.i18n.I18NException;
import es.caib.interdoc.commons.utils.Constants;
import es.caib.interdoc.service.model.AplicacioDTO;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.security.SecurityContext;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * Intercepta les cridades entrants a un WS. Veure: Veure https://cxf.apache.org/docs/interceptors.html
 *
 * @author anadal
 */

public class WsInInterceptor extends AbstractPhaseInterceptor<Message> {

    private static final Logger LOG = LoggerFactory.getLogger(WsInInterceptor.class);

    public WsInInterceptor() {
        super(Phase.PRE_INVOKE);
    }

    @Override
    public void handleMessage(Message message) throws Fault {

    	LOG.debug("WsInInterceptor::handleMessage() =>  Thread = {}", Thread.currentThread().getId());
        
        SecurityContext context = message.get(SecurityContext.class);
        if (context == null || context.getUserPrincipal() == null) {
            LOG.error("S'ha cridat a l'API sense autenticar la petició.");
            return;
        }

        String username = context.getUserPrincipal().getName();
        LOG.debug("WsInInterceptor::handleMessage() => username {}", username);
        LOG.debug("WsInInterceptor::handleMessage() => ITD_USER {}", context.isUserInRole(Constants.ITD_USER));
        LOG.debug("WsInInterceptor::handleMessage() => ITD_ADMIN {}", context.isUserInRole(Constants.ITD_ADMIN));
        LOG.debug("WsInInterceptor::handleMessage() => ITD_WS {}", context.isUserInRole(Constants.ITD_WS));
        
        Method method = getTargetMethod(message);
        LOG.debug("  + Method NAME = {}", method.getName());
        LOG.debug("  + Method CLASS = {}", method.getDeclaringClass());
        
        /*
        try {	
        	HttpServletRequest hsr = (HttpServletRequest) message.get("HTTP.REQUEST");
            Enumeration<String> headerNames = hsr.getHeaderNames();
            while(headerNames.hasMoreElements()) {
              String headerName = headerNames.nextElement();
              System.out.println("Header Name - " + headerName + ", Value - " + hsr.getHeader(headerName));
            }
            
            Enumeration<String> params = hsr.getParameterNames(); 
            while(params.hasMoreElements()){
             String paramName = params.nextElement();
             System.out.println("Parameter Name - "+paramName+", Value - "+ hsr.getParameter(paramName));
            }
           
        }catch(Exception e) {
        	LOG.error(e.getMessage());
        }
        */
        
    }

    private Method getTargetMethod(Message m) {
        BindingOperationInfo bop = m.getExchange().get(BindingOperationInfo.class);
        MethodDispatcher md = (MethodDispatcher) m.getExchange().get(Service.class)
                .get(MethodDispatcher.class.getName());
        return md.getMethod(bop);
    }

    @Override
    public void handleFault(Message message) {

        Fault f = (Fault) message.getContent(Exception.class);

        LOG.error("WsInInterceptor::handleFault() - Code = {}", f.getCode());
        LOG.error("WsInInterceptor::handleFault() - Msg = {}", f.getMessage());

        Throwable cause = f.getCause();

        // TODO obtenir Idioma de l'usuari
        Locale language = Locale.getDefault();

        if (cause != null) {
            LOG.error("WsInInterceptor::handleFault() - Cause Class = " + cause.getClass());
            if (cause instanceof UndeclaredThrowableException) {
                LOG.error("WsInInterceptor::handleFault() - Cause.UndeclaredThrowable");
                cause = ((UndeclaredThrowableException) cause).getUndeclaredThrowable();
            }
            if (cause instanceof I18NException) {
                LOG.error("WsInInterceptor::handleFault() - Cause.I18NException");
                // I18NException i18n = (I18NException) cause;
                // message.setContent(WsI18NException.class,
                //        new WsI18NException(i18n.getLocalizedMessage(language), cause));
            } else {
                LOG.error("WsInInterceptor::handleFault() - Cause.msg = {}", cause.getMessage());
                LOG.error("WsInInterceptor::handleFault() - Cause.type = {}", cause.getClass());
            }
        }

        super.handleFault(message);
    }
}