package es.caib.interdoc.ws.utilitats;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.security.AuthenticationException;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.security.SecurityContext;
import org.apache.log4j.Logger;

import es.caib.interdoc.ws.model.WSCredential;

public class WebServiceAuthenticationInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

	private static final Logger LOG = Logger.getLogger(WebServiceAuthenticationInterceptor.class);

	public WebServiceAuthenticationInterceptor() {
		super("pre-invoke");
	}

	public void handleMessage(SoapMessage message) throws Fault {

		// UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
		LOG.debug("Entramos en interceptor de WS. ");

		AuthenticationException authentication = null;
		
		SecurityContext context = message.get(SecurityContext.class);
        if (context == null || context.getUserPrincipal() == null) {
        	LOG.error("S'ha cridat a l'API sense autenticar la petició.");
        }

        if(context.getUserPrincipal() != null) {
	        String username = context.getUserPrincipal().getName();
	        LOG.debug("WebServiceAuthenticationInterceptor::handleMessage() => username " + username);
        }else {
        	LOG.debug("WebServiceAuthenticationInterceptor::handleMessage() => context.getUserPrincipal() is NULL");
        }
        
		MessageContentsList inObjects = MessageContentsList.getContentsList((Message) message);

		if (inObjects != null) {
			
			for (Object obj : inObjects) {
				LOG.info( obj.toString() );

				if (obj instanceof WSCredential) {
					LOG.info("Se intenta acceder a CSVDocumentService. ");
					WSCredential info = (WSCredential) obj;
					// usernamePasswordAuthenticationToken = new
					// UsernamePasswordAuthenticationToken(info.getIdaplicacion(),
					// info.getPassword()); continue;
					LOG.info("aplicacino: " + info.getIdaplicacion());
					LOG.info("passwd: " + info.getPassword());
					continue;
				}
			}
			
		} else {
			LOG.debug("inObjects IS NULL");
		}
		
		

		/*
		SecurityContext securityContext = SecurityContextHolder.getContext();

		try {
			Authentication authentication1 = this.authenticationManager
					.authenticate((Authentication) usernamePasswordAuthenticationToken);
			message.getExchange().put(Authentication.class, authentication1);

			securityContext.setAuthentication(authentication1);
		} catch (AuthenticationException ex) {
			LOG.error("Credenciales Erroneas. Se lanza SoapFault con mensaje. ");
			securityContext.setAuthentication(null);
			throw new SoapFault("Credenciales Erroneas. compruebe id de aplicaciï¿½n y password", ex,
					message.getVersion().getSender());
		}
		 */
	}

}
