package es.caib.interdoc.api.interna.ws.utilitats;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;

/**
 * Intercepta les cridades entrants al webservice de CSVQueryDocumentService.
 * @author jagarcia
 *
 */
public class WSSecurityInterceptor extends AbstractPhaseInterceptor<Message> {

	public WSSecurityInterceptor() {
		super(Phase.PRE_PROTOCOL);
	}

	public WSSecurityInterceptor(String phase) {
		super(Phase.PRE_PROTOCOL);
	}

	@Override
	public void handleMessage(Message message) throws Fault {

		Map<String, Object> props = new HashMap<String, Object>();
		props.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		props.put(WSHandlerConstants.PW_CALLBACK_CLASS, ServerPasswordCallback.class.getName());
		props.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);

		WSS4JInInterceptor wss4jInHandler = new WSS4JInInterceptor(props);

		message.getInterceptorChain().add((Interceptor) wss4jInHandler);
	}

}
