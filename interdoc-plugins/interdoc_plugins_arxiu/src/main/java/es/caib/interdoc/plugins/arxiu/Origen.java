package es.caib.interdoc.plugins.arxiu;

import es.caib.plugins.arxiu.api.ContingutOrigen;

public enum Origen {
	
	CIUTADA("0"),
	ADMINISTRACIO("1");
	
	private String str;
	
	Origen(String str) {
		this.str = str;
    }
	
	public static Origen toEnum(String str) {
		if (str != null) {
		    for (Origen valor: Origen.values()) {
		        if (valor.toString().equals(str)) {
		            return valor;
		        }
		    }
		}
	    return null;
	}
	
	@Override
	public String toString() {
		return str;
	}
	
	public static ContingutOrigen toContingutOrigen(Origen origen) {
		
		ContingutOrigen contingutOrigen = null;
		
		switch (origen) {
			case CIUTADA:
				contingutOrigen = ContingutOrigen.CIUTADA;
				break;
			case ADMINISTRACIO:
				contingutOrigen = ContingutOrigen.ADMINISTRACIO;
				break;
		}
		return contingutOrigen;
		
	}

}
