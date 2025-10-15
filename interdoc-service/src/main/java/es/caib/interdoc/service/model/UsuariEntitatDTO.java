package es.caib.interdoc.service.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Dades referents a un usuari.
 *
 * @author fbosch
 */
@Schema(name = "UsuariEntitat")
public class UsuariEntitatDTO {

    @NotNull
    private Long usuariEntitatId;
    @NotNull
    private Long usuariId;
    @NotNull
    private Long entitatId;
    @NotNull
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

    public UsuariEntitatDTO() {
    }

    public UsuariEntitatDTO(Long usuariEntitatId, Long usuariId, Long entitatId, boolean actiu) {
        super();
        this.usuariEntitatId = usuariEntitatId;
        this.usuariId = usuariId;
        this.entitatId = entitatId;
        this.actiu = actiu;
    }
    
    

}
