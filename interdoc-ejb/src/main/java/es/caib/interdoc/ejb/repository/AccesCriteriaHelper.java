package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Acces;
import es.caib.interdoc.persistence.model.Acces_;
import es.caib.interdoc.service.model.AccesAtribut;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Implentació del mapeig entre atributs de la capa de serveis de les Unitats Orgàniques i els paths corresponents
 * de la capa de persistència.
 */
public class AccesCriteriaHelper extends AbstractCriteriaHelper<Acces, AccesAtribut> {

    public AccesCriteriaHelper(CriteriaBuilder builder, Root<Acces> root) {
        super(builder, root);
    }

    /**
     * Mapeja els noms d'atributs amb el que treballa la capa de serveis,
     *
     * @param atribut atribut de unitats orgàniques emprat a la capa de servei.
     * @return path obtingut a partir del {@link Root<Acces>}.
     */
    @Override
    protected Path<?> getPath(AccesAtribut atribut) {
        switch (atribut) {
            case id:
                return root.get(Acces_.id);
            case identificacio:
                return root.get(Acces_.identificacio);
            case tipusIdentificacio:
                return root.get(Acces_.tipusIdentificacio);
            case ip:
                return root.get(Acces_.ip);
            case dataCreacio:
                return root.get(Acces_.dataCreacio);
            case referenciaId:
                return root.get(Acces_.referenciaId);
            default:
                throw new IllegalArgumentException("Valor no vàlid " + atribut);
        }
    }
}
