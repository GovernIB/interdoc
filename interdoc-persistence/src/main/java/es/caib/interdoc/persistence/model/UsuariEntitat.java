package es.caib.interdoc.persistence.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Representació d'un usuari. A nivell de classe definim la seqüència que emprarem, i les claus úniques.
 *
 * @author areus
 */
@Entity
@SequenceGenerator(name = "usuarientitat-sequence", sequenceName = "ITD_USUARIENTITAT_SEQ", allocationSize = 1)
@Table(name = "ITD_USUARIENTITAT", indexes = { @Index(name = "ITD_USUARIENTITAT_PK_I", columnList = "USUARIENTITATID") })
@NamedQueries({ @NamedQuery(name = UsuariEntitat.GET_ALL, query = "select a from UsuariEntitat a") })
public class UsuariEntitat extends BaseEntity {

    private static final long serialVersionUID = 27L;

    public static final String GET_ALL = "UsuariEntitat.GET_ALL";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarientitat-sequence")
    @Column(name = "USUARIENTITATID", nullable = false)
    private Long usuariEntitatId;
    
    @Column(name = "USUARIID", nullable = false)
    private Long usuariId;
    
    
    @Column(name = "ENTITATID", nullable = false)
    private Long entitatId;
    
    @Column(name = "ACTIU", nullable = false)
    private boolean actiu;
    

    public Long getUsuariEntitatId() {
        return usuariEntitatId;
    }
    
    public Long getUsuariId() {
        return usuariId;
    }
    
    public Long getEntitatId() {
        return entitatId;
    }
    
    public boolean isActiu() {
        return actiu;
    }
    
    

    public void setUsuariEntitatId(Long usuariEntitatId) {
        this.usuariEntitatId = usuariEntitatId;
    }
    
    public void setUsuariId(Long usuariId) {
        this.usuariId = usuariId;
    }
    
    public void setEntitatId(Long entitatId) {
        this.entitatId = entitatId;
    }
    
    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }
    
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UsuariEntitat))
            return false;
        UsuariEntitat that = (UsuariEntitat) o;
        return Objects.equals(usuariEntitatId, that.usuariEntitatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuariEntitatId);
    }

    @Override
    public String toString() {
        return "Usuari [usuariEntitatId=" + usuariEntitatId + ", usuariId=" + usuariId + ", entitatId=" + entitatId+"]";
    }

}
