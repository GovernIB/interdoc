package es.caib.interdoc.plugins.arxiu;

public class DocumentNotValidException extends Exception {

	@Override
	public String getMessage() {
		return "Document Not Valid Exception " + super.getMessage();
	}

	public DocumentNotValidException() {
		super();
	}

	public DocumentNotValidException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DocumentNotValidException(String message, Throwable cause) {
		super(message, cause);
	}

	public DocumentNotValidException(String message) {
		super(message);
	}

	public DocumentNotValidException(Throwable cause) {
		super(cause);
	}

	
	
}
