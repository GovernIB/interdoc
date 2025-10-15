package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.service.model.UsuariEntitatAtribut;
import es.caib.interdoc.persistence.model.UsuariEntitat;
import es.caib.interdoc.persistence.model.UsuariEntitat_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Implentació del mapeig entre atributs de la capa de serveis dels usuaris
 * de la capa de persistència.
 */
public class UsuariEntitatCriteriaHelper extends AbstractCriteriaHelper<UsuariEntitat, UsuariEntitatAtribut> {

    public UsuariEntitatCriteriaHelper(CriteriaBuilder builder, Root<UsuariEntitat> root) {
        super(builder, root);
    }

    /**
     * Mapeja els noms d'atributs amb el que treballa la capa de serveis,
     *
     * @param atribut atribut de unitats orgàniques emprat a la capa de servei.
     * @return path obtingut a partir del {@link Root<Aplicacio>}.
     */
    @Override
    protected Path<?> getPath(UsuariEntitatAtribut atribut) {
        // TODO revisar substituió dels noms dels camps per algo més dinàmic
        switch (atribut) {
            case usuariEntitatId:
                return root.get(UsuariEntitat_.usuariEntitatId);
            case usuariId:
                return root.get(UsuariEntitat_.usuariId);
            case entitatId:
                return root.get(UsuariEntitat_.entitatId);
            case actiu:
                return root.get(UsuariEntitat_.actiu);
            
            default:
                throw new IllegalArgumentException("Valor no vàlid " + atribut);
        }
    }
}
