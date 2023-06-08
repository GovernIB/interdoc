package es.caib.interdoc.service.model;

/**
 * Representa l'estat d'una entitat. Si està activa o inactiva, o si està a l'històric.
 *
 * @author areus
 */
public enum EstatPublicacio {
    ACTIU(1),
    INACTIU(2),
    HISTORIC(3);

    private final Integer clau;

    private EstatPublicacio(Integer clau) {
        this.clau = clau;
    }

    public Integer getClau() {
        return clau;
    }

}
