package es.caib.interdoc.api.interna.ws.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

/**
 * Classe d'utilitats per carregar un fitxer de filesystem per enviar-ho per DataHandler
 * @author jagarcia
 *
 */

public class InputStreamDataSource implements DataSource {
	
	 private InputStream inputStream;

	    public InputStreamDataSource(InputStream inputStream) {
	        this.inputStream = inputStream;
	    }

	    @Override
	    public InputStream getInputStream() throws IOException {
	        return inputStream;
	    }

	    @Override
	    public OutputStream getOutputStream() throws IOException {
	        throw new UnsupportedOperationException("Not implemented");
	    }

	    @Override
	    public String getContentType() {
	        return "*/*";
	    }

	    @Override
	    public String getName() {
	        return "InputStreamDataSource";
	    }

}
