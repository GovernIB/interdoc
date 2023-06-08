package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Traza;
import es.caib.interdoc.persistence.model.Traza_;
import es.caib.interdoc.service.model.TrazaAtribut;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Implentació del mapeig entre atributs de la capa de serveis de les Unitats Orgàniques i els paths corresponents
 * de la capa de persistència.
 */
public class TrazaCriteriaHelper extends AbstractCriteriaHelper<Traza, TrazaAtribut> {

    public TrazaCriteriaHelper(CriteriaBuilder builder, Root<Traza> root) {
        super(builder, root);
    }

    /**
     * Mapeja els noms d'atributs amb el que treballa la capa de serveis,
     *
     * @param atribut atribut de unitats orgàniques emprat a la capa de servei.
     * @return path obtingut a partir del {@link Root<Traza>}.
     */
    @Override
    protected Path<?> getPath(TrazaAtribut atribut) {
        switch (atribut) {
            case id:
                return root.get(Traza_.id);
            case nom:
                return root.get(Traza_.nom);
            case valor:
                return root.get(Traza_.valor);
            case referenciaId:
                return root.get(Traza_.referenciaId);
            case dataCreacio:
                return root.get(Traza_.dataCreacio);
            default:
                throw new IllegalArgumentException("Valor no vàlid " + atribut);
        }
    }
}
