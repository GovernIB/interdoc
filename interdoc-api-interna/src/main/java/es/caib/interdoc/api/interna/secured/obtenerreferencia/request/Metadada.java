package es.caib.interdoc.api.interna.secured.obtenerreferencia.request;

public class Metadada {
	
	private String clau;
	private String valor;
	private String tipus;
	
	public String getClau() {
		return clau;
	}
	public void setClau(String clau) {
		this.clau = clau;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getTipus() {
		return tipus;
	}
	public void setTipus(String tipus) {
		this.tipus = tipus;
	}
	
	@Override
	public String toString() {
		return "Metadada [clau=" + clau + ", valor=" + valor + ", tipus=" + tipus + "]";
	}

	public Metadada(String clau, String valor, String tipus) {
		super();
		this.clau = clau;
		this.valor = valor;
		this.tipus = tipus;
	}
	
	public Metadada(String clau, String valor) {
		super();
		this.clau = clau;
		this.valor = valor;
	}
	
	public Metadada() {
		super();
	}
	
}
