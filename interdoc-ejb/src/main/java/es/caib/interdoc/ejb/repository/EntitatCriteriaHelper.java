package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Entitat;
import es.caib.interdoc.persistence.model.Entitat_;
import es.caib.interdoc.service.model.EntitatAtribut;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Implentació del mapeig entre atributs de la capa de serveis de les Unitats Orgàniques i els paths corresponents
 * de la capa de persistència.
 */
public class EntitatCriteriaHelper extends AbstractCriteriaHelper<Entitat, EntitatAtribut> {

    public EntitatCriteriaHelper(CriteriaBuilder builder, Root<Entitat> root) {
        super(builder, root);
    }

    /**
     * Mapeja els noms d'atributs amb el que treballa la capa de serveis,
     *
     * @param atribut atribut de unitats orgàniques emprat a la capa de servei.
     * @return path obtingut a partir del {@link Root<Aplicacio>}.
     */
    @Override
    protected Path<?> getPath(EntitatAtribut atribut) {
        // TODO revisar substituió dels noms dels camps per algo més dinàmic
        switch (atribut) {
            case id:
                return root.get(Entitat_.id);
            case codiDir3:
                return root.get(Entitat_.codiDir3);
            case nom:
                return root.get(Entitat_.nom);
            case dataCreacio:
                return root.get(Entitat_.dataCreacio);
            case actiu:
                return root.get(Entitat_.actiu);
            default:
                throw new IllegalArgumentException("Valor no vàlid " + atribut);
        }
    }
}
