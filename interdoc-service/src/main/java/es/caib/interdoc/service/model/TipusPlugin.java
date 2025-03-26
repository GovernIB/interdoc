package es.caib.interdoc.service.model;

/**
 * Representa l'estat d'un registre. Si està actiu o inactiu
 *
 * @author areus
 */
public enum TipusPlugin {
    ARXIU(1L),
    FIRMA(2L);

    private final Long clau;

    private TipusPlugin(Long clau) {
        this.clau = clau;
    }

    public Long getClau() {
        return clau;
    }

}
