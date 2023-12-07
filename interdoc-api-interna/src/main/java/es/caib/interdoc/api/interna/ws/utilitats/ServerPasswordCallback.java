package es.caib.interdoc.api.interna.ws.utilitats;

import java.io.IOException;
import java.util.Optional;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.interdoc.commons.utils.Configuracio;
import es.caib.interdoc.service.facade.AplicacioServiceFacade;
import es.caib.interdoc.service.model.AplicacioDTO;

/**
 * 
 * Classe que s'utilitza per la autenticació del servei CsvQueryDocumentSecurity
 * Els usuarios autoritzats están donats d'alta a la taula de Aplicacio
 * 
 * @author jagarcia
 * 
 */

public class ServerPasswordCallback implements CallbackHandler {
	
	private static final Logger log = LoggerFactory.getLogger(ServerPasswordCallback.class);
	
	@EJB(mappedName = AplicacioServiceFacade.JNDI_NAME)
	protected AplicacioServiceFacade aplicacioEjb;

	public ServerPasswordCallback() {
		super();
	}

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];

		if (pc.getIdentifier() == null) {
			throw new IOException("authentication failure. Required username password to proceed ...");
		}
		
		if (aplicacioEjb == null) {
			try {
				aplicacioEjb = (AplicacioServiceFacade) (new InitialContext()).lookup(AplicacioServiceFacade.JNDI_NAME);
		
				Optional<AplicacioDTO> aplicacio = aplicacioEjb.findByUserName(pc.getIdentifier());
				
				if (aplicacio.isPresent()) {
					AplicacioDTO app = aplicacio.get();
					
					if (Configuracio.isDesenvolupament())
						log.info("aplicacio.findByUsername => " + app.getUsuari() + " - " + app.getCodiDir3());
	
					// set the password on the callback. This will be compared to the
					// password which was sent from the client.
					pc.setPassword(app.getClau());
				}else {
					// TODO Control de excepciones
					log.info("L'Usuari aplicació no té permisos o no existeix.");
				}
				
			} catch (NamingException e) {
				e.printStackTrace();
				throw new IOException("authentication failure. error aplicacioEjb initialization. ");
			}
		}else {
			throw new IOException("authentication failure. invalid user name or password ");
		}
				
	}

}
