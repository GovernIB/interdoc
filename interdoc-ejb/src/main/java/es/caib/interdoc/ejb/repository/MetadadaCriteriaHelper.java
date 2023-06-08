package es.caib.interdoc.ejb.repository;

import es.caib.interdoc.persistence.model.Metadada;
import es.caib.interdoc.persistence.model.Metadada_;
import es.caib.interdoc.service.model.MetadadaAtribut;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Implentació del mapeig entre atributs de la capa de serveis de les Unitats Orgàniques i els paths corresponents
 * de la capa de persistència.
 */
public class MetadadaCriteriaHelper extends AbstractCriteriaHelper<Metadada, MetadadaAtribut> {

    public MetadadaCriteriaHelper(CriteriaBuilder builder, Root<Metadada> root) {
        super(builder, root);
    }

    /**
     * Mapeja els noms d'atributs amb el que treballa la capa de serveis,
     *
     * @param atribut atribut de unitats orgàniques emprat a la capa de servei.
     * @return path obtingut a partir del {@link Root<Metadada>}.
     */
    @Override
    protected Path<?> getPath(MetadadaAtribut atribut) {
        switch (atribut) {
            case id:
                return root.get(Metadada_.id);
            case nom:
                return root.get(Metadada_.nom);
            case valor:
                return root.get(Metadada_.valor);
            case referenciaId:
                return root.get(Metadada_.referenciaId);
            case dataCreacio:
                return root.get(Metadada_.dataCreacio);
            default:
                throw new IllegalArgumentException("Valor no vàlid " + atribut);
        }
    }
}
