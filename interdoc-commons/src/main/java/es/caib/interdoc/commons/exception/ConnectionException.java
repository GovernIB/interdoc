package es.caib.interdoc.commons.exception;

public class ConnectionException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ConnectionException(Throwable cause) {
		super(cause);
	}

	public ConnectionException(String msj, Throwable cause) {
		super(msj, cause);
	}
}
