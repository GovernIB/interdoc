package es.caib.interdoc.api.interna.ws.exception;

public class InterdocException extends RuntimeException {
	
	private static final long serialVersionUID = 11233L;

	public InterdocException() {
		super();
	}
	
	public InterdocException(Throwable cause) {
		super(cause);
	}
	
	public InterdocException(String errorMessage) {
		super(errorMessage);
	}
	
	public InterdocException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}

}
