package es.caib.interdoc.service.model;

public enum TipusIdentificacioDTO {
    CLAVE(1),
    PIN(2),
    DNIE(3),
    PERSONA_JURIDICA(4),
    EMPLEADO_PUBLICO(5),
    ENTIDAD_NO_JURIDICA(6),
    EMPLEADO_PUBLICO_PSEUD(7),
    REPRESENTACION_PERSONA_JURIDICA(8),
    REPRESENTACION_SIN_PERSONA_JURIDICA(9);

    private final Integer clau;

    private TipusIdentificacioDTO(Integer clau) {
        this.clau = clau;
    }

    public Integer getClau() {
        return clau;
    }
}