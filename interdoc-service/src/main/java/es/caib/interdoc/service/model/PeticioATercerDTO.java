package es.caib.interdoc.service.model;

import java.time.LocalDate;

/**
 * Dades que representa una petició a tercer
 *
 * @author jagarcia
 */

public class PeticioATercerDTO {

    private Long id;

    private String csvId;

    private String eniId;

    private String codiDir3;

    private boolean esRecuperacioOriginal;

    private String documentEni;

    private String nif;

    private Integer tipusIdentificacio;

    private String ip;

    private LocalDate dataCreacio;

    private EstatPublicacio estat;

    public PeticioATercerDTO() {

    }

    public PeticioATercerDTO(Long peticioId, String csvId, String eniId, String codiDir3, boolean esRecuperacioOriginal, String documentEni,
                             String nif, int tipusIdentificacio, String ip, EstatPublicacio estat, LocalDate dataCreacio) {
        super();
        this.id = peticioId;
        this.csvId = csvId;
        this.eniId = eniId;
        this.codiDir3 = codiDir3;
        this.documentEni = documentEni;
        this.esRecuperacioOriginal = esRecuperacioOriginal;
        this.nif = nif;
        this.tipusIdentificacio = tipusIdentificacio;
        this.dataCreacio = dataCreacio;
        this.estat = estat;
    }

    public PeticioATercerDTO(Long peticioId, String csvId, String eniId, String codiDir3, boolean esRecuperacioOriginal, String documentEni,
                             String nif, Integer tipusIdentificacio, String ip, LocalDate dataCreacio, EstatPublicacio estat) {
        super();
        this.id = peticioId;
        this.csvId = csvId;
        this.eniId = eniId;
        this.codiDir3 = codiDir3;
        this.documentEni = documentEni;
        this.esRecuperacioOriginal = esRecuperacioOriginal;
        this.nif = nif;
        this.tipusIdentificacio = tipusIdentificacio;
        this.ip = ip;
        this.dataCreacio = dataCreacio;
        this.estat = estat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long peticioId) {
        this.id = peticioId;
    }

    public String getCsvId() {
        return csvId;
    }

    public void setCsvId(String csvId) {
        this.csvId = csvId;
    }

    public String getEniId() {
        return eniId;
    }

    public void setEniId(String eniId) {
        this.eniId = eniId;
    }

    public String getCodiDir3() {
        return codiDir3;
    }

    public void setCodiDir3(String codiDir3) {
        this.codiDir3 = codiDir3;
    }

    public boolean isEsRecuperacioOriginal() {
        return esRecuperacioOriginal;
    }

    public void setEsRecuperacioOriginal(boolean esRecuperacioOriginal) {
        this.esRecuperacioOriginal = esRecuperacioOriginal;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Integer getTipusIdentificacio() {
        return tipusIdentificacio;
    }

    public void setTipusIdentificacio(Integer tipusIdentificacio) {
        this.tipusIdentificacio = tipusIdentificacio;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDate getDataCreacio() {
        return dataCreacio;
    }

    public void setDataCreacio(LocalDate dataCreacio) {
        this.dataCreacio = dataCreacio;
    }

    public EstatPublicacio getEstat() {
        return estat;
    }

    public void setEstat(EstatPublicacio estat) {
        this.estat = estat;
    }

    public String getDocumentEni() {
        return documentEni;
    }

    public void setDocumentEni(String documentEni) {
        this.documentEni = documentEni;
    }

    @Override
    public String toString() {
        return "PeticioATercerDTO [peticioId=" + id + ", csvId=" + csvId + ", eniId=" + eniId + ", codiDir3="
                + codiDir3 + ", esRecuperacioOriginal=" + esRecuperacioOriginal + ", documentEni=" + documentEni
                + ", nif=" + nif + ", tipusIdentificacio=" + tipusIdentificacio + ", ip=" + ip + ", dataCreacio="
                + dataCreacio + ", estat=" + estat + "]";
    }

}
