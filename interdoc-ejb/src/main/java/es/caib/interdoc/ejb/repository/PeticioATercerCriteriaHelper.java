package es.caib.interdoc.ejb.repository;


import es.caib.interdoc.persistence.model.PeticioATercer;
import es.caib.interdoc.persistence.model.PeticioATercer_;
import es.caib.interdoc.service.model.PeticioATercerAtribut;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Implentació del mapeig entre atributs de la capa de serveis de les Unitats Orgàniques i els paths corresponents
 * de la capa de persistència.
 */
public class PeticioATercerCriteriaHelper extends AbstractCriteriaHelper<PeticioATercer, PeticioATercerAtribut> {

    public PeticioATercerCriteriaHelper(CriteriaBuilder builder, Root<PeticioATercer> root) {
        super(builder, root);
    }

    /**
     * Mapeja els noms d'atributs amb el que treballa la capa de serveis,
     *
     * @param atribut atribut de peticions emprat a la capa de servei.
     * @return path obtingut a partir del {@link Root<PeticioATercer>}.
     */
    @Override
    protected Path<?> getPath(PeticioATercerAtribut atribut) {
        // TODO revisar substituió dels noms dels camps per algo més dinàmic
        switch (atribut) {
            case id:
                return root.get(PeticioATercer_.id);
            case csvid:
                return root.get(PeticioATercer_.csvId);
            case eniid:
                return root.get(PeticioATercer_.eniId);
            case codiDir3:
                return root.get(PeticioATercer_.codiDir3);
            case nif:
                return root.get(PeticioATercer_.nif);
            case tipusIdentificacio:
                return root.get(PeticioATercer_.tipusIdentificacio);
            case ip:
                return root.get(PeticioATercer_.ip);
            case estat:
                return root.get(PeticioATercer_.estat);
            case dataCreacio:
                return root.get(PeticioATercer_.dataCreacio);
            default:
                throw new IllegalArgumentException("Valor no vàlid " + atribut);

        }
    }
}
