package es.caib.interdoc.service.model;

public enum PeticioEstatDTO {

    PENDENT(1),
    PROCESSAT(2),
    ERROR(3);

    private final Integer clau;

    private PeticioEstatDTO(Integer clau) {
        this.clau = clau;
    }

    public Integer getClau() {
        return clau;
    }

}
