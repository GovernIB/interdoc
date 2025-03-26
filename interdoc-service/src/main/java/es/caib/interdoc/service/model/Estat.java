package es.caib.interdoc.service.model;

/**
 * Representa l'estat d'un registre. Si està actiu o inactiu
 *
 * @author jagarcia
 */
public enum Estat {
    INACTIU(0),
    ACTIU(1);

    private final int clau;
    
    private Estat(int clau) {
        this.clau = clau;
    }

    public int getClau() {
        return clau;
    }
    
    public static Estat fromClau(int clau) {
    	for (Estat e : Estat.values()) {
    		if (e.getClau() == clau) {
    			return e;
    		}
    	}
    	throw new IllegalArgumentException("Codi d'estat no vàlid: " + clau);
    }
}
