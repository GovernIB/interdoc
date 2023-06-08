package es.caib.interdoc.api.interna.secured.obtenerreferencia.resposta;

import javax.xml.bind.annotation.XmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdentificadorBean {

	private static Logger log = LoggerFactory.getLogger(IdentificadorBean.class.getName());

	String valorCSV = "";
	String secuenciaIdentificador = "";

	public void setValorCSV(String valorCSV) {
		this.valorCSV = valorCSV;
	}

	@XmlElement(name = "eEMGDE.Firma.FormatoFirma.ValorCSV")
	public String getValorCSV() {
		return valorCSV;
	}

	public void setSecuenciaIdentificador(String secuenciaIdentificador) {
		this.secuenciaIdentificador = secuenciaIdentificador;
	}

	@XmlElement(name = "eEMGDE.Identificador.SecuenciaIdentificador")
	public String getSecuenciaIdentificador() {
		return secuenciaIdentificador;
	}

	@Override
	public String toString() {
		if (log.isDebugEnabled()) {
			StringBuilder builder = new StringBuilder();
			builder.append("\n valorCSV=" + valorCSV);
			builder.append("\n secuenciaIdentificador=" + secuenciaIdentificador);
			return builder.toString();
		} else {
			return "valorCSV=" + valorCSV;
		}
	}
}