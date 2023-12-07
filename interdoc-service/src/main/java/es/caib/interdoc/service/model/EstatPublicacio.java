package es.caib.interdoc.service.model;

/**
 * Representa l'estat d'un registre. Si està actiu o inactiu, o si està a l'històric.
 *
 * @author areus
 */
public enum EstatPublicacio {
	INACTIU(0),
	ACTIU(1),
    HISTORIC(2);

    private final Integer clau;

    private EstatPublicacio(Integer clau) {
        this.clau = clau;
    }

    public Integer getClau() {
        return clau;
    }

}
