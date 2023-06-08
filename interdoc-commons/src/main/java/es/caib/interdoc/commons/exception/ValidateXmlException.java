package es.caib.interdoc.commons.exception;

public class ValidateXmlException extends Exception {
	  private static final long serialVersionUID = 1L;
	  
	  public ValidateXmlException(Throwable cause) {
	    super(cause);
	  }
	  
	  public ValidateXmlException(String msj, Throwable cause) {
	    super(msj, cause);
	  }
	}