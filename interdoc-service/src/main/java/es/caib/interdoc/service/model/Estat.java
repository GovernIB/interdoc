package es.caib.interdoc.service.model;

/**
 * Representa l'estat d'un registre. Si està actiu o inactiu
 *
 * @author areus
 */
public enum Estat {
    INACTIU(false),
    ACTIU(true);

    private final Boolean clau;

    private Estat(Boolean clau) {
        this.clau = clau;
    }

    public Boolean getClau() {
        return clau;
    }

}
