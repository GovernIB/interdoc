package es.caib.interdoc.persistence.model;

import es.caib.interdoc.service.model.EstatPublicacio;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.*;

/**
 * Representa les peticions a tercers que es realitzen i no
 *
 * @author jagarcia
 */

@Entity
@SequenceGenerator(name = "peticioatercer-sequence", sequenceName = "ITD_PETICIOATERCER_SEQ", allocationSize = 1)
@Table(name = "ITD_PETICIOATERCER",
        indexes = {
                @Index(name = "ITD_PETICIOATERCER_PK_I", columnList = "PETICIOID")
        }
)
public class PeticioATercer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "peticioatercer-sequence")
    @Column(name = "PETICIOID", nullable = false, length = 19)
    private Long id;

    @Column(name = "CSVIDENTIFICADOR", nullable = true, length = 255)
    private String csvId;

    @Column(name = "ENIIDENTIFICADOR", nullable = true, length = 255)
    private String eniId;

    @Column(name = "CODIDIR3", nullable = false, length = 20)
    private String codiDir3;

    @Column(name = "DOCUMENTENI", nullable = false, length = 255)
    private String documentEni;

    @Column(name = "RECUPERACIOORIGINAL", nullable = false)
    private boolean esRecuperacioOriginal;

    @Column(name = "NIF", nullable = false, length = 50)
    private String nif;

    @Column(name = "TIPUSIDENTIFICACIO", nullable = false)
    private Integer tipusIdentificacio;

    @Column(name = "IP", nullable = false, length = 20)
    private String ip;

    @Column(name = "DATACREACIO", nullable = false)
    private LocalDate dataCreacio;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "ESTAT", nullable = false)
    private EstatPublicacio estat;


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

    public String getDocumentEni() {
        return documentEni;
    }

    public void setDocumentEni(String documentEni) {
        this.documentEni = documentEni;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PeticioATercer)) return false;
        PeticioATercer that = (PeticioATercer) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return "PeticioATercer [peticioId=" + id + ", csvId=" + csvId + ", eniId=" + eniId + ", codiDir3="
                + codiDir3 + ", documentEni=" + documentEni + ", esRecuperacioOriginal=" + esRecuperacioOriginal
                + ", nif=" + nif + ", tipusIdentificacio=" + tipusIdentificacio + ", ip=" + ip + ", dataCreacio="
                + dataCreacio + ", estat=" + estat + "]";
    }


}